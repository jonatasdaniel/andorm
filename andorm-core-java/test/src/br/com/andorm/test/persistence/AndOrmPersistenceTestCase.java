package br.com.andorm.test.persistence;

import br.com.andorm.persistence.PersistenceManager;
import android.test.AndroidTestCase;

public class AndOrmPersistenceTestCase extends AndroidTestCase {

	protected final PersistenceManager manager;
	
	public AndOrmPersistenceTestCase(PersistenceManager manager) {
		this.manager = manager;
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		manager.open();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		manager.close();
	}
	
}
