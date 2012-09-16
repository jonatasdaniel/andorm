package br.com.andorm.test.unit.property.reader;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;

import br.com.andorm.property.reader.GetMethodReader;
import br.com.andorm.property.reader.PropertyReader;
import br.com.andorm.reflection.Reflection;
import br.com.andorm.test.unit.property.PropertyAcessorTest;

/**
 * 
 * @author jonatas-daniel
 *
 */
public class GetMethodReaderTest extends PropertyAcessorTest {
	
	private PropertyReader reader = new GetMethodReader(); 
	
	@Test
	public void shouldBeAssignAValue() {
		Klass k = new Klass();
		Integer value = 123;
		k.setIntegerValue(value);
		Field field = Reflection.getField(Klass.class, "integerValue");
		Object readedValue = reader.read(k, field);
		Assert.assertNotNull(readedValue);
		Assert.assertEquals(value, readedValue);
	}
}