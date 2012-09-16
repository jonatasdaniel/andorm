package br.com.andorm.test.unit.property;

import java.lang.reflect.Field;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.andorm.property.BooleanFieldProperty;
import br.com.andorm.reflection.Reflection;

public class BooleanFieldPropertyTest {

	class Foo {
		private Boolean field;
		
		public Boolean getField() {
			return field;
		}

		public void setField(Boolean field) {
			this.field = field;
		}
	}
	
	private BooleanFieldProperty property;
	
	@Before
	public void setUp() {
		Field field = Reflection.getField(Foo.class, "field");
		property = new BooleanFieldProperty("field", field);
	}
	
	@Test
	public void shouldAssignFalseValue() {
		Foo foo = new Foo();
		
		Integer value = 0;
		Boolean expected = false;
		property.setValue(foo, value);
		Boolean returned = foo.getField();
		Assert.assertNotNull(returned);
		Assert.assertEquals(expected, returned);
	}
	
	@Test
	public void shouldReadFalseValue() {
		Foo foo = new Foo();
		foo.setField(false);
		
		Integer expected = 0;
		Object returned = property.getValue(foo);
		Assert.assertNotNull(returned);
		Assert.assertEquals(expected, returned);
	}
	
}