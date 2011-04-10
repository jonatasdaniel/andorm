package br.com.andorm.persistence;

import static br.com.andorm.reflection.Reflactor.in;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;

public class ContentValuesHelper {

	private final Map<Class<?>, Method> methods;
	private Method putNullMethod;;

	public ContentValuesHelper() {
		methods = new HashMap<Class<?>, Method>();

		createMethods();
	}

	private void createMethods() {
		addMethod(Boolean.class);
		addMethod(Double.class);
		addMethod(Float.class);
		addMethod(Integer.class);
		addMethod(Long.class);
		addMethod(Short.class);
		addMethod(String.class);

		putNullMethod = in(ContentValues.class)
				.returnMethod("putNull", String.class);
	}

	private void addMethod(Class<?> paramType) {
		Method method = in(ContentValues.class)
				.returnMethod("put", String.class, paramType);
		methods.put(paramType, method);
	}

	public Method getMethod(Class<?> paramType) {
		if (paramType != null)
			return methods.get(paramType);
		else
			return putNullMethod;
	}

	public Method getPutNullMethod() {
		return putNullMethod;
	}

}