package br.com.andorm.persistence;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import resources.ResourceBundleFactory;
import br.com.andorm.AndOrmConfiguration;
import br.com.andorm.AndOrmException;
import br.com.andorm.Column;
import br.com.andorm.Entity;
import static br.com.andorm.utils.reflection.ReflectionUtils.in;


public final class PersistenceManagerFactory {
	
	private final static ResourceBundle bundle = ResourceBundleFactory.get();
	
	private PersistenceManagerFactory() {}
	
	public static PersistenceManager create(AndOrmConfiguration configuration) {
		AndroidPersistenceManager manager = new AndroidPersistenceManager();
		PersistenceManagerCache persistenceManagerCache = new PersistenceManagerCache();
		
		for(Class<?> clazz : configuration.getEntities()) {
			if(!clazz.isAnnotationPresent(Entity.class))
				throw new AndOrmException(MessageFormat.format(bundle.getString("is_not_a_entity"), clazz.getName()));
			
			final String tableName = clazz.getAnnotation(Entity.class).tableName();
			EntityCache cache = new EntityCache(clazz, tableName);
			
			for(Field field : clazz.getDeclaredFields()) {
				String columnName = field.getName().toLowerCase();
				if(field.isAnnotationPresent(Column.class))
					columnName = field.getAnnotation(Column.class).name();
				
				Method setMethod = in(clazz).returnSetMethodOf(field);
				Method getMethod = in(clazz).returnGetMethodOf(field);
				Property property = new Property(columnName, field, getMethod, setMethod);
				
				cache.add(property);
			}
			
			if(cache.getPk() == null)
				throw new AndOrmException(MessageFormat.format(bundle.getString("has_not_a_pk"), clazz.getName()));
			
			persistenceManagerCache.add(cache);
		}
		
		
		manager.setCache(persistenceManagerCache);
		
		return manager;
	}

}
