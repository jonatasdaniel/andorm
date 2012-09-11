package br.com.andorm.reflection;

import java.lang.reflect.Method;

/**
 * 
 * @author jonatas-daniel
 *
 */
public class MethodFinder {

	private final String methodName;
	private final Class<?> klass;

	public MethodFinder(String methodName, Class<?> klass) {
		this.methodName = methodName;
		this.klass = klass;
	}
	
	public Method withoutArgs() {
		return withArgs(new Class[] {});
	}
	
	public Method withArgs(Class<?>... parameterTypes) {
		try {
			return klass.getDeclaredMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

}