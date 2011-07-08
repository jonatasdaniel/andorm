package br.com.andorm.persistence.property;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static br.com.andorm.reflection.Reflactor.*;

/**
 * 
 * @author jonatasdaniel
 * @since 04/03/2011
 * @version 0.1
 * 
 */
public class Property {

	private final String columnName;
	private final Field field;
	private final Method setMethod;
	private final Method getMethod;
	private final boolean nullable;
	private final Class<?> databaseFieldType;

	public Property(String columnName, Field field, Method getMethod,
			Method setMethod) {
		this(columnName, field, getMethod, setMethod, true);
	}

	public Property(String columnName, Field field, Method getMethod,
			Method setMethod, boolean nullable) {
		this(columnName, field, getMethod, setMethod, nullable, field.getType());
	}

	public Property(String columnName, Field field, Method getMethod,
			Method setMethod, boolean nullable, Class<?> databaseFieldType) {
		this.columnName = columnName;
		this.field = field;
		this.setMethod = setMethod;
		this.getMethod = getMethod;
		this.nullable = nullable;
		this.databaseFieldType = databaseFieldType;
	}

	public Object get(Object of) {
		return invoke(of, getMethod).withoutParams();
	}

	public void set(Object in, Object value) {
		invoke(in, setMethod).withParams(value);
	}

	public String getColumnName() {
		return columnName;
	}

	public Field getField() {
		return field;
	}

	public Method getSetMethod() {
		return setMethod;
	}

	public Method getGetMethod() {
		return getMethod;
	}

	public boolean isNullable() {
		return nullable;
	}

	public Class<?> getDatabaseFieldType() {
		return databaseFieldType;
	}

}