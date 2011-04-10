package br.com.andorm.persistence;


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
import br.com.andorm.query.Criteria;

import static br.com.andorm.reflection.Reflactor.*;

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
		EntityCache cache = this.cache.getEntityCache(o.getClass());
		if(cache == null)
			throw new AndOrmPersistenceException(MessageFormat.format(bundle.getString("is_not_a_entity"), o.getClass().getCanonicalName()));
		
		ContentValues values = new ContentValues();
		for(String column : cache.getColumnsWithoutAutoInc()) {
			Property property = cache.getPropertyByColumn(column);
			Object param = property.get(o);
			
			if(param != null) {
				Method putMethod = this.cache.getContentValuesHelper().getMethod(param.getClass());
				invoke(values, putMethod).withParams(column, param);
			} else {
				Method putMethod = this.cache.getContentValuesHelper().getPutNullMethod();
				invoke(values, putMethod).withParams(column);
			}
		}
		
		try {
			database.insert(cache.getTableName(), null, values);
		} catch(SQLException e) {
			throw new AndOrmPersistenceException(MessageFormat.format(bundle.getString("save_error"), o.getClass().getCanonicalName(), e.getMessage()));
		}
	}
	
	@Override
	public void delete(Object o) {
		EntityCache cache = this.cache.getEntityCache(o.getClass());
		if(cache == null)
			throw new AndOrmPersistenceException(MessageFormat.format(bundle.getString("is_not_a_entity"), o.getClass().getCanonicalName()));
		
		//alter here when change to composite primary key
		String whereClause = cache.getPk().getColumnName().concat("=?");
		Object param = cache.getPk().get(o);
		if(param == null)
			throw new AndOrmException(MessageFormat.format(bundle.getString("id_null"), o.getClass().getCanonicalName()));
		String[] whereArgs = {param.toString()};
		
		try {
			database.delete(cache.getTableName(), whereClause, whereArgs);
		} catch(SQLException e) {
			throw new AndOrmPersistenceException(MessageFormat.format(bundle.getString("delete_error"), o.getClass().getCanonicalName(), e.getMessage()));
		}
	}

	@Override
	public void update(Object o) {
		EntityCache cache = this.cache.getEntityCache(o.getClass());
		if(cache == null)
			throw new AndOrmPersistenceException(MessageFormat.format(bundle.getString("is_not_a_entity"), o.getClass().getCanonicalName()));
		
		ContentValues values = new ContentValues();
		for(String column : cache.getColumnsWithoutAutoInc()) {
			Property property = cache.getPropertyByColumn(column);
			Object param = property.get(o);
			
			if(param != null) {
				Method putMethod = this.cache.getContentValuesHelper().getMethod(param.getClass());
				invoke(values, putMethod).withParams(column, param);
			} else {
				Method putMethod = this.cache.getContentValuesHelper().getPutNullMethod();
				invoke(values, putMethod).withParams(column);
			}
		}
		
		//alter here when change to composite primary key
		String whereClause = cache.getPk().getColumnName().concat("=?");
		Object param = cache.getPk().get(o);
		if(param == null)
			throw new AndOrmException(MessageFormat.format(bundle.getString("id_null"), o.getClass().getCanonicalName()));
		String[] whereArgs = {param.toString()};
		
		try {
			database.update(cache.getTableName(), values, whereClause, whereArgs);
		} catch(SQLException e) {
			throw new AndOrmPersistenceException(MessageFormat.format(bundle.getString("save_error"), o.getClass().getCanonicalName(), e.getMessage()));
		}
	}

	@Override
	public <T> T read(Class<T> entityClass, Object pk) {
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
			T object = newInstanceOf(entityClass);
			inflate(cursor, object, cache);
			return object;
		} else
			return null;
	}

	private void inflate(Cursor cursor, Object object, EntityCache entityCache) { 
		for(String column : entityCache.getColumns()) {
			int columnIndex = cursor.getColumnIndex(column);
			
			Property property = entityCache.getPropertyByColumn(column);
			Method setMethod = property.getSetMethod();
			
			if(cursor.isNull(columnIndex)) {
				property.set(object, null);
			} else {
				Class<?> type = setMethod.getParameterTypes()[0];
				
				Method cursorMethod = cache.getCursorHelper().getMethod(type);
				Object param = invoke(cursor, cursorMethod).withParams(columnIndex);
				
				property.set(object, param);
			}
		}
	}
	
	private <T> T newInstanceOf(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch(InstantiationException ie) {
			throw new AndOrmException(MessageFormat.format(bundle.getString("object_creation_error"), clazz.getCanonicalName()));
		} catch(IllegalAccessException iae) {
			throw new AndOrmException(MessageFormat.format(bundle.getString("object_creation_error"), clazz.getCanonicalName()));
		}
	}
	
	protected void setCache(PersistenceManagerCache cache) {
		this.cache = cache;
	}

	@Override
	public Transaction getTransaction() {
		if(transaction == null)
			transaction = new AndroidTransaction(database);
		return transaction;
	}

	@Override
	public TableManager getTableManager() {
		return new TableManager(cache, database);
	}

	@Override
	public List<? extends Object> list(Criteria criteria) {
		EntityCache cache = this.cache.getEntityCache(criteria.getClazz());
		if(cache == null)
			throw new AndOrmException(MessageFormat.format(bundle.getString("is_not_a_entity"), criteria.getClazz().getCanonicalName()));

		AndroidQueryBuilder queryBuilder = new AndroidQueryBuilder(criteria, cache);
		queryBuilder.build();
		
		String selection = queryBuilder.whereClause();
		String[] selectionArgs = queryBuilder.whereArgs();
		String orderBy = queryBuilder.orderBy();
		String groupBy = queryBuilder.groupBy();
		String having = queryBuilder.having();
		
		Cursor cursor = database.query(cache.getTableName(), null, selection, selectionArgs, groupBy, having, orderBy);
		
		List<Object> list = new ArrayList<Object>();
		
		if(cursor.moveToFirst()) {
			do {
				Object object = newInstanceOf(criteria.getClazz());
				inflate(cursor, object, cache);
				list.add(object);
			} while(cursor.moveToNext());
		}
		
		return list;
	}

	@Override
	public long count(Class<?> of) {
		EntityCache cache = this.cache.getEntityCache(of);
		if(cache == null)
			throw new AndOrmException(MessageFormat.format(bundle.getString("is_not_a_entity"), of.getCanonicalName()));
		
		StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ");
		sql.append(cache.getTableName());
		return database.compileStatement(sql.toString()).simpleQueryForLong();
	}

}