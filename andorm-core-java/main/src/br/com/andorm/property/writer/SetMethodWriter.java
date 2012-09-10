package br.com.andorm.property.writer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import br.com.andorm.reflection.Reflection;

public class SetMethodWriter implements PropertyWriter {

	private Method setMethod;
	
	@Override
	public void write(Object in, Field field, Object value) {
		if(setMethod == null) {
			setMethod = getMethodOf(in.getClass(), field);
		}
		
		Reflection.execute(setMethod, in).withArgs(value);
	}
	
	private Method getMethodOf(Class<?> klass, Field field) {
		String fieldName = field.getName();
		
		StringBuilder methodName = new StringBuilder("set");
		methodName.append(String.valueOf(fieldName.charAt(0)).toUpperCase());
		methodName.append(fieldName.substring(1));
		
		String name = methodName.toString();
		
		return Reflection.findMethod(name, klass).withArgs(field.getType());
	}

}