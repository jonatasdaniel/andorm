package br.com.andorm.test.property;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import br.com.andorm.persistence.Property;
import android.test.AndroidTestCase;

import static br.com.andorm.utils.reflection.ReflectionUtils.*;

public class PropertyTest extends AndroidTestCase {

	private Property property;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		Field field = in(EntityProperty.class).returnField("simpleAttr");
		Method setMethod = in(EntityProperty.class).returnSetMethodOf(field);
		Method getMethod = in(EntityProperty.class).returnGetMethodOf(field);
		property = new Property("simple_attr", field, getMethod, setMethod);
	}
	
	public void testAssignment() {
		EntityProperty p = new EntityProperty();
		property.set(p, 15);
		Object returned = property.get(p);
		
		assertNotNull(returned);
		assertEquals(15, returned);
	}
	
}