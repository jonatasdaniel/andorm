package br.com.andorm.persistence;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.database.Cursor;

import static br.com.andorm.reflection.Reflactor.*;

public class CursorHelper {

	private final Map<Class<?>, Method> methods;
	
	public CursorHelper() {
		methods = new HashMap<Class<?>, Method>();
		
		createMethods();
	}

	private void createMethods() {
		addMethod("getDouble", Double.class);
		addMethod("getFloat", Float.class);
		addMethod("getInt", Integer.class);
		addMethod("getLong", Long.class);
		addMethod("getLong", Date.class);
		addMethod("getShort", Short.class);
		addMethod("getString", String.class);
	}
	
	private void addMethod(String methodName, Class<?> key) {
		Method method = in(Cursor.class).returnMethod(methodName, int.class);
		methods.put(key, method);
	}
	
	public Method getMethod(Class<?> key) {
		return methods.get(key);
	}
	
	@SuppressWarnings(value="unchecked")
	protected <T> T invokeGet(Cursor cursor, Class<T> returnType, int param) {
		Method method = methods.get(returnType);
		return (T) invoke(cursor, method).withParams(param);
	}
	
}