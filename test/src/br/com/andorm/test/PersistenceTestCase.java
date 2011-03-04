package br.com.andorm.test;

import br.com.andorm.AndOrmConfiguration;
import br.com.andorm.persistence.PersistenceManager;
import br.com.andorm.persistence.PersistenceManagerFactory;
import br.com.andorm.test.entity.Client;
import android.test.AndroidTestCase;


public class PersistenceTestCase extends AndroidTestCase {

	protected PersistenceManager manager;
	
	private AndOrmConfiguration configure() {
		AndOrmConfiguration config = new AndOrmConfiguration("/sdcard/database.sqlite");
		
		config.addEntity(Client.class);
		
		return config;
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		manager = PersistenceManagerFactory.create(configure());
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		manager = null;
		System.gc();
	}
	
}