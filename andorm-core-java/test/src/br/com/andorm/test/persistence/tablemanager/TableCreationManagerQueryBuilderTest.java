package br.com.andorm.test.persistence.tablemanager;

import android.test.AndroidTestCase;
import br.com.andorm.Entity;
import br.com.andorm.PrimaryKey;
import br.com.andorm.persistence.EntityCache;
import br.com.andorm.persistence.PersistenceManagerCache;
import br.com.andorm.persistence.property.EnumeratedProperty;
import br.com.andorm.persistence.property.PrimaryKeyProperty;
import br.com.andorm.persistence.property.Property;
import br.com.andorm.persistence.tablemanager.PropertyCreationQueryBuilder;
import br.com.andorm.persistence.tablemanager.TableCreationQueryBuilder;
import br.com.andorm.reflection.Reflactor;
import br.com.andorm.types.EnumType;

public class TableCreationManagerQueryBuilderTest extends AndroidTestCase {

	private TableCreationQueryBuilder builder;
	private PersistenceManagerCache cache;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		cache = new PersistenceManagerCache();
		builder = new TableCreationQueryBuilder(cache, new PropertyCreationQueryBuilder());
	}
	
	public void testSimpleEntity() {
		EntityCache cache = new EntityCache(People.class, "people", null);
		cache.setPk(new PrimaryKeyProperty("id", Reflactor.in(People.class).returnField("id"), null, null, false));
		cache.add(new Property("name", Reflactor.in(People.class).returnField("name"), null, null));
		cache.add(new Property("address", Reflactor.in(People.class).returnField("address"), null, null));
		cache.add(new Property("phone", Reflactor.in(People.class).returnField("phone"), null, null));
		this.cache.add(cache);
		
		final String expected = "CREATE TABLE people ( id INTEGER PRIMARY KEY NOT NULL, address TEXT, name TEXT, phone TEXT );";
		String returned = builder.build(People.class);
		assertEquals(expected, returned);
	}
	
	public void testOrdinalEnumEntity() {
		EntityCache cache = new EntityCache(EnumEntity.class, "enum_entity", null);
		cache.setPk(new PrimaryKeyProperty("id", Reflactor.in(EnumEntity.class).returnField("id"), null, null, false));
		cache.add(new EnumeratedProperty("type", Reflactor.in(EnumEntity.class).returnField("type"), null, null, EnumType.Ordinal));
		this.cache.add(cache);
		
		final String expected = "CREATE TABLE enum_entity ( id INTEGER PRIMARY KEY NOT NULL, type INTEGER );";
		String returned = builder.build(EnumEntity.class);
		assertEquals(expected, returned);
	}
	
	public void testNamedEnumEntity() {
		EntityCache cache = new EntityCache(EnumEntity.class, "enum_entity", null);
		cache.setPk(new PrimaryKeyProperty("id", Reflactor.in(EnumEntity.class).returnField("id"), null, null, false));
		cache.add(new EnumeratedProperty("type", Reflactor.in(EnumEntity.class).returnField("type"), null, null, EnumType.Name));
		this.cache.add(cache);
		
		final String expected = "CREATE TABLE enum_entity ( id INTEGER PRIMARY KEY NOT NULL, type TEXT );";
		String returned = builder.build(EnumEntity.class);
		assertEquals(expected, returned);
	}
	
}

@SuppressWarnings("all")
@Entity
class People {
	@PrimaryKey
	private Integer id;
	private String name;
	private String address;
	private String phone;
}

@SuppressWarnings("all")
@Entity
class EnumEntity {
	@PrimaryKey
	private Integer id;
	private MyEnum type;
}

enum MyEnum {
	Type1, Type2
}