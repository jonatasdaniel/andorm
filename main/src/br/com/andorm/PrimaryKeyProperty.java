package br.com.andorm;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

import br.com.andorm.persistence.Property;

public class PrimaryKeyProperty extends Property {

	private final boolean	autoInc;

	public PrimaryKeyProperty(String columnName, Field field, Method getMethod,
			Method setMethod, boolean autoInc) {
		super(columnName, field, getMethod, setMethod);
		this.autoInc = autoInc;
	}

	public boolean isAutoInc() {
		return autoInc;
	}

}