package br.com.andorm.persistence;


public final class TableManager {

	private final PersistenceManagerCache cache;

	public TableManager(PersistenceManagerCache cache) {
		this.cache = cache;
	}
	
	public void create(Class<?> entityClass) {
		
	}
	
	public void drop(Class<?> entityClass) {
		
	}
	
}