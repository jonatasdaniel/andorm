package br.com.andorm.test.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


import static br.com.andorm.reflection.Reflactor.*;

import android.test.AndroidTestCase;


public class ReflactorTest extends AndroidTestCase {

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
	
	public void testReturnPrivateAccessorMethod() {
		Field field = in(ReflectionTestClass.class).returnField("endereco");
		assertNotNull(field);
		assertEquals("endereco", field.getName());
		assertEquals(String.class, field.getType());
		
		Method setMethod = in(ReflectionTestClass.class).returnSetMethodOf(field);
		assertNotNull(setMethod);
		assertEquals("setEndereco", setMethod.getName());
		assertEquals(String.class, setMethod.getParameterTypes()[0]);
		
		Method getMethod = in(ReflectionTestClass.class).returnGetMethodOf(field);
		assertNotNull(getMethod);
		assertEquals("getEndereco", getMethod.getName());
		assertEquals(String.class, getMethod.getReturnType());
	}
	
	public void testInvokePrivateAccessorMethod() {
		Field field = in(ReflectionTestClass.class).returnField("endereco");
		Method setMethod = in(ReflectionTestClass.class).returnSetMethodOf(field);
		ReflectionTestClass object = new ReflectionTestClass();
		invoke(object, setMethod).withParams("a address");
		Method getMethod = in(ReflectionTestClass.class).returnGetMethodOf(field);
		String returned = (String) invoke(object, getMethod).withoutParams();
		assertEquals(returned, "a address");
	}
	
	public void testGetFieldValueMethod() {
		ReflectionTestClass c = new ReflectionTestClass();
		c.setIdade(20);
		c.setNome("a name");
		c.setPeso(70);
		
		Field idadeField = in(ReflectionTestClass.class).returnField("idade");
		Field nomeField = in(ReflectionTestClass.class).returnField("nome");
		Field pesoField = in(ReflectionTestClass.class).returnField("peso");
		
		Object idade = getFieldValue(c, idadeField);
		assertNotNull(idade);
		assertEquals(c.getIdade(), idade);
		
		Object nome = getFieldValue(c, nomeField);
		assertNotNull(nome);
		assertEquals(c.getNome(), nome);
		
		Object peso = getFieldValue(c, pesoField);
		assertNotNull(peso);
		assertEquals(c.getPeso(), peso);
	}
	
}