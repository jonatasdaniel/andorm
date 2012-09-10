package br.com.andorm.cache;

import java.util.HashMap;
import java.util.Map;

public class PersistenceManagerCache {

	private final Map<Class<?>, EntityCache> entities;
	
	public PersistenceManagerCache() {
		entities = new HashMap<Class<?>, EntityCache>();
	}
	
	public void add(EntityCache cache) {
		entities.put(cache.getEntityClass(), cache);
	}
	
}