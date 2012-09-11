package br.com.andorm.property.reader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import br.com.andorm.reflection.Reflection;

/**
 * 
 * @author jonatas-daniel
 *
 */
public class GetMethodReader implements PropertyReader {

	private Method getMethod;
	
	@Override
	public Object read(Object of, Field field) {
		if(getMethod == null) {
			getMethod = getMethodOf(of.getClass(), field);
		}
		
		return Reflection.execute(getMethod, of).withoutArgs();
	}

	private Method getMethodOf(Class<?> klass, Field field) {
		String fieldName = field.getName();
		
		StringBuilder methodName = new StringBuilder("get");
		methodName.append(String.valueOf(fieldName.charAt(0)).toUpperCase());
		methodName.append(fieldName.substring(1));
		
		String name = methodName.toString();
		
		return Reflection.findMethod(name, klass).withoutArgs();
	}

}