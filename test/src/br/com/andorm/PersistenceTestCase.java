package br.com.andorm;

import android.test.AndroidTestCase;
import br.com.andorm.entity.BasicClient;
import br.com.andorm.persistence.PersistenceManager;
import br.com.andorm.persistence.PersistenceManagerFactory;


public class PersistenceTestCase extends AndroidTestCase {

	protected PersistenceManager manager;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		manager = PersistenceManagerFactory.create(configure());
		
		manager.open();
		manager.getTransaction().begin();
	}
	
	private AndOrmConfiguration configure() {
		AndOrmConfiguration config = new AndOrmConfiguration("/sdcard/andorm_db_test.sqlite");
		
		config.addEntity(BasicClient.class);
		
		return config;
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		manager.getTransaction().end();
		
		manager = null;
		System.gc();
	}
	
}