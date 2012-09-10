package br.com.andorm.config;

import java.util.ArrayList;
import java.util.List;

public class AndOrmConfig {

	private final String databasePath;
	private final List<EntityConfig> entities;

	public AndOrmConfig(String databasePath) {
		this.databasePath = databasePath;

		entities = new ArrayList<EntityConfig>();
	}

	public void addEntity(EntityConfig config) {
		entities.add(config);
	}

	public String getDatabasePath() {
		return databasePath;
	}

	public List<EntityConfig> getEntities() {
		return entities;
	}

}