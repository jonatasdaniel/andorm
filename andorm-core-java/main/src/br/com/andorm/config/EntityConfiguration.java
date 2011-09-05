package br.com.andorm.config;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import br.com.andorm.AndOrmException;
import br.com.andorm.resources.ResourceBundleFactory;

public class EntityConfiguration {

	private final ResourceBundle bundle = ResourceBundleFactory.get();
	
	private final Class<?> entityClass;
	private Boolean verifyOperationMethods;

	public EntityConfiguration(Class<?> entityClass) {
		this(entityClass, true);
	}
	
	public EntityConfiguration(Class<?> entityClass, Boolean verifyOperationMethods) {
		if(entityClass != null) {
			this.entityClass = entityClass;
		} else {
			throw new AndOrmException(MessageFormat.format(bundle.getString("null_param"), "entityClass"));
		}
		
		if(verifyOperationMethods != null) {
			this.verifyOperationMethods = verifyOperationMethods;
		} else {
			throw new AndOrmException(MessageFormat.format(bundle.getString("null_param"), "verifyOperationMethods"));
		}
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public Boolean getVerifyOperationMethods() {
		return verifyOperationMethods;
	}

	public void setVerifyOperationMethods(Boolean verifyOperationMethods) {
		this.verifyOperationMethods = verifyOperationMethods;
	}

}