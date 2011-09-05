package br.com.andorm.test.property;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import br.com.andorm.property.Property;
import android.test.AndroidTestCase;

import static br.com.andorm.reflection.Reflactor.*;

public class PropertyTest extends AndroidTestCase {

	private Property property;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		Field field = in(PropertyObject.class).returnField("simpleAttr");
		Method setMethod = in(PropertyObject.class).returnSetMethodOf(field);
		Method getMethod = in(PropertyObject.class).returnGetMethodOf(field);
		property = new Property("simple_attr", field, getMethod, setMethod);
	}
	
	public void testAssignment() {
		PropertyObject p = new PropertyObject();
		property.set(p, 15);
		Object returned = property.get(p);
		
		assertNotNull(returned);
		assertEquals(15, returned);
	}
	
}