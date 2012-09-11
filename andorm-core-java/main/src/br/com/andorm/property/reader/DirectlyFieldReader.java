package br.com.andorm.property.reader;

import java.lang.reflect.Field;

import br.com.andorm.reflection.Reflection;

/**
 * 
 * @author jonatas-daniel
 *
 */
public class DirectlyFieldReader implements PropertyReader {

	@Override
	public Object read(Object of, Field field) {
		field.setAccessible(true);
		return Reflection.get(field).of(of);
	}

}