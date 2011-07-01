package br.com.andorm.test.operation;

import br.com.andorm.config.AndOrmConfiguration;
import br.com.andorm.persistence.PersistenceManager;
import br.com.andorm.persistence.PersistenceManagerFactory;
import br.com.andorm.test.entity.OperationsEntity;
import br.com.andorm.test.persistence.AndOrmPersistenceTestCase;

public class OperationExecutionTest extends AndOrmPersistenceTestCase {

	public OperationExecutionTest() {
		super(create());
	}

	private static PersistenceManager create() {
		AndOrmConfiguration conf = new AndOrmConfiguration(databasePath());
		conf.addEntity(OperationsEntity.class);
		
		return PersistenceManagerFactory.create(conf);
	}
}