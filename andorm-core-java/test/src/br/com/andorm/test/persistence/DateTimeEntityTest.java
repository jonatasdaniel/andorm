package br.com.andorm.test.persistence;

import java.util.Date;

import br.com.andorm.AndOrmConfiguration;
import br.com.andorm.persistence.PersistenceManager;
import br.com.andorm.persistence.PersistenceManagerFactory;
import br.com.andorm.test.entity.DateTimeEntity;

public class DateTimeEntityTest extends AndOrmPersistenceTestCase {

	public DateTimeEntityTest() {
		super(pm());
	}
	
	private static PersistenceManager pm() {
		AndOrmConfiguration conf = new AndOrmConfiguration("sdcard/andorm_db_test.sqlite");
		conf.addEntity(DateTimeEntity.class);
		
		return PersistenceManagerFactory.create(conf);
	}
	
	public void testShouldSaveDate() {
		DateTimeEntity entity = new DateTimeEntity();
		Date date = new Date();
		entity.setDate(date);
		
		manager.getTransaction().begin();
		manager.save(entity);
		
		DateTimeEntity returned = manager.read(DateTimeEntity.class, 1);
		
		assertEquals(returned.getDate(), date);
		
		manager.getTransaction().end();
	}
	
	public void testShouldSaveNullDate() {
		DateTimeEntity entity = new DateTimeEntity();
		
		manager.getTransaction().begin();
		manager.save(entity);
		
		DateTimeEntity returned = manager.read(DateTimeEntity.class, 1);
		
		assertNull(returned.getDate());
		
		manager.getTransaction().end();
	}

}