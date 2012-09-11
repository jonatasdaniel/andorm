package br.com.andorm.config;

/**
 * 
 * @author jonatas-daniel
 *
 */
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