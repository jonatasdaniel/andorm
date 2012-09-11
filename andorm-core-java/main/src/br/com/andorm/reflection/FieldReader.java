package br.com.andorm.reflection;

import java.lang.reflect.Field;

/**
 * 
 * @author jonatas-daniel
 *
 */
public class FieldReader {

	private final Field field;

	public FieldReader(Field field) {
		this.field = field;
	}

	public Object of(Object object) {
		field.setAccessible(true);
		try {
			return field.get(object);
		} catch (IllegalAccessException e) {
			return null;
		}
	}
}