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

	private Map<Class<?>, EntityCache> entityCaches;
	private final ContentValuesHelper contentValuesHelper;
	private final CursorHelper cursorHelper;

	protected PersistenceManagerCache() {
		entityCaches = new HashMap<Class<?>, EntityCache>();

		cursorHelper = new CursorHelper();
		contentValuesHelper = new ContentValuesHelper();
	}

	protected void add(EntityCache cache) {
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

}