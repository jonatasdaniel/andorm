package br.com.andorm;

import br.com.andorm.AndOrmConfiguration;
import br.com.andorm.entity.Client;
import br.com.andorm.persistence.PersistenceManager;
import br.com.andorm.persistence.PersistenceManagerFactory;
import android.test.AndroidTestCase;


public class PersistenceTestCase extends AndroidTestCase {

	protected PersistenceManager manager;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		manager = PersistenceManagerFactory.create(configure());
		
		manager.getTransaction().begin();
	}
	
	private AndOrmConfiguration configure() {
		AndOrmConfiguration config = new AndOrmConfiguration("/sdcard/database.sqlite");
		
		config.addEntity(Client.class);
		
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