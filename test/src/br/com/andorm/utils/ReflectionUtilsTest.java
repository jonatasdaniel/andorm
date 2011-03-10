package br.com.andorm.utils;

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
		Integer returned = (Integer) invoke(c, method).withNoParams();
		assertTrue(returned == 20);
	}
	
}