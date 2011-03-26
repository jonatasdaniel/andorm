package br.com.andorm.persistence.property;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import br.com.andorm.types.TemporalType;

/**
 * 
 * @author jonatasdaniel
 * @since 25/03/2011
 * @version 0.1
 * 
 */
public class DateTimeProperty extends Property {

	private final TemporalType type;

	public DateTimeProperty(String columnName, Field field, Method getMethod, Method setMethod, TemporalType type) {
		super(columnName, field, getMethod, setMethod);
		this.type = type;
	}

	public TemporalType getType() {
		return type;
	}
	
	@Override
	public void set(Object in, Object value) {
		if(value != null) {
			Long time = (Long) value;
			super.set(in, new Date(time));	
		} else {
			super.set(in, null);
		}
	}
	
	@Override
	public Object get(Object of) {
		Date date = (Date) super.get(of); 
		return date.getTime();
	}
	
}