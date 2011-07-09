package br.com.andorm.example.infra.persistence;

import java.util.List;

import br.com.andorm.example.model.entity.Cliente;
import br.com.andorm.persistence.PersistenceManager;
import br.com.andorm.query.Criteria;
import static br.com.andorm.query.Restriction.*;

public class ClienteDao {

	private final PersistenceManager manager;

	public ClienteDao(PersistenceManager manager) {
		super();
		this.manager = manager;
	}

	public void save(Cliente cliente) {
		manager.save(cliente);
	}
	
	public void update(Cliente cliente) {
		manager.update(cliente);
	}
	
	public void remove(Cliente cliente) {
		manager.delete(cliente);
	}
	
	public Cliente find(Integer id) {
		return manager.read(Cliente.class, id);
	}
	
	public List<Cliente> findAll() {
		Criteria query = Criteria.from(Cliente.class);
		
		return manager.find(Cliente.class, query);
	}
	
	public List<Cliente> findByNome(String nome) {
		Criteria query = Criteria.from(Cliente.class).where(like("nome", nome));
		
		return manager.find(Cliente.class, query);
	}
}