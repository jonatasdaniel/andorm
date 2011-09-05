package br.com.andorm.persistence.tablemanager;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import br.com.andorm.persistence.AndOrmPersistenceException;
import br.com.andorm.persistence.EntityCache;
import br.com.andorm.persistence.PersistenceManagerCache;
import br.com.andorm.property.Property;
import br.com.andorm.resources.ResourceBundleFactory;

public class TableCreationQueryBuilder {

	private final PersistenceManagerCache cache;
	private final PropertyCreationQueryBuilder propertyCreationBuilder;
	
	private final ResourceBundle bundle = ResourceBundleFactory.get();

	public TableCreationQueryBuilder(PersistenceManagerCache cache, PropertyCreationQueryBuilder propertyCreationBuilder) {
		this.cache = cache;
		this.propertyCreationBuilder = propertyCreationBuilder;
	}
	
	public String build(Class<?> entityClass) {
		EntityCache cache = this.cache.getEntityCache(entityClass);
		if(cache == null)
			throw new AndOrmPersistenceException(MessageFormat.format(bundle.getString("is_not_a_entity"), entityClass.getCanonicalName()));
		
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE ");
		builder.append(cache.getTableName());
		builder.append(" ");
		if(!cache.getColumns().isEmpty())
			builder.append("( ");
		
		builder.append(propertyCreationBuilder.build(cache.getPk()));
		
		List<String> columns = cache.getColumns();
		Collections.sort(columns);
		
		for(String column : columns) {
			
			Property property = cache.getPropertyByColumn(column);
			if(property == cache.getPk())
				continue;
			
			builder.append(", ");
			builder.append(propertyCreationBuilder.build(property));
		}
		
		if(!cache.getColumns().isEmpty())
			builder.append(" );");
		
		return builder.toString();
	}
	
}