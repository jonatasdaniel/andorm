package br.com.andorm.property;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

public class BigDecimalProperty extends Property {

	public BigDecimalProperty(String columnName, Field field, Method getMethod,
			Method setMethod) {
		super(columnName, field, getMethod, setMethod, true, Double.class);
	}

	public BigDecimalProperty(String columnName, Field field, Method getMethod,
			Method setMethod, boolean nullable) {
		super(columnName, field, getMethod, setMethod, nullable, Double.class);
	}
	
	@Override
	public void set(Object in, Object value) {
		if(value != null) {
			String strValue = value.toString();
			BigDecimal newValue = new BigDecimal(strValue);
			
			super.set(in, newValue);
		} else {
			super.set(in, null);
		}
	}
	
	@Override
	public Object get(Object of) {
		BigDecimal bigDecValue = (BigDecimal) super.get(of);
		if(bigDecValue != null) {
			Double doubleValue = bigDecValue.doubleValue();
			return doubleValue;
		} else {
			return null;
		}
	}

}