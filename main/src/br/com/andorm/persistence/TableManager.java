package br.com.andorm.persistence;

/**
 * 
 * @author jonatasdaniel
 * @since 08/03/2011
 * @version 0.1
 *
 */
public final class TableManager {

	private final PersistenceManagerCache cache;

	protected TableManager(PersistenceManagerCache cache) {
		this.cache = cache;
	}
	
	public void create(Class<?> entityClass) {
		
	}
	
	public void drop(Class<?> entityClass) {
		
	}
	
}