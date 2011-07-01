package br.com.andorm.test.persistence.tablemanager;

import static br.com.andorm.reflection.Reflactor.in;

import java.math.BigDecimal;
import java.util.Date;

import android.test.AndroidTestCase;
import br.com.andorm.persistence.property.BigDecimalProperty;
import br.com.andorm.persistence.property.PrimaryKeyProperty;
import br.com.andorm.persistence.property.Property;
import br.com.andorm.persistence.tablemanager.PropertyCreationQueryBuilder;

public class PropertyCreationQueryBuilderTest extends AndroidTestCase {

	private PropertyCreationQueryBuilder builder;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		builder = new PropertyCreationQueryBuilder();
	}
	
	public void testStringProperty() {
		@SuppressWarnings("all")
		class TestClass {
			String nome;
		}
		
		Property property = new Property("nome_pessoa", in(TestClass.class).returnField("nome"), null, null);
		
		String returned = builder.build(property);
		assertEquals("nome_pessoa TEXT", returned);
	}
	
	public void testIntegerProperty() {
		@SuppressWarnings("all")
		class TestClass {
			Integer idade;
		}
		
		Property property = new Property("idade_pessoa", in(TestClass.class).returnField("idade"), null, null);
		
		String returned = builder.build(property);
		assertEquals("idade_pessoa INTEGER", returned);
	}
	
	public void testDateProperty() {
		@SuppressWarnings("all")
		class TestClass {
			Date data;
		}
		
		Property property = new Property("data_nascimento", in(TestClass.class).returnField("data"), null, null);
		
		String returned = builder.build(property);
		assertEquals("data_nascimento LONG", returned);
	}
	
	public void testFloatProperty() {
		@SuppressWarnings("all")
		class TestClass {
			Float peso;
		}
		
		Property property = new Property("peso", in(TestClass.class).returnField("peso"), null, null);
		
		String returned = builder.build(property);
		assertEquals("peso REAL", returned);
	}
	
	public void testDoubleProperty() {
		@SuppressWarnings("all")
		class TestClass {
			Double peso;
		}
		
		Property property = new Property("peso", in(TestClass.class).returnField("peso"), null, null);
		
		String returned = builder.build(property);
		assertEquals("peso REAL", returned);
	}
	
	public void testBigDecimalProperty() {
		@SuppressWarnings("all")
		class TestClass {
			BigDecimal valor;
		}
		
		Property property = new BigDecimalProperty("valor", in(TestClass.class).returnField("valor"), null, null);
		
		String returned = builder.build(property);
		assertEquals("valor REAL", returned);
	}
	
	public void testNotNullProperty() {
		@SuppressWarnings("all")
		class TestClass {
			String attr;
		}
		
		Property property = new Property("not_null_attr", in(TestClass.class).returnField("attr"), null, null, false);
		
		String returned = builder.build(property);
		assertEquals("not_null_attr TEXT NOT NULL", returned);
	}
	
	public void testPrimaryKeyProperty() {
		@SuppressWarnings("all")
		class TestClass {
			Integer id;
		}
		
		Property property = new PrimaryKeyProperty("id", in(TestClass.class).returnField("id"), null, null, false);
		
		String returned = builder.build(property);
		assertEquals("id INTEGER PRIMARY KEY NOT NULL", returned);
	}
	
	public void testPKAutoIncProperty() {
		@SuppressWarnings("all")
		class TestClass {
			Integer id;
		}
		
		Property property = new PrimaryKeyProperty("id", in(TestClass.class).returnField("id"), null, null, true);
		
		String returned = builder.build(property);
		assertEquals("id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL", returned);
	}
	
}