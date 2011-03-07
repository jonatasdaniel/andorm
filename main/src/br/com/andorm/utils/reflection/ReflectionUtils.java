package br.com.andorm.utils.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import resources.ResourceBundleFactory;

import br.com.andorm.AndOrmException;


public final class ReflectionUtils {

	private final static ResourceBundle bundle = ResourceBundleFactory.get();
	
	private final Class<?> clazz;
	
	private ReflectionUtils(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public final Method returnSetMethodOf(Field field) {
		StringBuilder methodName = new StringBuilder("set");
		methodName.append(String.valueOf(field.getName().charAt(0)).toUpperCase());
		methodName.append(field.getName().substring(1));
		try {
			return clazz.getDeclaredMethod(methodName.toString(), new Class<?>[] {field.getDeclaringClass()});
		} catch(NoSuchMethodException e) {
			throw new AndOrmException(MessageFormat.format(bundle.getString("method_not_found"), methodName.toString(), clazz.getName()));
		}
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
	
	public static final Invoker invoke(Object receiver, Method method) {
		return new Invoker(receiver, method);
	}
	
	public final static ReflectionUtils in(Class<?> clazz) {
		return new ReflectionUtils(clazz);
	}
	
}