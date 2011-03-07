package br.com.andorm.utils.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import resources.ResourceBundleFactory;

import br.com.andorm.AndOrmException;


public final class Invoker {

	private final ResourceBundle bundle = ResourceBundleFactory.get();
	
	private final Object receiver;
	private final Method method;
	
	public Invoker(Object receiver, Method method) {
		this.receiver = receiver;
		this.method = method;
	}
	
	public Object withParams(Object... params) {
		try {
			return method.invoke(receiver, params);
		} catch(InvocationTargetException e) {
			throw new AndOrmException(MessageFormat.format(bundle.getString("invocation_failed"), method.getName(), e.getMessage()));
		} catch(IllegalAccessException ie) {
			throw new AndOrmException(MessageFormat.format(bundle.getString("invocation_failed"), method.getName(), ie.getMessage()));
		}
	}
	
	public Object withNoParams() {
		try {
			return method.invoke(receiver, new Object[] {});
		} catch(InvocationTargetException e) {
			throw new AndOrmException(MessageFormat.format(bundle.getString("invocation_failed"), method.getName(), e.getMessage()));
		} catch(IllegalAccessException ie) {
			throw new AndOrmException(MessageFormat.format(bundle.getString("invocation_failed"), method.getName(), ie.getMessage()));
		}
	}
}