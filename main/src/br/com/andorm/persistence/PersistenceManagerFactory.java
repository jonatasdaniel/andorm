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
import br.com.andorm.Column;
import br.com.andorm.Entity;
import br.com.andorm.PrimaryKey;
import br.com.andorm.Table;
import br.com.andorm.Transient;
import static br.com.andorm.utils.NameResolver.*;


public final class PersistenceManagerFactory {
	
	private final static ResourceBundle bundle = ResourceBundleFactory.get();
	
	private PersistenceManagerFactory() {}
	
	public static PersistenceManager create(AndOrmConfiguration configuration) {
		AndroidPersistenceManager manager = new AndroidPersistenceManager();
		PersistenceManagerCache persistenceManagerCache = new PersistenceManagerCache();
		
		for(Class<?> clazz : configuration.getEntities()) {
			if(!clazz.isAnnotationPresent(Entity.class))
				throw new AndOrmException(MessageFormat.format(bundle.getString("is_not_a_entity"), clazz.getName()));
			
			String tableName = toUnderscoreLowerCase(clazz.getSimpleName().toLowerCase());
			if(clazz.isAnnotationPresent(Table.class))
				tableName = clazz.getAnnotation(Table.class).value();
			EntityCache cache = new EntityCache(clazz, tableName);
			
			for(Field field : clazz.getDeclaredFields()) {
				if(Modifier.isStatic(field.getModifiers()) || field.isAnnotationPresent(Transient.class))
					continue;
				String columnName = toUnderscoreLowerCase(field.getName().toLowerCase());
				if(field.isAnnotationPresent(Column.class))
					columnName = field.getAnnotation(Column.class).name();
				
				Method setMethod = in(clazz).returnSetMethodOf(field);
				Method getMethod = in(clazz).returnGetMethodOf(field);
				if(field.isAnnotationPresent(PrimaryKey.class)) {
					boolean isAutoInc = field.getAnnotation(PrimaryKey.class).autoInc();
					PrimaryKeyProperty pk = new PrimaryKeyProperty(columnName, field, getMethod, setMethod, isAutoInc);
					cache.setPk(pk);
				} else {
					Property property = new Property(columnName, field, getMethod, setMethod);
					
					cache.add(property);
				}
			}
			
			if(cache.getPk() == null)
				throw new AndOrmException(MessageFormat.format(bundle.getString("has_not_a_pk"), clazz.getName()));
			
			persistenceManagerCache.add(cache);
		}
		
		
		manager.setCache(persistenceManagerCache);
		
		return manager;
	}

}
