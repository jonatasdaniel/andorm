package br.com.andorm.test.unit.reflection;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;

import br.com.andorm.exception.AndOrmReflectionException;
import br.com.andorm.reflection.Reflection;
import br.com.andorm.resources.ResourceBundleFactory;

public class GetFieldReflectionTest extends ReflectionTest {
	
	@Test(expected=AndOrmReflectionException.class)
	public void shouldBeThrowAException() {
		String fieldName = "something";
		try {
			Reflection.getField(Klass.class, fieldName);
		} catch(AndOrmReflectionException e) {
			Assert.assertEquals(e.getMessage(), ResourceBundleFactory.get().getString("reflection.field-not-found", fieldName, Klass.class.getName()));
			throw e;
		}
	}
	
	@Test
	public void shouldBeReturnFooField() {
		String fieldName = "integerValue";
		Field field = Reflection.getField(Klass.class, fieldName);
		Assert.assertNotNull(field);
		Assert.assertEquals(field.getName(), fieldName);
		Assert.assertEquals(field.getType(), Integer.class);
	}
	
}