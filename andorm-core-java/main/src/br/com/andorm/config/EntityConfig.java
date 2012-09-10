package br.com.andorm.config;

public class EntityConfig {

	private final Class<?> entityClass;

	public EntityConfig(Class<?> entityClass) {
		super();
		this.entityClass = entityClass;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

}