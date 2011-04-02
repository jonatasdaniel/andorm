package br.com.andorm.persistence;


import static br.com.andorm.reflection.Reflactor.in;
import static br.com.andorm.reflection.Reflactor.invoke;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;

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
	private Map<Class<?>, Method> cursorMethods;

	protected PersistenceManagerCache() {
		entityCaches = new HashMap<Class<?>, EntityCache>();
		contentValuesMethods = new HashMap<Class<?>, Method>();
		cursorMethods = new HashMap<Class<?>, Method>();
		
		createContentValuesMethods();
		createCursorMethods();
	}
	
	private void createContentValuesMethods() {
		putContentValuesMethod(Boolean.class);
		putContentValuesMethod(Double.class);
		putContentValuesMethod(Float.class);
		putContentValuesMethod(Integer.class);
		putContentValuesMethod(Long.class);
		putContentValuesMethod(Short.class);
		putContentValuesMethod(String.class);
	}
	
	private void createCursorMethods() {
		putCursorMethod("getDouble", Double.class);
		putCursorMethod("getFloat", Float.class);
		putCursorMethod("getInt", Integer.class);
		putCursorMethod("getLong", Long.class);
		putCursorMethod("getShort", Short.class);
		putCursorMethod("getString", String.class);
	}
	
	private void putContentValuesMethod(Class<?> paramType) {
		Method method = in(ContentValues.class).returnMethod("put", String.class, paramType);
		contentValuesMethods.put(paramType, method);
	}
	
	private void putCursorMethod(String methodName, Class<?> returnType) {
		Method method = in(Cursor.class).returnMethod(methodName, int.class);
		cursorMethods.put(returnType, method);
	}
	
	protected void add(EntityCache cache) {
		entityCaches.put(cache.getEntityClass(), cache);
	}
	
	protected void add(Class<?> paramType, Method putMethod) {
		contentValuesMethods.put(paramType, putMethod);
	}

	public EntityCache getEntityCache(Class<?> entityClass) {
		return entityCaches.get(entityClass);
	}
	
	protected void invokePut(ContentValues values, String columnName, Object param) {
		Method method = contentValuesMethods.get(param.getClass());
		invoke(values, method).withParams(columnName, param);
	}
	
	@SuppressWarnings(value="unchecked")
	protected <T> T invokeGet(Cursor cursor, Class<T> returnType, int param) {
		Method method = cursorMethods.get(returnType);
		return (T) invoke(cursor, method).withParams(param);
	}

}