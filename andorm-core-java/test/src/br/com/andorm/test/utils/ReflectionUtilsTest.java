package br.com.andorm.test.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static br.com.andorm.utils.reflection.ReflectionUtils.*;

import android.test.AndroidTestCase;


public class ReflectionUtilsTest extends AndroidTestCase {

	public void testGetMethod() {
		ReflectionTestClass c = new ReflectionTestClass();
		c.setIdade(20);
		Method method = in(c.getClass()).returnGetMethodOf("idade");
		assertNotNull(method);
		assertEquals("getIdade", method.getName());
		assertEquals(Integer.class, method.getReturnType());
	}
	
	public void testSetMethod() {
		ReflectionTestClass c = new ReflectionTestClass();
		c.setIdade(20);
		Method method = in(c.getClass()).returnSetMethodOf("idade");
		assertNotNull(method);
		assertEquals("setIdade", method.getName());
		assertEquals(Integer.class, method.getParameterTypes()[0]);
	}
	
	public void testInvokerMethod() {
		ReflectionTestClass c = new ReflectionTestClass();
		c.setIdade(20);
		Method method = in(c.getClass()).returnGetMethodOf("idade");
		Integer returned = (Integer) invoke(c, method).withoutParams();
		assertTrue(returned == 20);
	}
	
	public void testReturnFieldNome() {
		Field field = in(ReflectionTestClass.class).returnField("nome");
		assertNotNull(field);
		assertEquals("nome", field.getName());
		assertEquals(String.class, field.getType());
	}
	
	public void testReturnFieldIdade() {
		Field field = in(ReflectionTestClass.class).returnField("idade");
		assertNotNull(field);
		assertEquals("idade", field.getName());
		assertEquals(Integer.class, field.getType());
	}
	
	public void testReturnFieldPeso() {
		Field field = in(ReflectionTestClass.class).returnField("peso");
		assertNotNull(field);
		assertEquals("peso", field.getName());
		assertEquals(Integer.class, field.getType());
	}
	
}