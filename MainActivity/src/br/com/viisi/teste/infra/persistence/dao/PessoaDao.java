package br.com.viisi.teste.infra.persistence.dao;

import static br.com.andorm.query.Restriction.like;

import java.util.List;

import br.com.andorm.persistence.PersistenceManager;
import br.com.andorm.query.Criteria;
import br.com.viisi.teste.model.Pessoa;

public class PessoaDao {

	private final PersistenceManager manager;

	public PessoaDao(PersistenceManager manager) {
		super();
		this.manager = manager;
	}

	public void save(Pessoa cliente) {
		manager.save(cliente);
	}

	public void update(Pessoa cliente) {
		manager.update(cliente);
	}

	public void remove(Pessoa cliente) {
		manager.delete(cliente);
	}

	public Pessoa find(Integer id) {
		return manager.read(Pessoa.class, id);
	}

	public List<Pessoa> findAll() {
		Criteria query = Criteria.from(Pessoa.class);

		return manager.find(Pessoa.class, query);
	}

	public List<Pessoa> findByNome(String nome) {
		Criteria query = Criteria.from(Pessoa.class).where(like("nome", nome));

		return manager.find(Pessoa.class, query);
	}
}