package br.com.andorm.persistence.property;


import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * 
 * @author jonatasdaniel
 * @since 08/03/2011
 * @version 0.1
 *
 */
public class PrimaryKeyProperty extends Property {

	private final boolean	autoInc;

	public PrimaryKeyProperty(String columnName, Field field, Method getMethod, Method setMethod, boolean autoInc, boolean nullable) {
		super(columnName, field, getMethod, setMethod, nullable);
		this.autoInc = autoInc;
	}

	public boolean isAutoInc() {
		return autoInc;
	}

}