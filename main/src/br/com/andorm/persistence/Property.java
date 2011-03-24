package br.com.andorm.persistence;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static br.com.andorm.utils.reflection.ReflectionUtils.*;

/**
 * 
 * @author jonatasdaniel
 * @since 04/03/2011
 * @version 0.1
 * 
 */
public class Property {

	private final String	columnName;
	private final Field		field;
	private final Method	setMethod;
	private final Method	getMethod;

	public Property(String columnName, Field field, Method getMethod, Method setMethod) {
		this.columnName = columnName;
		this.field = field;
		this.setMethod = setMethod;
		this.getMethod = getMethod;
	}

	public Object get(Object of) {
		return invoke(of, getMethod).withoutParams();
	}
	
	public void set(Object in, Object value) {
		invoke(in, setMethod).withParams(value);
	}
	
	protected String getColumnName() {
		return columnName;
	}

	protected Field getField() {
		return field;
	}

	protected Method getSetMethod() {
		return setMethod;
	}

	protected Method getGetMethod() {
		return getMethod;
	}

}