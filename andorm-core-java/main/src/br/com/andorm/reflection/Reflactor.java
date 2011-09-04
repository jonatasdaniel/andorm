package br.com.andorm.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import br.com.andorm.AndOrmException;
import br.com.andorm.resources.ResourceBundleFactory;


public final class Reflactor {

	private final static ResourceBundle bundle = ResourceBundleFactory.get();
	
	private final Class<?> clazz;
	
	private Reflactor(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public final Field returnField(String fieldName) {
		try {
			return clazz.getDeclaredField(fieldName);
		} catch(NoSuchFieldException e) {
			throw new AndOrmException(MessageFormat.format(bundle.getString("field_not_found"), fieldName, clazz.getName()));
		}
	}
	
	public final Method returnSetMethodOf(String field) {
		Field f = returnField(field);
		
		return returnSetMethodOf(f);
	}
	
	public final Method returnSetMethodOf(Field field) {
		StringBuilder methodName = new StringBuilder("set");
		methodName.append(String.valueOf(field.getName().charAt(0)).toUpperCase());
		methodName.append(field.getName().substring(1));
		try {
			return clazz.getDeclaredMethod(methodName.toString(), field.getType());
		} catch(NoSuchMethodException e) {
			throw new AndOrmException(MessageFormat.format(bundle.getString("method_not_found"), methodName.toString(), clazz.getName()));
		}
	}
	
	public final Method returnGetMethodOf(String field) {
		Field f = returnField(field);
		
		return returnGetMethodOf(f);
	}
	
	public final Method returnGetMethodOf(Field field) {
		StringBuilder methodName = new StringBuilder("get");
		methodName.append(String.valueOf(field.getName().charAt(0)).toUpperCase());
		methodName.append(field.getName().substring(1));
		try {
			return clazz.getDeclaredMethod(methodName.toString(), new Class<?>[] {});
		} catch(NoSuchMethodException e) {
			throw new AndOrmException(MessageFormat.format(bundle.getString("method_not_found"), methodName.toString(), clazz.getName()));
		}
	}
	
	public final Method returnMethod(String methodName) {
		return returnMethod(methodName, new Class<?>[] {});
	}
	
	public final Method returnMethod(String methodName, Class<?>... paramTypes) {
		try {
			return clazz.getDeclaredMethod(methodName, paramTypes);
		} catch(NoSuchMethodException e) {
			throw new AndOrmException(MessageFormat.format(bundle.getString("method_not_found"), methodName, clazz.getName()));
		}
	}
	
	public static final Invoker invoke(Object receiver, Method method) {
		return new Invoker(receiver, method);
	}
	
	public final static Reflactor in(Class<?> clazz) {
		return new Reflactor(clazz);
	}
	
	public final static Object getFieldValue(Object of, Field field) {
		try {
			field.setAccessible(true);
			return field.get(of);
		} catch(IllegalAccessException e) {
			throw new AndOrmException(MessageFormat.format(bundle.getString("get_field_value_error"), field.getName(), e.getMessage()));
		}
	}
	
	public final static <T> T newInstance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch(InstantiationException ie) {
			throw new AndOrmException(MessageFormat.format(bundle.getString("object_creation_error"), clazz.getCanonicalName()));
		} catch(IllegalAccessException iae) {
			throw new AndOrmException(MessageFormat.format(bundle.getString("object_creation_error"), clazz.getCanonicalName()));
		}
	}
	
}