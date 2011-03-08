package br.com.andorm.persistence;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.andorm.PrimaryKeyProperty;

/**
 * 
 * @author jonatasdaniel
 * @since 04/03/2011
 * @version 0.1
 * 
 */
public final class EntityCache {

	private final Class<?>			entityClass;
	private final String			tableName;
	private Property				pk;

	private List<String>			columns;
	private List<String>			columnsWithoutAutoInc;

	private Map<String, Property>	columnProperties;
	private Map<String, Property>	fieldProperties;

	public EntityCache(Class<?> entityClass, String tableName) {
		this.entityClass = entityClass;
		this.tableName = tableName;

		columns = new ArrayList<String>();
		columnProperties = new HashMap<String, Property>();
		fieldProperties = new HashMap<String, Property>();
	}

	protected void setPk(PrimaryKeyProperty pk) {
		if(!pk.isAutoInc())
			columnsWithoutAutoInc.add(pk.getColumnName());
		add(pk);
	}
	
	protected void add(Property property) {
		columns.add(property.getColumnName());
		columnProperties.put(property.getColumnName(), property);
		fieldProperties.put(property.getField().getName(), property);
	}

	protected Property getPropertyByColumn(String column) {
		return columnProperties.get(column);
	}

	protected Property getPropertyByField(String field) {
		return fieldProperties.get(field);
	}

	public String getTableName() {
		return tableName;
	}

	protected Property getPk() {
		return pk;
	}

	protected void setPk(Property pk) {
		this.pk = pk;
	}

	protected List<String> getColumns() {
		return columns;
	}

	protected List<String> getColumnsWithoutAutoInc() {
		return columnsWithoutAutoInc;
	}

	protected Map<String, Property> getColumnProperties() {
		return columnProperties;
	}

	protected Map<String, Property> getFieldProperties() {
		return fieldProperties;
	}

	protected Class<?> getEntityClass() {
		return entityClass;
	}
}