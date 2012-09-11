package br.com.andorm.test.unit.reflection;

import java.lang.reflect.Field;

import org.junit.Test;

import junit.framework.Assert;
import br.com.andorm.reflection.Reflection;

/**
 * 
 * @author jonatas-daniel
 *
 */
public class ReadFieldValueReflectionTest extends ReflectionTest {

	@Test
	public void shouldBeReturnIntegerValue() {
		Integer value = 123;
		Klass k = new Klass();
		k.setIntegerValue(value);
		Field field = Reflection.getField(Klass.class, "integerValue");
		Object returned = Reflection.get(field).of(k);
		Assert.assertNotNull(returned);
		Assert.assertEquals(value, returned);
	}
	
}