package br.com.andorm;

import br.com.andorm.entity.BasicClient;
import br.com.andorm.persistence.AndOrmPersistenceException;


public class BasicClientTest extends PersistenceTestCase {

	public void testSave() {
		BasicClient client = new BasicClient("Rua das palmeiras", "João da Cunha");
		
		try {
			manager.save(client);
		} catch(AndOrmPersistenceException e) {
			fail(e.getMessage());
		}
		
		BasicClient readed = manager.get(BasicClient.class, 1);
		assertEquals(readed, client);
	}
	
	public void testUpdate() {
		BasicClient client = new BasicClient("Rua das palmeiras", "João da Cunha");
		
		try {
			manager.save(client);
		} catch(AndOrmPersistenceException e) {
			fail(e.getMessage());
		}
		
		BasicClient readed = manager.get(BasicClient.class, 1);
		readed.setEndereco("Other address");
		readed.setNome("A name");
		
		try {
			manager.update(readed);
		} catch(AndOrmPersistenceException e) {
			fail(e.getMessage());
		}
		
		client = manager.get(BasicClient.class, 1);
		assertEquals(client, readed);
	}
	
}