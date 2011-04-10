package br.com.andorm.persistence;

import static br.com.andorm.reflection.Reflactor.in;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.Date;
import java.util.ResourceBundle;

import resources.ResourceBundleFactory;
import br.com.andorm.AndOrmConfiguration;
import br.com.andorm.AndOrmException;
import br.com.andorm.AutoInc;
import br.com.andorm.Column;
import br.com.andorm.DateTime;
import br.com.andorm.Entity;
import br.com.andorm.MappedSuperClass;
import br.com.andorm.PrimaryKey;
import br.com.andorm.Table;
import br.com.andorm.Transient;
import br.com.andorm.persistence.property.DateTimeProperty;
import br.com.andorm.persistence.property.PrimaryKeyProperty;
import br.com.andorm.persistence.property.Property;
import br.com.andorm.types.TemporalType;
import static br.com.andorm.utils.NameResolver.*;

/**
 * 
 * @author jonatasdaniel
 * @since 18/02/2011
 * @version 0.1
 *
 */
public final class PersistenceManagerFactory {
	
	private final static ResourceBundle bundle = ResourceBundleFactory.get();
	
	private PersistenceManagerFactory() {}
	
	public static PersistenceManager create(AndOrmConfiguration configuration) {
		AndroidPersistenceManager manager = new AndroidPersistenceManager(configuration.getDatabasePath());
		PersistenceManagerCache persistenceManagerCache = new PersistenceManagerCache();
		
		for(Class<?> clazz : configuration.getEntities()) {
			if(!clazz.isAnnotationPresent(Entity.class))
				throw new AndOrmException(MessageFormat.format(bundle.getString("is_not_a_entity"), clazz.getName()));
			
			String tableName = null;
			if(clazz.isAnnotationPresent(Table.class))
				tableName = clazz.getAnnotation(Table.class).value();
			else
				tableName = toUnderscoreLowerCase(clazz.getSimpleName());
			
			EntityCache cache = new EntityCache(clazz, tableName);
			
			reflectClass(clazz, cache);
			
			if(cache.getPk() == null)
				throw new AndOrmException(MessageFormat.format(bundle.getString("has_not_a_pk"), clazz.getName()));
			
			persistenceManagerCache.add(cache);
		}
		
		manager.setCache(persistenceManagerCache);
		
		return manager;
	}
	
	private static void reflectClass(Class<?> clazz, EntityCache cache) {
		if(clazz.getSuperclass().isAnnotationPresent(MappedSuperClass.class)) {
			reflectClass(clazz.getSuperclass(), cache);
		}
		
		for(Field field : clazz.getDeclaredFields()) {
			if(Modifier.isStatic(field.getModifiers()) || field.isAnnotationPresent(Transient.class))
				continue;
			String columnName = null;
			boolean nullable = true;
			
			Column column = null;
			if(field.isAnnotationPresent(Column.class))
				column = field.getAnnotation(Column.class);
			
			if(column != null) {
				if(column.name().length() > 0) {
					columnName = field.getAnnotation(Column.class).name();
				}
				
				nullable = field.getAnnotation(Column.class).nullable();
			} 
			
			if(column == null || columnName == null)
				columnName = toUnderscoreLowerCase(field.getName());
			
			Method setMethod = in(clazz).returnSetMethodOf(field);
			Method getMethod = in(clazz).returnGetMethodOf(field);
			
			if(field.isAnnotationPresent(PrimaryKey.class)) {
				boolean isAutoInc = field.isAnnotationPresent(AutoInc.class);
				PrimaryKeyProperty pk = new PrimaryKeyProperty(columnName, field, getMethod, setMethod, isAutoInc, nullable);
				cache.setPk(pk);
			} else if(field.getType() == Date.class || field.isAnnotationPresent(DateTime.class)) {
				TemporalType type = TemporalType.Date;
				
				if(field.isAnnotationPresent(DateTime.class)) {
					if(field.getType() != Date.class)
						throw new AndOrmException(MessageFormat.format(bundle.getString("wrong_date_time_type"), field.getType().getCanonicalName()));
					
					type = field.getAnnotation(DateTime.class).type();
				}
				
				DateTimeProperty dateTimeProperty = new DateTimeProperty(columnName, field, getMethod, setMethod, type);
				cache.add(dateTimeProperty);
			} else {
				Property property = new Property(columnName, field, getMethod, setMethod, nullable);
				
				cache.add(property);
			}
		}
	}

}
