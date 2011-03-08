package br.com.andorm.persistence;

import android.database.sqlite.SQLiteDatabase;


public class AndroidPersistenceManager implements PersistenceManager {

	private SQLiteDatabase database;
	private Transaction transaction;
	private PersistenceManagerCache cache;
	
	@Override
	public void open() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(Object o) throws AndOrmPersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Object o) throws AndOrmPersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Object o) throws AndOrmPersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object get(Class<?> entityClass, Object pk) {
		// TODO Auto-generated method stub
		return null;
	}

	protected PersistenceManagerCache getCache() {
		return cache;
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