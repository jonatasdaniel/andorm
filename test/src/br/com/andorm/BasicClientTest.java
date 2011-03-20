package br.com.andorm;

import java.util.Arrays;
import java.util.List;

import com.jonatasdaniel.criteria.Criteria;

import br.com.andorm.entity.BasicClient;
import br.com.andorm.persistence.AndOrmPersistenceException;

import static com.jonatasdaniel.criteria.Restriction.*;


public class BasicClientTest extends PersistenceTestCase {

	public void testSave() {
		BasicClient client = new BasicClient("Rua das palmeiras", "João da Cunha");
		
		try {
			manager.save(client);
		} catch(AndOrmPersistenceException e) {
			fail(e.getMessage());
		}
		
		BasicClient readed = manager.read(BasicClient.class, 1);
		assertEquals(readed, client);
	}
	
	public void testUpdate() {
		BasicClient client = new BasicClient("Rua das palmeiras", "João da Cunha");
		
		try {
			manager.save(client);
		} catch(AndOrmPersistenceException e) {
			fail(e.getMessage());
		}
		
		BasicClient readed = manager.read(BasicClient.class, 1);
		readed.setEndereco("Other address");
		readed.setNome("A name");
		
		try {
			manager.update(readed);
		} catch(AndOrmPersistenceException e) {
			fail(e.getMessage());
		}
		
		client = manager.read(BasicClient.class, 1);
		assertEquals(client, readed);
	}
	
	public void testDelete() {
		BasicClient client = new BasicClient("Rua das palmeiras", "João da Cunha");
		
		try {
			manager.save(client);
		} catch(AndOrmPersistenceException e) {
			fail(e.getMessage());
		}
		
		client = manager.read(BasicClient.class, 1);
		try {
			manager.delete(client);
		} catch(AndOrmPersistenceException e) {
			fail(e.getMessage());
		}
		
		assertNull(manager.read(BasicClient.class, 1));
	}
	
	public void testFilter() {
		try {
			manager.save(new BasicClient("Rua das montanhas", "Alfredo Silva"));
		} catch(AndOrmPersistenceException e) {
			fail(e.getMessage());
		}
		
		Criteria criteria = new Criteria(BasicClient.class);
		criteria.where(like("nome", "Alfredo%"));
		
		List<BasicClient> returned = (List<BasicClient>) manager.list(criteria);
		assertTrue(returned.size() == 1);
		assertTrue(isEqual(returned, Arrays.asList(new BasicClient("Rua das montanhas", "Alfredo Silva"))));
	}
	
	private boolean isEqual(List<BasicClient> received, List<BasicClient> expected) {
		if(received == null)
			return expected == null;
		else if(expected == null)
			return received == null;
		else if(received.size() != expected.size())
			return false;
		else {
			for(int i = 0; i < expected.size(); i++)
				if(!expected.get(i).equals(received.get(i)))
					return false;
			
			return true;
		}
	}
	
}