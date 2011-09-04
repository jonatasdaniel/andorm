package br.com.andorm.test;

import static br.com.andorm.reflection.Reflactor.getFieldValue;
import static br.com.andorm.reflection.Reflactor.in;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import android.test.AndroidTestCase;
import br.com.andorm.AndOrmException;
import br.com.andorm.config.AndOrmConfiguration;
import br.com.andorm.config.EntityConfiguration;
import br.com.andorm.config.NameTypes;
import br.com.andorm.persistence.AndroidPersistenceManager;
import br.com.andorm.persistence.EntityCache;
import br.com.andorm.persistence.PersistenceManager;
import br.com.andorm.persistence.PersistenceManagerCache;
import br.com.andorm.persistence.PersistenceManagerFactory;
import br.com.andorm.persistence.property.DateTimeProperty;
import br.com.andorm.persistence.property.Property;
import br.com.andorm.resources.ResourceBundleFactory;
import br.com.andorm.test.entity.AnnotatedDateTimeEntity;
import br.com.andorm.test.entity.DateTimeEntity;
import br.com.andorm.test.entity.PessoaFisica;
import br.com.andorm.test.entity.WrongDateTimeEntity;
import br.com.andorm.types.TemporalType;

public class PersistenceManagerFactoryTest extends AndroidTestCase {

	private final ResourceBundle bundle = ResourceBundleFactory.get();

	public void testMustThrowAExcpetion() {
		AndOrmConfiguration conf = new AndOrmConfiguration("path");
		conf.addEntity(Integer.class);
		try {
			PersistenceManagerFactory.create(conf);
			fail("must throw a exception");
		} catch (AndOrmException e) {
			assertEquals(e.getMessage(), MessageFormat.format(
					bundle.getString("is_not_a_entity"),
					Integer.class.getName()));
		}
	}

	public void testShouldReflectSuperClass() {
		AndOrmConfiguration conf = new AndOrmConfiguration("path");
		conf.addEntity(PessoaFisica.class);

		PersistenceManager manager = PersistenceManagerFactory.create(conf);
		Field cacheField = in(AndroidPersistenceManager.class).returnField(
				"cache");
		PersistenceManagerCache managerCache = (PersistenceManagerCache) getFieldValue(
				manager, cacheField);
		EntityCache cache = managerCache.getEntityCache(PessoaFisica.class);

		Property cpf = cache.getPropertyByField("cpf");
		assertNotNull(cpf);

		Property id = cache.getPropertyByField("id");
		assertNotNull(id);

		Property nome = cache.getPropertyByField("nome");
		assertNotNull(nome);

		Property endereco = cache.getPropertyByField("endereco");
		assertNotNull(endereco);

		Property email = cache.getPropertyByField("email");
		assertNotNull(email);
	}

	public void testShouldHaveDateTimeProperty() {
		AndOrmConfiguration conf = new AndOrmConfiguration("path");
		conf.addEntity(DateTimeEntity.class);

		PersistenceManager manager = PersistenceManagerFactory.create(conf);
		Field cacheField = in(AndroidPersistenceManager.class).returnField(
				"cache");
		PersistenceManagerCache managerCache = (PersistenceManagerCache) getFieldValue(
				manager, cacheField);
		EntityCache cache = managerCache.getEntityCache(DateTimeEntity.class);

		DateTimeProperty registerDate = (DateTimeProperty) cache
				.getPropertyByField("date");
		assertNotNull(registerDate);
		assertEquals(Date.class, registerDate.getField().getType());
		assertEquals(TemporalType.Date, registerDate.getType());
	}

	public void testShouldHaveAnnotatedDateTimeProperty() {
		AndOrmConfiguration conf = new AndOrmConfiguration("path");
		conf.addEntity(AnnotatedDateTimeEntity.class);

		PersistenceManager manager = PersistenceManagerFactory.create(conf);
		Field cacheField = in(AndroidPersistenceManager.class).returnField(
				"cache");
		PersistenceManagerCache managerCache = (PersistenceManagerCache) getFieldValue(
				manager, cacheField);
		EntityCache cache = managerCache
				.getEntityCache(AnnotatedDateTimeEntity.class);

		DateTimeProperty registerDate = (DateTimeProperty) cache
				.getPropertyByField("date");
		assertNotNull(registerDate);
		assertEquals(Date.class, registerDate.getField().getType());
		assertEquals(TemporalType.DateTime, registerDate.getType());
	}

	public void testShouldHaveOriginalNameTypes() {
		AndOrmConfiguration conf = new AndOrmConfiguration("path");
		conf.addEntity(new EntityConfiguration(PessoaFisica.class, NameTypes.Original));

		PersistenceManager manager = PersistenceManagerFactory.create(conf);
		Field cacheField = in(AndroidPersistenceManager.class).returnField(
				"cache");
		PersistenceManagerCache managerCache = (PersistenceManagerCache) getFieldValue(
				manager, cacheField);
		EntityCache cache = managerCache.getEntityCache(PessoaFisica.class);

		Property data = (Property) cache.getPropertyByField("dataCadastro");
		assertEquals(data.getColumnName(), "dataCadastro");
	}

	public void testShouldHaveUnderscoredNameTypes() {
		AndOrmConfiguration conf = new AndOrmConfiguration("path");
		conf.addEntity(new EntityConfiguration(PessoaFisica.class, NameTypes.Underscored));

		PersistenceManager manager = PersistenceManagerFactory.create(conf);
		Field cacheField = in(AndroidPersistenceManager.class).returnField(
				"cache");
		PersistenceManagerCache managerCache = (PersistenceManagerCache) getFieldValue(
				manager, cacheField);
		EntityCache cache = managerCache.getEntityCache(PessoaFisica.class);

		Property data = (Property) cache.getPropertyByField("dataCadastro");
		assertEquals(data.getColumnName(), "data_cadastro");
	}

	public void testWrongDateTimeType() {
		AndOrmConfiguration conf = new AndOrmConfiguration("path");
		conf.addEntity(WrongDateTimeEntity.class);

		try {
			PersistenceManagerFactory.create(conf);
		} catch (AndOrmException e) {
			assertEquals(MessageFormat.format(
					bundle.getString("wrong_date_time_type"),
					Calendar.class.getCanonicalName()), e.getMessage());
		}
	}

}