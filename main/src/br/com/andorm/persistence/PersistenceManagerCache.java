package br.com.andorm.persistence;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import br.com.andorm.utils.reflection.ReflectionUtils;

import static br.com.andorm.utils.reflection.ReflectionUtils.*;

import android.content.ContentValues;

/**
 * 
 * @author jonatasdaniel
 * @since 04/03/2011
 * @version 0.1
 * 
 */
public class PersistenceManagerCache {

	private Map<Class<?>, EntityCache>	entityCaches;
	private Map<Class<?>, Method> contentValuesMethods;

	protected PersistenceManagerCache() {
		entityCaches = new HashMap<Class<?>, EntityCache>();
		contentValuesMethods = new HashMap<Class<?>, Method>();
		
		createContentValuesMethods();
	}
	
	private void createContentValuesMethods() {
		putMethod(Boolean.class);
		putMethod(Double.class);
		putMethod(Float.class);
		putMethod(Integer.class);
		putMethod(Long.class);
		putMethod(Short.class);
		putMethod(String.class);
	}
	
	private void putMethod(Class<?> paramType) {
		Method method = in(ContentValues.class).returnMethod("put", String.class, paramType);
		contentValuesMethods.put(paramType, method);
	}
	
	protected void add(EntityCache cache) {
		entityCaches.put(cache.getEntityClass(), cache);
	}
	
	protected void add(Class<?> paramType, Method putMethod) {
		contentValuesMethods.put(paramType, putMethod);
	}

	protected EntityCache getEntityCache(Class<?> entityClass) {
		return entityCaches.get(entityClass);
	}
	
	protected void invokePut(ContentValues values, Object param) {
		Method method = contentValuesMethods.get(param.getClass());
		invoke(values, method).withParams(param);
	}

}