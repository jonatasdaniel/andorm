package br.com.andorm.property.writer;

import java.lang.reflect.Field;

import br.com.andorm.reflection.Reflection;

/**
 * 
 * @author jonatas-daniel
 *
 */
public class FieldAssignmentWriter implements PropertyWriter {

	@Override
	public void write(Object in, Field field, Object value) {
		field.setAccessible(true);
		Reflection.set(field, in).value(value);
	}

}