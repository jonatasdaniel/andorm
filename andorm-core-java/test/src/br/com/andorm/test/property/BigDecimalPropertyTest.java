package br.com.andorm.test.property;

import static br.com.andorm.reflection.Reflactor.in;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import android.test.AndroidTestCase;
import br.com.andorm.property.BigDecimalProperty;

public class BigDecimalPropertyTest extends AndroidTestCase {

	private BigDecimalProperty property;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		Field field = in(PropertyObject.class).returnField("bigDecimal");
		Method setMethod = in(PropertyObject.class).returnSetMethodOf(field);
		Method getMethod = in(PropertyObject.class).returnGetMethodOf(field);
		property = new BigDecimalProperty("bigDecimal", field, getMethod, setMethod);
	}

	public void testFloatValue() {
		PropertyObject object = new PropertyObject();
		
		BigDecimal value = new BigDecimal("1223556.1344");
		object.setBigDecimal(value);
		
		Double doubleValue = (Double) property.get(object);
		assertEquals(doubleValue.doubleValue(), value.doubleValue());
		
		property.set(object, 1223556.1344);
		BigDecimal newValue = object.getBigDecimal();
		
		assertEquals(newValue, value);
	}
	
	public void testIntegerValue() {
		PropertyObject object = new PropertyObject();
		
		BigDecimal value = new BigDecimal("34566");
		object.setBigDecimal(value);
		
		Double doubleValue = (Double) property.get(object);
		assertEquals(doubleValue.intValue(), value.intValue());
		
		property.set(object, 34566);
		BigDecimal newValue = object.getBigDecimal();
		
		assertEquals(newValue, value);
	}
	
	public void testNullValue() {
		PropertyObject object = new PropertyObject();
		
		object.setBigDecimal(null);
		
		assertNull(property.get(object));
		
		property.set(object, null);
		BigDecimal newValue = object.getBigDecimal();
		
		assertNull(newValue);
	}

}