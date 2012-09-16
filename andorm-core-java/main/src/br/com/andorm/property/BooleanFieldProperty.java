package br.com.andorm.property;

import java.lang.reflect.Field;

public class BooleanFieldProperty extends FieldProperty {

	public BooleanFieldProperty(String columnName, Field field) {
		super(columnName, field);
	}
	
	@Override
	public Object getValue(Object of) {
		Object value = super.getValue(of);
		if(value != null) {
			if((Boolean) value) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return null;
		}
	}
	
	@Override
	public void setValue(Object in, Object value) {
		if(value != null) {
			value = ((Integer) value) == 1;
		}
		super.setValue(in, value);
	}

}