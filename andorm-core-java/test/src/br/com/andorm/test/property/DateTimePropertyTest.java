package br.com.andorm.test.property;

import static br.com.andorm.reflection.Reflactor.in;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import android.test.AndroidTestCase;
import br.com.andorm.persistence.property.DateTimeProperty;
import br.com.andorm.types.TemporalType;

public class DateTimePropertyTest extends AndroidTestCase {

	private DateTimeProperty property;
	
	protected void setUp() throws Exception {
		Field field = in(PropertyObject.class).returnField("dateAttr");
		Method setMethod = in(PropertyObject.class).returnSetMethodOf(field);
		Method getMethod = in(PropertyObject.class).returnGetMethodOf(field);
		property = new DateTimeProperty("abc", field, getMethod, setMethod, TemporalType.DateTime);
	}
	
	public void testAssignment() {
		PropertyObject object = new PropertyObject();
		Long expected = new Date().getTime();
		property.set(object, expected);
		Long returned = (Long) property.get(object);
		assertEquals(expected, returned);
	}
	
}