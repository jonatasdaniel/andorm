package br.com.andorm;

import br.com.andorm.entity.Client;
import br.com.andorm.persistence.AndOrmPersistenceException;


public class ClientEntityTest extends PersistenceTestCase {

	public void testSave() {
		Client client = new Client("Rua das palmeiras", "João da Cunha", true);
		
		try {
			manager.save(client);
		} catch(AndOrmPersistenceException e) {
			fail(e.getMessage());
		}
		
		Client readed = manager.get(Client.class, 1);
		assertEquals(readed, client);
	}
	
	public void testUpdate() {
		Client client = new Client("Rua das palmeiras", "João da Cunha", true);
		
		try {
			manager.save(client);
		} catch(AndOrmPersistenceException e) {
			fail(e.getMessage());
		}
		
		Client readed = manager.get(Client.class, 1);
		readed.setEndereco("Other address");
		readed.setNome("A name");
		
		try {
			manager.update(readed);
		} catch(AndOrmPersistenceException e) {
			fail(e.getMessage());
		}
		
		client = manager.get(Client.class, 1);
		assertEquals(client, readed);
	}
	
}