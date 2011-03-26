package br.com.andorm.persistence;

import static br.com.andorm.utils.reflection.ReflectionUtils.in;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import resources.ResourceBundleFactory;
import br.com.andorm.AndOrmConfiguration;
import br.com.andorm.AndOrmException;
import br.com.andorm.AutoInc;
import br.com.andorm.Column;
import br.com.andorm.Entity;
import br.com.andorm.MappedSuperClass;
import br.com.andorm.PrimaryKey;
import br.com.andorm.Table;
import br.com.andorm.Transient;
import br.com.andorm.persistence.property.PrimaryKeyProperty;
import br.com.andorm.persistence.property.Property;
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
			if(field.isAnnotationPresent(Column.class))
				columnName = field.getAnnotation(Column.class).name();
			else
				columnName = toUnderscoreLowerCase(field.getName());
			
			Method setMethod = in(clazz).returnSetMethodOf(field);
			Method getMethod = in(clazz).returnGetMethodOf(field);
			if(field.isAnnotationPresent(PrimaryKey.class)) {
				boolean isAutoInc = field.isAnnotationPresent(AutoInc.class);
				PrimaryKeyProperty pk = new PrimaryKeyProperty(columnName, field, getMethod, setMethod, isAutoInc);
				cache.setPk(pk);
			} else {
				Property property = new Property(columnName, field, getMethod, setMethod);
				
				cache.add(property);
			}
		}
	}

}
