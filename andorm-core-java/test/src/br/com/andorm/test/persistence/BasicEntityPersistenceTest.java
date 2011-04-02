package br.com.andorm.test.persistence;

import br.com.andorm.AndOrmConfiguration;
import br.com.andorm.persistence.PersistenceManager;
import br.com.andorm.persistence.PersistenceManagerFactory;
import br.com.andorm.test.entity.BasicEntity;

public class BasicEntityPersistenceTest extends AndOrmPersistenceTestCase {
	
	public BasicEntityPersistenceTest() {
		super(createPersistenceManager());
	}
	
	private static PersistenceManager createPersistenceManager() {
		AndOrmConfiguration conf = new AndOrmConfiguration("sdcard/andorm_db_test.sqlite");
		conf.addEntity(BasicEntity.class);
		
		return PersistenceManagerFactory.create(conf);
	}
	
	public void testSaveOneObject() {
		BasicEntity entity = new BasicEntity("a name", "an address");
		manager.getTransaction().begin();
		manager.save(entity);
		
		BasicEntity returned = manager.read(BasicEntity.class, 1);
		
		assertNotNull(returned);
		assertEquals(entity, returned);
		
		manager.getTransaction().end();
	}
	
	public void testSaveTwoObjects() {
		BasicEntity firstEntity = new BasicEntity("a name of first entity", "an address of first entity");
		manager.getTransaction().begin();
		manager.save(firstEntity);
		
		BasicEntity secondEntity = new BasicEntity("a name of second entity", "an address of second entity");
		manager.save(secondEntity);
		
		BasicEntity returnedFirst = manager.read(BasicEntity.class, 1);
		BasicEntity returnedSecond = manager.read(BasicEntity.class, 2);
		
		assertNotNull(firstEntity);
		assertEquals(firstEntity, returnedFirst);
		
		assertNotNull(secondEntity);
		assertEquals(secondEntity, returnedSecond);
		
		manager.getTransaction().end();
	}
	
	public  void testUpdate() {
		BasicEntity entity = new BasicEntity("a name", "an address");
		manager.getTransaction().begin();
		manager.save(entity);
		
		BasicEntity returned = manager.read(BasicEntity.class, 1);
		assertNotNull(returned);
		assertEquals(entity, returned);
		
		entity.setId(returned.getId());
		entity.setEndereco("a new address");
		entity.setNome("a new name");
		
		manager.update(entity);
		
		returned = manager.read(BasicEntity.class, 1);
		assertNotNull(returned);
		assertEquals(entity, returned);
		
		manager.getTransaction().end();
	}
	
	public void testDelete() {
		BasicEntity entity = new BasicEntity("a name", "an address");
		manager.getTransaction().begin();
		manager.save(entity);
		
		assertEquals(1, manager.count(BasicEntity.class));
		
		entity = manager.read(BasicEntity.class, 1);
		manager.delete(entity);
		
		assertEquals(0, manager.count(BasicEntity.class));
		
		manager.getTransaction().end();
	}

}