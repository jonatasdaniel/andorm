package br.com.andorm.persistence;


import java.text.MessageFormat;
import java.util.ResourceBundle;

import resources.ResourceBundleFactory;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
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

	private SQLiteDatabase			database;
	private Transaction				transaction;
	private PersistenceManagerCache	cache;

	private final ResourceBundle	bundle	= ResourceBundleFactory.get();

	@Override
	public void open() {

	}

	@Override
	public void close() {

	}

	@Override
	public void save(Object o) throws AndOrmPersistenceException {
		EntityCache cache = this.cache.getEntityCache(o.getClass());
		if(cache == null)
			throw new AndOrmPersistenceException(MessageFormat.format(bundle.getString("is_not_a_entity"), o.getClass().getCanonicalName()));
		
		ContentValues values = new ContentValues();
		for(String column : cache.getColumnsWithoutAutoInc()) {
			Property property = cache.getPropertyByColumn(column);
			Object param = invoke(o, property.getGetMethod());
			this.cache.invokePut(values, param);
		}
		
		database.insert(cache.getTableName(), null, values);
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
			Object param = invoke(o, property.getGetMethod());
			this.cache.invokePut(values, param);
		}
		
		//alter here when change to composite primary key
		String whereClause = cache.getPk().getColumnName().concat("=?");
		String[] whereArgs = {invoke(o, cache.getPk().getGetMethod()).toString()};
		
		database.update(cache.getTableName(), values, whereClause, whereArgs);
	}

	@Override
	public <T> T get(Class<T> entityClass, Object pk) {
		EntityCache cache = this.cache.getEntityCache(entityClass);
		if(cache == null)
			throw new AndOrmException(MessageFormat.format(bundle.getString("is_not_a_entity"), entityClass.getCanonicalName()));

		return null;
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