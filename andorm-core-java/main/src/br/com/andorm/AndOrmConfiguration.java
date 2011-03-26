package br.com.andorm;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import resources.ResourceBundleFactory;

/**
 * 
 * @author jonatasdaniel
 * @since 04/03/2011
 * @version 0.1
 *
 */
public class AndOrmConfiguration {

	private final String databasePath;
	private final List<Class<?>> entities;
	
	private final ResourceBundle bundle = ResourceBundleFactory.get();
	
	public AndOrmConfiguration(String databasePath) {
		entities = new ArrayList<Class<?>>();
		if(databasePath != null)
			this.databasePath = databasePath;
		else
			throw new AndOrmException(bundle.getString("invalid_database_path"));
	}
	
	public void addEntity(Class<?> entityClass) {
		entities.add(entityClass);
	}
	
	public String getDatabasePath() {
		return databasePath;
	}
	
	public List<Class<?>> getEntities() {
		return entities;
	}
}