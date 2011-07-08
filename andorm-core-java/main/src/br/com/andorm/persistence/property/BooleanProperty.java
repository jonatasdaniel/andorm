package br.com.andorm.persistence.property;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class BooleanProperty extends Property {

	public BooleanProperty(String columnName, Field field, Method getMethod, Method setMethod) {
		super(columnName, field, getMethod, setMethod);
	}
	
	public BooleanProperty(String columnName, Field field, Method getMethod, Method setMethod, boolean nullable) {
		super(columnName, field, getMethod, setMethod, nullable);
	}

	@Override
	public void set(Object in, Object value) {
		if(value != null) {
			Integer intValue = (Integer) value;
			super.set(in, intValue == 1);
		} else {
			super.set(in, null);
		}
	}
	
	@Override
	public Object get(Object of) {
		Boolean value = (Boolean) super.get(of);
		if(value != null) {
			return value ? 1 : 0;
		} else {
			return null;
		}
	}
	
}