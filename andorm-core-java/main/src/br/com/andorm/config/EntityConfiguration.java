package br.com.andorm.config;

import br.com.andorm.provider.DefaultProvider;
import br.com.andorm.provider.Provider;

public class EntityConfiguration {

	private Class<?> entityClass;
	private NameTypes nameTypes;
	private Boolean verifyOperationMethods;
	private Provider provider;

	public EntityConfiguration(Class<?> entityClass) {
		this(entityClass, NameTypes.Underscored, true, new DefaultProvider());
	}

	public EntityConfiguration(Class<?> entityClass, NameTypes nameTypes) {
		this(entityClass, nameTypes, true, new DefaultProvider());
	}

	public EntityConfiguration(Class<?> entityClass, NameTypes nameTypes,
			Boolean verifyOperationMethods) {
		this(entityClass, NameTypes.Underscored, verifyOperationMethods,
				new DefaultProvider());
	}

	public EntityConfiguration(Class<?> entityClass,
			Boolean verifyOperationMethods) {
		this(entityClass, NameTypes.Underscored, verifyOperationMethods,
				new DefaultProvider());
	}

	public EntityConfiguration(Class<?> entityClass, Provider provider) {
		this(entityClass, NameTypes.Underscored, true, provider);
	}

	public EntityConfiguration(Class<?> entityClass, NameTypes nameTypes,
			Boolean verifyOperationMethods, Provider provider) {
		this.entityClass = entityClass;
		this.nameTypes = nameTypes;
		this.verifyOperationMethods = verifyOperationMethods;
		this.provider = provider;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	public NameTypes getNameTypes() {
		return nameTypes;
	}

	public void setNameTypes(NameTypes nameTypes) {
		this.nameTypes = nameTypes;
	}

	public Boolean getVerifyOperationMethods() {
		return verifyOperationMethods;
	}

	public void setVerifyOperationMethods(Boolean verifyOperationMethods) {
		this.verifyOperationMethods = verifyOperationMethods;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

}