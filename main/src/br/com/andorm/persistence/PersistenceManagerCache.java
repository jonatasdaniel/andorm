package br.com.andorm.persistence;


import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author jonatasdaniel
 * @since 04/03/2011
 * @version 0.1
 * 
 */
public class PersistenceManagerCache {

	private Map<Class<?>, EntityCache>	entityCaches;

	protected void add(EntityCache cache) {
		if(entityCaches == null)
			entityCaches = new HashMap<Class<?>, EntityCache>();
		entityCaches.put(cache.getEntityClass(), cache);
	}

	protected EntityCache getEntityCache(Class<?> entityClass) {
		return entityCaches.get(entityClass);
	}

}