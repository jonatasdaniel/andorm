package br.com.andorm.test.unit.property.reader;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;

import br.com.andorm.property.reader.DirectlyFieldReader;
import br.com.andorm.property.reader.PropertyReader;
import br.com.andorm.reflection.Reflection;
import br.com.andorm.test.unit.property.PropertyTest;

/**
 * 
 * @author jonatas-daniel
 *
 */
public class DirectlyFieldReaderTest extends PropertyTest {

	private PropertyReader reader = new DirectlyFieldReader();
	
	@Test
	public void shouldAssignIntegerValue() {
		Klass k = new Klass();
		Integer value = 123;
		k.setIntegerValue(value);
		Field field = Reflection.getField(Klass.class, "integerValue");
		Object readedValue = reader.read(k, field);
		Assert.assertNotNull(readedValue);
		Assert.assertEquals(value, readedValue);
	}
	
	@Test
	public void shouldAssignStringValue() {
		Klass k = new Klass();
		String value = "";
		k.setStringValue(value);
		Field field = Reflection.getField(Klass.class, "stringValue");
		Object readedValue = reader.read(k, field);
		Assert.assertNotNull(readedValue);
		Assert.assertEquals(value, readedValue);
	}
	
}