package br.com.andorm.test.property;

import static br.com.andorm.reflection.Reflactor.in;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.test.AndroidTestCase;
import br.com.andorm.property.EnumeratedProperty;
import br.com.andorm.types.EnumType;

public class EnumeratedPropertyTest extends AndroidTestCase {

	private EnumeratedProperty property;
	
	public void testAssignmentOrdinalEnumerated() {
		Field field = in(PropertyObject.class).returnField("enumerator");
		Method setMethod = in(PropertyObject.class).returnSetMethodOf(field);
		Method getMethod = in(PropertyObject.class).returnGetMethodOf(field);
		property = new EnumeratedProperty("test", field, getMethod, setMethod, EnumType.Ordinal);
		
		PropertyObject obj = new PropertyObject();
		obj.setEnumerator(EnumTest.Option2);
		Integer returned = (Integer) property.get(obj);
		assertTrue(2 == returned);	
	}
	
	public void testReturnOrdinalEnumerated() {
		Field field = in(PropertyObject.class).returnField("enumerator");
		Method setMethod = in(PropertyObject.class).returnSetMethodOf(field);
		Method getMethod = in(PropertyObject.class).returnGetMethodOf(field);
		property = new EnumeratedProperty("test", field, getMethod, setMethod, EnumType.Ordinal);
		
		PropertyObject obj = new PropertyObject();
		
		property.set(obj, EnumTest.Option2.ordinal());
		assertEquals(obj.getEnumerator(), EnumTest.Option2);
	}
	
	public void testAssignmentNameEnumerated() {
		Field field = in(PropertyObject.class).returnField("enumerator");
		Method setMethod = in(PropertyObject.class).returnSetMethodOf(field);
		Method getMethod = in(PropertyObject.class).returnGetMethodOf(field);
		property = new EnumeratedProperty("test", field, getMethod, setMethod, EnumType.Name);
		
		PropertyObject obj = new PropertyObject();
		obj.setEnumerator(EnumTest.Option2);
		String returned = (String) property.get(obj);
		assertEquals("Option2", returned);	
	}
	
	public void testReturnNameEnumerated() {
		Field field = in(PropertyObject.class).returnField("enumerator");
		Method setMethod = in(PropertyObject.class).returnSetMethodOf(field);
		Method getMethod = in(PropertyObject.class).returnGetMethodOf(field);
		property = new EnumeratedProperty("test", field, getMethod, setMethod, EnumType.Name);
		
		PropertyObject obj = new PropertyObject();
		
		property.set(obj, EnumTest.Option2.name());
		assertEquals(obj.getEnumerator(), EnumTest.Option2);
	}
	
}