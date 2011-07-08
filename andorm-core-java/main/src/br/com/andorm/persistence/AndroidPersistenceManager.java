package br.com.andorm.persistence;


import static br.com.andorm.reflection.Reflactor.invoke;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import resources.ResourceBundleFactory;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import br.com.andorm.AndOrmException;
import br.com.andorm.persistence.property.Property;
import br.com.andorm.persistence.tablemanager.TableManager;
import br.com.andorm.query.Criteria;
import br.com.andorm.reflection.Reflactor;

/**
 * 
 * @author jonatasdaniel
 * @since 18/02/2011
 * @version 0.1
 *
 */
public class AndroidPersistenceManager implements PersistenceManager {

	private final String databasePath;
	private SQLiteDatabase			database;
	private Transaction				transaction;
	private PersistenceManagerCache	cache;

	private final ResourceBundle	bundle	= ResourceBundleFactory.get();

	public AndroidPersistenceManager(String databasePath) {
		this.databasePath = databasePath;
	}
	
	@Override
	public void open() {
		database = SQLiteDatabase.openOrCreateDatabase(databasePath, null);
	}

	@Override
	public void close() {
		if(database != null && database.isOpen())
			database.close();
	}

	@Override
	public void save(Object o) {
		if(!isOpen())
			open();
		
		EntityCache cache = this.cache.getEntityCache(o.getClass());
		if(cache == null)
			throw new AndOrmPersistenceException(MessageFormat.format(bundle.getString("is_not_a_entity"), o.getClass().getCanonicalName()));
		
		ContentValues values = new ContentValues();
		for(String column : cache.getColumnsWithoutAutoInc()) {
			Property property = cache.getPropertyByColumn(column);
			Object param = property.get(o);
			
			Method putMethod = this.cache.getContentValuesHelper().getMethod(property.getDatabaseFieldType());
			invoke(values, putMethod).withParams(column, param);
		}
		
		if(cache.getBeforeSaveMethod() != null) {
			invoke(o, cache.getBeforeSaveMethod()).withoutParams();
		}
		
		String tableName = cache.getTableName();
		try {
			database.insert(tableName, null, values);
		} catch(SQLException e) {
			throw new AndOrmPersistenceException(MessageFormat.format(bundle.getString("save_error"), o.getClass().getCanonicalName(), e.getMessage()));
		}
		
		if(cache.getAfterSaveMethod() != null) {
			invoke(o, cache.getAfterSaveMethod()).withoutParams();
		}
	}
	
	@Override
	public void delete(Object o) {
		if(!isOpen())
			open();
		EntityCache cache = this.cache.getEntityCache(o.getClass());
		if(cache == null)
			throw new AndOrmPersistenceException(MessageFormat.format(bundle.getString("is_not_a_entity"), o.getClass().getCanonicalName()));
		
		//alter here when change to composite primary key
		String whereClause = cache.getPk().getColumnName().concat("=?");
		Object param = cache.getPk().get(o);
		if(param == null)
			throw new AndOrmException(MessageFormat.format(bundle.getString("id_null"), o.getClass().getCanonicalName()));
		String[] whereArgs = {param.toString()};
		
		if(cache.getBeforeDeleteMethod() != null) {
			invoke(o, cache.getBeforeDeleteMethod()).withoutParams();
		}
		
		String tableName = cache.getTableName();
		try {
			database.delete(tableName, whereClause, whereArgs);
		} catch(SQLException e) {
			throw new AndOrmPersistenceException(MessageFormat.format(bundle.getString("delete_error"), o.getClass().getCanonicalName(), e.getMessage()));
		}
		
		if(cache.getAfterDeleteMethod() != null) {
			invoke(o, cache.getAfterDeleteMethod()).withoutParams();
		}
	}

	@Override
	public void update(Object o) {
		if(!isOpen())
			open();
		EntityCache cache = this.cache.getEntityCache(o.getClass());
		if(cache == null)
			throw new AndOrmPersistenceException(MessageFormat.format(bundle.getString("is_not_a_entity"), o.getClass().getCanonicalName()));
		
		ContentValues values = new ContentValues();
		for(String column : cache.getColumnsWithoutAutoInc()) {
			Property property = cache.getPropertyByColumn(column);
			Object param = property.get(o);
			
			Method putMethod = this.cache.getContentValuesHelper().getMethod(property.getDatabaseFieldType());
			invoke(values, putMethod).withParams(column, param);
		}
		
		//alter here when change to composite primary key
		String whereClause = cache.getPk().getColumnName().concat("=?");
		Object param = cache.getPk().get(o);
		if(param == null)
			throw new AndOrmException(MessageFormat.format(bundle.getString("id_null"), o.getClass().getCanonicalName()));
		String[] whereArgs = {param.toString()};
		
		if(cache.getBeforeUpdateMethod() != null) {
			invoke(o, cache.getBeforeUpdateMethod()).withoutParams();
		}
		
		String tableName = cache.getTableName();
		try {
			database.update(tableName, values, whereClause, whereArgs);
		} catch(SQLException e) {
			throw new AndOrmPersistenceException(MessageFormat.format(bundle.getString("save_error"), o.getClass().getCanonicalName(), e.getMessage()));
		}
		
		if(cache.getAfterUpdateMethod() != null) {
			invoke(o, cache.getAfterUpdateMethod()).withoutParams();
		}
	}

