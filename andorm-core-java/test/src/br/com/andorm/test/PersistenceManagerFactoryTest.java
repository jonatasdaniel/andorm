package br.com.andorm.test;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import resources.ResourceBundleFactory;
import android.test.AndroidTestCase;
import br.com.andorm.AndOrmConfiguration;
import br.com.andorm.AndOrmException;
import br.com.andorm.persistence.AndroidPersistenceManager;
import br.com.andorm.persistence.EntityCache;
import br.com.andorm.persistence.PersistenceManager;
import br.com.andorm.persistence.PersistenceManagerCache;
import br.com.andorm.persistence.PersistenceManagerFactory;
import br.com.andorm.persistence.property.Property;
import br.com.andorm.test.entity.PessoaFisica;

import static br.com.andorm.reflection.Reflactor.*;


public class PersistenceManagerFactoryTest extends AndroidTestCase {
	
	private final ResourceBundle bundle = ResourceBundleFactory.get();
	
	public void testMustThrowAExcpetion() {
		AndOrmConfiguration conf = new AndOrmConfiguration("path");
		conf.addEntity(Integer.class);
		try {
			PersistenceManagerFactory.create(conf);
			fail("must throw a exception");
		} catch(AndOrmException e) {
			assertEquals(e.getMessage(), MessageFormat.format(bundle.getString("is_not_a_entity"), Integer.class.getName()));
		}
	}
	
	public void testShouldReflectSuperClass() {
		AndOrmConfiguration conf = new AndOrmConfiguration("path");
		conf.addEntity(PessoaFisica.class);
		
		PersistenceManager manager = PersistenceManagerFactory.create(conf);
		Field cacheField = in(AndroidPersistenceManager.class).returnField("cache");
		PersistenceManagerCache managerCache = (PersistenceManagerCache) getFieldValue(manager, cacheField);
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
	
}