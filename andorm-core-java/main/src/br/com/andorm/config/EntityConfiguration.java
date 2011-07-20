package br.com.andorm.config;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import resources.ResourceBundleFactory;

import br.com.andorm.AndOrmException;
import br.com.andorm.provider.DefaultProvider;
import br.com.andorm.provider.Provider;

public class EntityConfiguration {

	private final ResourceBundle bundle = ResourceBundleFactory.get();
	
	private final Class<?> entityClass;
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
		this(entityClass, nameTypes, verifyOperationMethods,
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
		if(entityClass != null) {
			this.entityClass = entityClass;
		} else {
			throw new AndOrmException(MessageFormat.format(bundle.getString("null_param"), "entityClass"));
		}
		if(nameTypes != null) {
			this.nameTypes = nameTypes;
		} else {
			throw new AndOrmException(MessageFormat.format(bundle.getString("null_param"), "nameTypes"));
		}
		if(verifyOperationMethods != null) {
			this.verifyOperationMethods = verifyOperationMethods;
		} else {
			throw new AndOrmException(MessageFormat.format(bundle.getString("null_param"), "verifyOperationMethods"));
		}
		if(provider != null) {
			this.provider = provider;
		} else {
			throw new AndOrmException(MessageFormat.format(bundle.getString("null_param"), "provider"));
		}
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

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

}