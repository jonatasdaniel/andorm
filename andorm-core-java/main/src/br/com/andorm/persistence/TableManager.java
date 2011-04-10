package br.com.andorm.persistence;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import resources.ResourceBundleFactory;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.PopupWindow;
import br.com.andorm.AndOrmException;
import br.com.andorm.persistence.property.PrimaryKeyProperty;
import br.com.andorm.persistence.property.Property;

/**
 * 
 * @author jonatasdaniel
 * @since 08/03/2011
 * @version 0.1
 *
 */
public final class TableManager {

	private final PersistenceManagerCache cache;
	private final SQLiteDatabase database;
	
	private final Map<Class<?>, String> types;
	
	private final ResourceBundle bundle = ResourceBundleFactory.get();

	protected TableManager(PersistenceManagerCache cache, SQLiteDatabase database) {
		this.cache = cache;
		this.database = database;
		
		types = new HashMap<Class<?>, String>();
		
		types();
	}
	
	private void types() {
		types.put(String.class, "TEXT");
		types.put(Date.class, "LONG");
		types.put(Double.class, "REAL");
		types.put(Float.class, "REAL");
		types.put(Integer.class, "INTEGER");
		types.put(Long.class, "INTEGER");
		
	}

	public void create(Class<?> entityClass) {
		EntityCache cache = this.cache.getEntityCache(entityClass);
		if(cache == null)
			throw new AndOrmException(MessageFormat.format(bundle.getString("is_not_a_entity"), entityClass.getCanonicalName()));
		
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE ").append(cache.getTableName());
		builder.append(" ( ");
		
		boolean firstTime = true;
		
		for(String column : cache.getColumns()) {
			if(!firstTime)
				builder.append(", ");
			else
				firstTime = false;
			
			Property property = cache.getPropertyByColumn(column);
			
			builder.append(buildColumnStatement(column, property));
		}
		
		builder.append(" );");
		
		final String sql = builder.toString();
		
		try {
			database.execSQL(sql);
		} catch(SQLiteException e) {
			
		}
	}
	
	private String buildColumnStatement(String columnName, Property property) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(columnName).append(" ");
		Class<?> type = property.getField().getType();
		String typeName = types.get(type);
		
		if(typeName != null) {
			builder.append(" ");
			builder.append(typeName);
			builder.append(" ");
			
			if(property instanceof PrimaryKeyProperty) {
				PrimaryKeyProperty pk = (PrimaryKeyProperty) property;
				builder.append(" PRIMARY KEY");
			}
			
			if(!property.isNullable()) {
				builder.append(" NOT NULL");
			}
			
		} else
			throw new AndOrmException(MessageFormat.format(bundle.getString("type_not_supported"), type.getCanonicalName()));
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		return builder.toString();
	}
	
	public void drop(Class<?> entityClass) {
		
	}
	
}