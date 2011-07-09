package br.com.andorm.example.infra.persistence;

import br.com.andorm.config.AndOrmConfiguration;
import br.com.andorm.example.model.entity.Cliente;
import br.com.andorm.example.model.entity.PessoaFisica;
import br.com.andorm.example.model.entity.PessoaJuridica;
import br.com.andorm.persistence.PersistenceManager;
import br.com.andorm.persistence.PersistenceManagerFactory;

public class PMFactory {

	private static PersistenceManager instance;
	
	private PMFactory() {}
	
	public static PersistenceManager get() {
		if(instance == null) {
			instance = PersistenceManagerFactory.create(configure());
		}
		
		return instance;
	}
	
	private static AndOrmConfiguration configure() {
		AndOrmConfiguration conf = new AndOrmConfiguration("path_do_banco");
		
		conf.addEntity(Cliente.class);
		conf.addEntity(PessoaFisica.class);
		conf.addEntity(PessoaJuridica.class);
		
		return conf;
	}
	
}