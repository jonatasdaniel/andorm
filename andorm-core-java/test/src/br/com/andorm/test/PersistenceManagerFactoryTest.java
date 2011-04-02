package br.com.andorm.test;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import resources.ResourceBundleFactory;
import android.test.AndroidTestCase;
import br.com.andorm.AndOrmConfiguration;
import br.com.andorm.AndOrmException;
import br.com.andorm.persistence.PersistenceManagerFactory;


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
	
}