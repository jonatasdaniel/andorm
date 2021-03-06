package br.com.andorm.property;


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

	public PrimaryKeyProperty(String columnName, Field field, Method getMethod, Method setMethod, boolean autoInc) {
		super(columnName, field, getMethod, setMethod, false);
		this.autoInc = autoInc;
	}

	public boolean isAutoInc() {
		return autoInc;
	}

}