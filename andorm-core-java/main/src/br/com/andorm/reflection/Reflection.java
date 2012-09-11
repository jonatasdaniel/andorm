package br.com.andorm.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import br.com.andorm.exception.AndOrmReflectionException;
import br.com.andorm.resources.ResourceBundleFactory;

/**
 * 
 * @author jonatas-daniel
 *
 */
public class Reflection {

	public static <T> T newInstance(Class<T> of) {
		try {
			return of.newInstance();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static FieldWriter set(Field field, Object in) {
		return new FieldWriter(field, in);
	}
	
	public static FieldReader get(Field field) {
		return new FieldReader(field);
	}

	public static MethodFinder findMethod(String name, Class<?> klass) {
		return new MethodFinder(name, klass);
	}
	
	public static MethodExecuter execute(Method method, Object in) {
		return new MethodExecuter(method, in);
	}
	
	public static Field getField(Class<?> of, String name) {
		try {
			return of.getDeclaredField(name);
		} catch (NoSuchFieldException e) {
			String message = ResourceBundleFactory.get().getString("reflection.field-not-found", name, of.getName());
			throw new AndOrmReflectionException(message);
		}
	}

}