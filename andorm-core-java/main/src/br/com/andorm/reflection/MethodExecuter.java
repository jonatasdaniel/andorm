package br.com.andorm.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import br.com.andorm.exception.AndOrmReflectionException;
import br.com.andorm.resources.ResourceBundleFactory;

/**
 * 
 * @author jonatas-daniel
 *
 */
public class MethodExecuter {

	private final Method method;
	private final Object in;

	public MethodExecuter(Method method, Object in) {
		this.method = method;
		this.in = in;
	}
	
	public Object withoutArgs() {
		return withArgs(new Object[] {});
	}
	
	public Object withArgs(Object... args) {
		method.setAccessible(true);
		try {
			return method.invoke(in, args);
		} catch (InvocationTargetException e) {
			String message = ResourceBundleFactory.get().getString("reflection.method-execution-error", method.getName(), in.getClass().getName());
			throw new AndOrmReflectionException(message);
		} catch (IllegalArgumentException e) {
			String message = ResourceBundleFactory.get().getString("reflection.method-execution-error", method.getName(), in.getClass().getName());
			throw new AndOrmReflectionException(message);
		} catch (IllegalAccessException e) {
			String message = ResourceBundleFactory.get().getString("reflection.method-execution-error", method.getName(), in.getClass().getName());
			throw new AndOrmReflectionException(message);
		}
	}

}