package br.com.andorm.property.writer;

import java.lang.reflect.Field;

public interface PropertyWriter {

	void write(Object in, Field field, Object value);
	
}