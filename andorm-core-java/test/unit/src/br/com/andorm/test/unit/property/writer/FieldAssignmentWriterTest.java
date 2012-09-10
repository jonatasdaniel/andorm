package br.com.andorm.test.unit.property.writer;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;

import br.com.andorm.exception.AndOrmReflectionException;
import br.com.andorm.property.writer.FieldAssignmentWriter;
import br.com.andorm.property.writer.PropertyWriter;
import br.com.andorm.reflection.Reflection;
import br.com.andorm.resources.ResourceBundleFactory;
import br.com.andorm.test.unit.property.PropertyTest;

public class FieldAssignmentWriterTest extends PropertyTest {

	private PropertyWriter writer = new FieldAssignmentWriter();
	
	@Test
	public void shouldAssignIntegerValue() {
		Klass k = new Klass();
		Integer value = 123;
		Field field = Reflection.getField(Klass.class, "integerValue");
		writer.write(k, field, value);
		Assert.assertNotNull(k.getIntegerValue());
		Assert.assertEquals(value, k.getIntegerValue());
	}
	
	@Test
	public void shouldAssignStringValue() {
		Klass k = new Klass();
		String value = "";
		Field field = Reflection.getField(Klass.class, "stringValue");
		writer.write(k, field, value);
		Assert.assertNotNull(k.getStringValue());
		Assert.assertEquals(value, k.getStringValue());
	}
	
	@Test(expected=AndOrmReflectionException.class)
	public void shouldBeThrowAException() {
		Klass k = new Klass();
		Field field = Reflection.getField(Klass.class, "integerValue");
		
		Object value = "";
		try {
			writer.write(k, field, value);
		} catch(AndOrmReflectionException e) {
			String expected = ResourceBundleFactory.get().getString("reflection.wrong-field-value-type", value.getClass(), field.getName());
			Assert.assertEquals(e.getMessage(), expected);
			throw e;
		}
	}
	
}