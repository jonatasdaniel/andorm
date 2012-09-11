package br.com.andorm.reflection;

import java.lang.reflect.Field;

import br.com.andorm.exception.AndOrmReflectionException;
import br.com.andorm.resources.ResourceBundleFactory;

/**
 * 
 * @author jonatas-daniel
 *
 */
public class FieldWriter {

	private final Field field;
	private final Object in;

	public FieldWriter(Field field, Object in) {
		this.field = field;
		this.in = in;
	}

	public void value(Object value) {
		field.setAccessible(true);
		try {
			field.set(in, value);
		} catch (IllegalArgumentException e) {
			String message = ResourceBundleFactory.get().getString("reflection.wrong-field-value-type", value.getClass(), field.getName());
			throw new AndOrmReflectionException(message);
		} catch (IllegalAccessException e) {
			String message = ResourceBundleFactory.get().getString("reflection.wrong-field-value-type", value.getClass(), field.getName());
			throw new AndOrmReflectionException(message);
		}
	}
}