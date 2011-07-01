package br.com.andorm.config;

public class EntityConfiguration {

	private Class<?> entityClass;
	private NameTypes nameTypes;
	private Boolean verifyOperationMethods;

	public EntityConfiguration(Class<?> entityClass) {
		this(entityClass, NameTypes.Original, true);
	}

	public EntityConfiguration(Class<?> entityClass, NameTypes nameTypes) {
		this(entityClass, nameTypes, true);
	}

	public EntityConfiguration(Class<?> entityClass, NameTypes nameTypes,
			Boolean verifyOperationMethods) {
		super();
		this.entityClass = entityClass;
		this.nameTypes = nameTypes;
		this.verifyOperationMethods = verifyOperationMethods;
	}

	public Class<?> getEntityClass() {
		return entityClass;
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

}