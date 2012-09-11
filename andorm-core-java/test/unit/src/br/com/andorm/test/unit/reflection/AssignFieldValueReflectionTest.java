package br.com.andorm.test.unit.reflection;

import java.lang.reflect.Field;

import junit.framework.Assert;

import org.junit.Test;

import br.com.andorm.exception.AndOrmReflectionException;
import br.com.andorm.reflection.Reflection;
import br.com.andorm.resources.ResourceBundleFactory;

/**
 * 
 * @author jonatas-daniel
 *
 */
public class AssignFieldValueReflectionTest extends ReflectionTest {
	
	@Test(expected=AndOrmReflectionException.class)
	public void shouldBeThrowWrongTypeException() {
		Field field = Reflection.getField(Klass.class, "foo");
		String value = "";
		Klass k = new Klass();
		try {
			Reflection.set(field, k).value(value);
		} catch(AndOrmReflectionException e) {
			Assert.assertEquals(e.getMessage(), ResourceBundleFactory.get().getString("reflection.wrong-field-value-type", field.getName(), value.getClass().getName()));
			throw e;
		}
	}
	
}