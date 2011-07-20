package br.com.andorm.config;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import resources.ResourceBundleFactory;
import br.com.andorm.AndOrmException;

/**
 * 
 * @author jonatasdaniel
 * @since 04/03/2011
 * @version 0.1
 * 
 */
public class AndOrmConfiguration {

	private final String databasePath;
	private final List<EntityConfiguration> entityConfigurations;

	private final ResourceBundle bundle = ResourceBundleFactory.get();

	public AndOrmConfiguration(String databasePath) {
		entityConfigurations = new ArrayList<EntityConfiguration>();
		if (databasePath != null && databasePath.length() > 0)
			this.databasePath = databasePath;
		else
			throw new AndOrmException(bundle.getString("invalid_database_path"));
	}

	public void addEntity(Class<?> entityClass) {
		EntityConfiguration configuration = new EntityConfiguration(entityClass);
		addEntity(configuration);
	}

	public void addEntity(EntityConfiguration configuration) {
		entityConfigurations.add(configuration);
	}

	public String getDatabasePath() {
		return databasePath;
	}

	public List<EntityConfiguration> getEntityConfigurations() {
		return entityConfigurations;
	}

}