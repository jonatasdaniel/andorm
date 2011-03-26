package br.com.andorm.test.property;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import br.com.andorm.persistence.property.Property;
import android.test.AndroidTestCase;

import static br.com.andorm.utils.reflection.ReflectionUtils.*;

public class PropertyTest extends AndroidTestCase {

	private Property property;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		Field field = in(ObjectPropertyTest.class).returnField("simpleAttr");
		Method setMethod = in(ObjectPropertyTest.class).returnSetMethodOf(field);
		Method getMethod = in(ObjectPropertyTest.class).returnGetMethodOf(field);
		property = new Property("simple_attr", field, getMethod, setMethod);
	}
	
	public void testAssignment() {
		ObjectPropertyTest p = new ObjectPropertyTest();
		property.set(p, 15);
		Object returned = property.get(p);
		
		assertNotNull(returned);
		assertEquals(15, returned);
	}
	
}