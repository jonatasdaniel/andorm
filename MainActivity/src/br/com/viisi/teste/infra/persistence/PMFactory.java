package br.com.viisi.teste.infra.persistence;

import android.content.Context;
import br.com.andorm.config.AndOrmConfiguration;
import br.com.andorm.config.EntityConfiguration;
import br.com.andorm.persistence.PersistenceManager;
import br.com.andorm.persistence.PersistenceManagerFactory;
import br.com.viisi.teste.model.Pessoa;

public class PMFactory {

	private static PersistenceManager instance;

	private PMFactory() {
	}

	public static PersistenceManager get(Context context) {
		if (instance == null) {
			instance = PersistenceManagerFactory.createPMDatabaseAndTablesIfNotExists(configure(), context);
		}

		return instance;
	}

	private static AndOrmConfiguration configure() {
		AndOrmConfiguration conf = new AndOrmConfiguration("testexx.db");

		// true - Verifica metodos de @BeforeInsert, etc...
		EntityConfiguration ecPessoa = new EntityConfiguration(Pessoa.class, true);
		conf.addEntity(ecPessoa);

		return conf;
	}

}