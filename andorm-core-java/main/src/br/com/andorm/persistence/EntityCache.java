package br.com.andorm.persistence;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.andorm.persistence.property.PrimaryKeyProperty;
import br.com.andorm.persistence.property.Property;
import br.com.andorm.provider.Provider;

/**
 * 
 * @author jonatasdaniel
 * @since 04/03/2011
 * @version 0.1
 * 
 */
public final class EntityCache {

	private final Class<?> entityClass;
	private final String tableName;
	private PrimaryKeyProperty pk;

	private final Provider provider;
	
	private Method beforeSaveMethod;
	private Method afterSaveMethod;
	private Method beforeUpdateMethod;
	private Method afterUpdateMethod;
	private Method beforeDeleteMethod;
	private Method afterDeleteMethod;

	private List<String> columns;
	private List<String> columnsWithoutAutoInc;

	private Map<String, Property> columnProperties;
	private Map<String, Property> fieldProperties;

	public EntityCache(Class<?> entityClass, String tableName, Provider provider) {
		this.entityClass = entityClass;
		this.tableName = tableName;
		this.provider = provider;

		columns = new ArrayList<String>();
		columnProperties = new HashMap<String, Property>();
		fieldProperties = new HashMap<String, Property>();
		columnsWithoutAutoInc = new ArrayList<String>();
	}

	protected void setPk(PrimaryKeyProperty pk) {
		this.pk = pk;

		add(pk);
	}

	protected void add(Property property) {
		columns.add(property.getColumnName());
		columnProperties.put(property.getColumnName(), property);
		fieldProperties.put(property.getField().getName(), property);

		if (property instanceof PrimaryKeyProperty) {
			PrimaryKeyProperty pk = (PrimaryKeyProperty) property;
			if (!pk.isAutoInc())
				columnsWithoutAutoInc.add(pk.getColumnName());
		} else {
			columnsWithoutAutoInc.add(property.getColumnName());
		}
	}

	public Property getPropertyByColumn(String column) {
		return columnProperties.get(column);
	}

	public Property getPropertyByField(String field) {
		return fieldProperties.get(field);
	}

	public Method getBeforeSaveMethod() {
		return beforeSaveMethod;
	}

	public void setBeforeSaveMethod(Method beforeSaveMethod) {
		this.beforeSaveMethod = beforeSaveMethod;
	}

	public Method getAfterSaveMethod() {
		return afterSaveMethod;
	}

	public void setAfterSaveMethod(Method afterSaveMethod) {
		this.afterSaveMethod = afterSaveMethod;
	}

	public Method getBeforeUpdateMethod() {
		return beforeUpdateMethod;
	}

	public void setBeforeUpdateMethod(Method beforeUpdateMethod) {
		this.beforeUpdateMethod = beforeUpdateMethod;
	}

	public Method getAfterUpdateMethod() {
		return afterUpdateMethod;
	}

	public void setAfterUpdateMethod(Method afterUpdateMethod) {
		this.afterUpdateMethod = afterUpdateMethod;
	}

	public Method getBeforeDeleteMethod() {
		return beforeDeleteMethod;
	}

	public void setBeforeDeleteMethod(Method beforeDeleteMethod) {
		this.beforeDeleteMethod = beforeDeleteMethod;
	}

	public Method getAfterDeleteMethod() {
		return afterDeleteMethod;
	}

	public void setAfterDeleteMethod(Method afterDeleteMethod) {
		this.afterDeleteMethod = afterDeleteMethod;
	}

	public String getTableName() {
		return tableName;
	}

	public PrimaryKeyProperty getPk() {
		return pk;
	}

	public List<String> getColumns() {
		return columns;
	}

	public List<String> getColumnsWithoutAutoInc() {
		return columnsWithoutAutoInc;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public Provider getProvider() {
		return provider;
	}

}