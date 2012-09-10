package br.com.andorm.test.unit.property.writer;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;

import br.com.andorm.exception.AndOrmReflectionException;
import br.com.andorm.property.writer.PropertyWriter;
import br.com.andorm.property.writer.SetMethodWriter;
import br.com.andorm.reflection.Reflection;
import br.com.andorm.resources.ResourceBundleFactory;
import br.com.andorm.test.unit.property.PropertyTest;

public class SetMethodWriterTest extends PropertyTest {
	
	private PropertyWriter writer = new SetMethodWriter(); 
	
	@Test
	public void shouldBeAssignAValue() {
		Klass k = new Klass();
		Field field = Reflection.getField(Klass.class, "integerValue");
		Object value = 123;
		writer.write(k, field, value);
		Assert.assertNotNull(k.getIntegerValue());
		Assert.assertEquals(value, k.getIntegerValue());
	}
	
	@Test(expected=AndOrmReflectionException.class)
	public void shouldBeThrowAException() {
		Klass k = new Klass();
		Field field = Reflection.getField(Klass.class, "integerValue");
		
		Object value = "";
		try {
			writer.write(k, field, value);
		} catch(AndOrmReflectionException e) {
			String expected = ResourceBundleFactory.get().getString("reflection.method-execution-error", "setIntegerValue", k.getClass().getName());
			Assert.assertEquals(e.getMessage(), expected);
			throw e;
		}
	}

}