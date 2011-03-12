package br.com.andorm.persistence;


import java.io.File;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import resources.ResourceBundleFactory;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.andorm.AndOrmException;
import static br.com.andorm.utils.reflection.ReflectionUtils.*;

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
	public void save(Object o) throws AndOrmPersistenceException {
		EntityCache cache = this.cache.getEntityCache(o.getClass());
		if(cache == null)
			throw new AndOrmPersistenceException(MessageFormat.format(bundle.getString("is_not_a_entity"), o.getClass().getCanonicalName()));
		
		ContentValues values = new ContentValues();
		for(String column : cache.getColumnsWithoutAutoInc()) {
			Property property = cache.getPropertyByColumn(column);
			Object param = invoke(o, property.getGetMethod()).withNoParams();
			this.cache.invokePut(values, column, param);
		}
		
		try {
			database.insert(cache.getTableName(), null, values);
		} catch(SQLException e) {
			throw new AndOrmPersistenceException(MessageFormat.format(bundle.getString("save_error"), o.getClass().getCanonicalName(), e.getMessage()));
		}
	}

	@Override
	public void delete(Object o) throws AndOrmPersistenceException {
		EntityCache cache = this.cache.getEntityCache(o.getClass());
		if(cache == null)
			throw new AndOrmPersistenceException(MessageFormat.format(bundle.getString("is_not_a_entity"), o.getClass().getCanonicalName()));
	}

	@Override
	public void update(Object o) throws AndOrmPersistenceException {
		EntityCache cache = this.cache.getEntityCache(o.getClass());
		if(cache == null)
			throw new AndOrmPersistenceException(MessageFormat.format(bundle.getString("is_not_a_entity"), o.getClass().getCanonicalName()));
		
		ContentValues values = new ContentValues();
		for(String column : cache.getColumnsWithoutAutoInc()) {
			Property property = cache.getPropertyByColumn(column);
			Object param = invoke(o, property.getGetMethod()).withNoParams();
			this.cache.invokePut(values, column, param);
		}
		
		//alter here when change to composite primary key
		String whereClause = cache.getPk().getColumnName().concat("=?");
		String[] whereArgs = {invoke(o, cache.getPk().getGetMethod()).toString()};
		
		try {
			database.update(cache.getTableName(), values, whereClause, whereArgs);
		} catch(SQLException e) {
			throw new AndOrmPersistenceException(MessageFormat.format(bundle.getString("save_error"), o.getClass().getCanonicalName(), e.getMessage()));
		}
	}

	@Override
	public <T> T get(Class<T> entityClass, Object pk) {
		EntityCache cache = this.cache.getEntityCache(entityClass);
		if(cache == null)
			throw new AndOrmException(MessageFormat.format(bundle.getString("is_not_a_entity"), entityClass.getCanonicalName()));

		String selection = cache.getPk().getColumnName().concat(" =?");
		String[] selectionArgs = {pk.toString()};
		
		Cursor cursor = database.query(cache.getTableName(), null, selection, selectionArgs, null, null, null);
		
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
				invoke(object, setMethod).withParams(new Object[] {null});
			} else {
				Class<?> type = setMethod.getParameterTypes()[0];
				Object param = cache.invokeGet(cursor, type, columnIndex);
				invoke(object, setMethod).withParams(param);
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
		return new TableManager(cache);
	}

}