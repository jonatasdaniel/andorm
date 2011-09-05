package br.com.andorm.persistence.tablemanager;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import br.com.andorm.AndOrmException;
import br.com.andorm.property.BooleanProperty;
import br.com.andorm.property.PrimaryKeyProperty;
import br.com.andorm.property.Property;
import br.com.andorm.resources.ResourceBundleFactory;

public class PropertyCreationQueryBuilder {

	private final Map<Class<?>, String> types;
	
	private final ResourceBundle bundle = ResourceBundleFactory.get();

	public PropertyCreationQueryBuilder() {
		types = new HashMap<Class<?>, String>();
		
		createTypes();
	}
	
	private void createTypes() {
		types.put(String.class, "TEXT");
		types.put(Date.class, "LONG");
		types.put(Double.class, "REAL");
		types.put(Float.class, "REAL");
		types.put(BigDecimal.class, "REAL");
		types.put(Integer.class, "INTEGER");
		types.put(Long.class, "INTEGER");
		types.put(Boolean.class, "BOOL");
		
	}
	
	public String build(Property property) {
		StringBuilder builder = new StringBuilder();
		
		Class<?> type = property.getDatabaseFieldType();
		if(property instanceof BooleanProperty) {
			type = Boolean.class;
		}
		String typeName = types.get(type);
		if(typeName == null)
			throw new AndOrmException(MessageFormat.format(bundle.getString("type_not_supported"), type.getCanonicalName()));
		
		builder.append(property.getColumnName()).append(" ");
		builder.append(typeName);
		
		if(property instanceof PrimaryKeyProperty) {
			PrimaryKeyProperty pk = (PrimaryKeyProperty) property;
			builder.append(" PRIMARY KEY");
			if(pk.isAutoInc()) {
				builder.append(" AUTOINCREMENT");
			}
		}
		
		if(!property.isNullable()) {
			builder.append(" NOT NULL");
		}
		
		return builder.toString();
	}

}