	@Override
	public <T> T read(Class<T> entityClass, Object pk) {
		if(!isOpen())
			open();
		EntityCache cache = this.cache.getEntityCache(entityClass);
		if(cache == null)
			throw new AndOrmException(MessageFormat.format(bundle.getString("is_not_a_entity"), entityClass.getCanonicalName()));

		String selection = cache.getPk().getColumnName().concat(" =?");
		String[] selectionArgs = {pk.toString()};
		
		Cursor cursor = null;
		try {
			cursor = database.query(cache.getTableName(), null, selection, selectionArgs, null, null, null);
		} catch(SQLiteException e) {
			throw new AndOrmPersistenceException(MessageFormat.format("read_error", entityClass.getCanonicalName(), e.getMessage()));
		}
		
		if(cursor.moveToFirst()) {
			T object = Reflactor.newInstance(entityClass);
			inflate(cursor, object, cache);
			return object;
		} else
			return null;
	}

	private void inflate(Cursor cursor, Object object, EntityCache entityCache) { 
		for(String column : entityCache.getColumns()) {
			int columnIndex = cursor.getColumnIndex(column);
			
			Property property = entityCache.getPropertyByColumn(column);
			
			if(cursor.isNull(columnIndex)) {
				property.set(object, null);
			} else {
				Class<?> type = property.getDatabaseFieldType();
				
				Method cursorMethod = cache.getCursorHelper().getMethod(type);
				Object param = invoke(cursor, cursorMethod).withParams(columnIndex);
				
				property.set(object, param);
			}
		}
	}
	
	protected void setCache(PersistenceManagerCache cache) {
		this.cache = cache;
	}

	@Override
	public Transaction getTransaction() {
		if(!isOpen())
			open();
		if(transaction == null)
			transaction = new AndroidTransaction(database);
		return transaction;
	}

	@Override
	public TableManager getTableManager() {
		if(!isOpen())
			open();
		return new TableManager(cache, database);
	}

	@Override
	public <T> List<T> find(Class<T> entityClass, Criteria query) {
		if(!isOpen())
			open();
		EntityCache cache = this.cache.getEntityCache(query.getClazz());
		if(cache == null)
			throw new AndOrmException(MessageFormat.format(bundle.getString("is_not_a_entity"), query.getClazz().getCanonicalName()));

		AndroidQueryBuilder queryBuilder = new AndroidQueryBuilder(query, cache);
		queryBuilder.build();
		
		String selection = queryBuilder.whereClause();
		String[] selectionArgs = queryBuilder.whereArgs();
		String orderBy = queryBuilder.orderBy();
		String groupBy = queryBuilder.groupBy();
		String having = queryBuilder.having();
		
		Cursor cursor = database.query(cache.getTableName(), null, selection, selectionArgs, groupBy, having, orderBy);
		
		List<T> list = new ArrayList<T>();
		
		if(cursor.moveToFirst()) {
			do {
				@SuppressWarnings("all")
				T object = (T) Reflactor.newInstance(query.getClazz());
				inflate(cursor, object, cache);
				list.add(object);
			} while(cursor.moveToNext());
		}
		
		cursor.close();
		close();
		
		return list;
	}

	@Override
	public long count(Class<?> of) {
		if(!isOpen())
			open();
		EntityCache cache = this.cache.getEntityCache(of);
		if(cache == null)
			throw new AndOrmException(MessageFormat.format(bundle.getString("is_not_a_entity"), of.getCanonicalName()));
		
		StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ");
		sql.append(cache.getTableName());
		return database.compileStatement(sql.toString()).simpleQueryForLong();
	}

	@Override
	public long count(Criteria criteria) {
		if(!isOpen())
			open();
		return 0;
	}

	@Override
	public boolean isOpen() {
		return database != null && database.isOpen();
	}

	@Override
	public <T> T first(Class<T> of) {
		if(!isOpen())
			open();
		EntityCache cache = this.cache.getEntityCache(of);
		if(cache == null)
			throw new AndOrmException(MessageFormat.format(bundle.getString("is_not_a_entity"), of.getCanonicalName()));

		Cursor cursor = null;
		String limit = "1";
		try {
			cursor = database.query(cache.getTableName(), null, null, null, null, null, null, limit);
		} catch(SQLiteException e) {
			throw new AndOrmPersistenceException(MessageFormat.format("read_error", of.getCanonicalName(), e.getMessage()));
		}
		
		if(cursor.moveToFirst()) {
			T object = Reflactor.newInstance(of);
			inflate(cursor, object, cache);
			return object;
		} else
			return null;
	}

	@Override
	public <T> T last(Class<T> of) {
		if(!isOpen())
			open();
		EntityCache cache = this.cache.getEntityCache(of);
		if(cache == null)
			throw new AndOrmException(MessageFormat.format(bundle.getString("is_not_a_entity"), of.getCanonicalName()));

		Cursor cursor = null;
		String limit = "1";
		try {
			cursor = database.query(cache.getTableName(), null, null, null, null, null, null, limit);
		} catch(SQLiteException e) {
			throw new AndOrmPersistenceException(MessageFormat.format("read_error", of.getCanonicalName(), e.getMessage()));
		}
		
		if(cursor.moveToLast()) {
			T object = Reflactor.newInstance(of);
			inflate(cursor, object, cache);
			return object;
		} else
			return null;
	}

}