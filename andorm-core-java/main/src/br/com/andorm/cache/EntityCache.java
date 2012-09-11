package br.com.andorm.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.andorm.property.FieldProperty;
import br.com.andorm.property.PrimaryKeyFieldProperty;
import br.com.andorm.provider.Provider;

/**
 * 
 * @author jonatas-daniel
 *
 */
public class EntityCache {

	private final Class<?> entityClass;
	private final String tableName;
	private final Provider provider;
	private PrimaryKeyFieldProperty pk;
	private final List<String> columns;
	private final List<String> columnsWithoutAutoInc;
	private final Map<String, FieldProperty> columnsToProperties;
	private final Map<String, FieldProperty> fieldsToProperties;

	public EntityCache(Class<?> klass, String tableName, Provider provider) {
		this.entityClass = klass;
		this.tableName = tableName;
		this.provider = provider;
		
		columns = new ArrayList<String>();
		columnsWithoutAutoInc = new ArrayList<String>();
		columnsToProperties = new HashMap<String, FieldProperty>();
		fieldsToProperties = new HashMap<String, FieldProperty>();
	}

	public FieldProperty propertyByColumn(String column) {
		return columnsToProperties.get(column);
	}

	public FieldProperty propertyByField(String field) {
		return fieldsToProperties.get(field);
	}

	public Provider getProvider() {
		return provider;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public String getTableName() {
		return tableName;
	}

	public PrimaryKeyFieldProperty getPk() {
		return pk;
	}

	public String[] getColumns() {
		String[] columns = new String[this.columns.size()];
		return this.columns.toArray(columns);
	}

	public String[] getColumnsWithoutAutoInc() {
		String[] columns = new String[this.columnsWithoutAutoInc.size()];
		return this.columnsWithoutAutoInc.toArray(columns);
	}

	public void setPk(PrimaryKeyFieldProperty property) {
		this.pk = property;
		columns.add(property.getColumnName());
		if (!property.isAutoInc()) {
			columnsWithoutAutoInc.add(property.getColumnName());
		}
		columnsToProperties.put(property.getColumnName(), property);
		fieldsToProperties.put(property.getField().getName(), property);
	}

	public void addFieldProperty(FieldProperty property) {
		columns.add(property.getColumnName());
		columnsWithoutAutoInc.add(property.getColumnName());
		columnsToProperties.put(property.getColumnName(), property);
		fieldsToProperties.put(property.getField().getName(), property);
	}

}