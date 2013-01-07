package br.com.andorm.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author jonatasdaniel
 * @since 04/03/2011
 * @version 0.1
 * 
 * @author tiago.emerick
 * @modified: 07/01/2013
 * @version 0.2
 * 
 */
public class PersistenceManagerCache {

	private Map<Class<?>, EntityCache> entityCaches;
	private final ContentValuesHelper contentValuesHelper;
	private final CursorHelper cursorHelper;

	public PersistenceManagerCache() {
		entityCaches = new HashMap<Class<?>, EntityCache>();

		cursorHelper = new CursorHelper();
		contentValuesHelper = new ContentValuesHelper();
	}

	public void add(EntityCache cache) {
		entityCaches.put(cache.getEntityClass(), cache);
	}

	public EntityCache getEntityCache(Class<?> entityClass) {
		return entityCaches.get(entityClass);
	}

	public CursorHelper getCursorHelper() {
		return cursorHelper;
	}

	public ContentValuesHelper getContentValuesHelper() {
		return contentValuesHelper;
	}
	
	@SuppressWarnings("all")
	public List<Class<?>> getAllEntities() {
		List<Class<?>> entityClasses = new ArrayList<Class<?>>(entityCaches.keySet());
		return entityClasses;
	}

}