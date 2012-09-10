package br.com.andorm.property.reader;

import java.lang.reflect.Field;

public interface PropertyReader {

	Object read(Object of, Field field);
	
}