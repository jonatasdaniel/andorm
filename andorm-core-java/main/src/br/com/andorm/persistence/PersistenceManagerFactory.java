package br.com.andorm.persistence;

import java.lang.reflect.Field;

import br.com.andorm.Entity;
import br.com.andorm.Table;
import br.com.andorm.cache.EntityCache;
import br.com.andorm.cache.PersistenceManagerCache;
import br.com.andorm.config.AndOrmConfig;
import br.com.andorm.config.EntityConfig;
import br.com.andorm.property.FieldProperty;
import br.com.andorm.property.FieldPropertyFactory;
import br.com.andorm.property.PrimaryKeyFieldProperty;
import br.com.andorm.property.reader.PropertyReader;
import br.com.andorm.property.writer.PropertyWriter;
import br.com.andorm.provider.Provider;
import br.com.andorm.reflection.Reflection;
import br.com.andorm.types.TextFormat;

/**
 * 
 * @author jonatas-daniel
 *
 */
public class PersistenceManagerFactory {

	private final AndOrmConfig config;
	private final PersistenceManagerCache cache;
	private PersistenceManager manager;
	
	private PersistenceManagerFactory(AndOrmConfig config) {
		this.config = config;
		
		cache = new PersistenceManagerCache();
	}
	
	public static PersistenceManager get(AndOrmConfig config) {
		PersistenceManagerFactory factory = new PersistenceManagerFactory(config);
		
		return factory.create();
	}
	
	public PersistenceManager create() {
		for (EntityConfig config : this.config.getEntities()) {
			EntityCache cache = createCache(config);
			this.cache.add(cache);
		}
		
		manager = new SQLitePersistenceManager(cache);
		return manager;
	}

	private EntityCache createCache(EntityConfig config) {
		Class<?> entityClass = config.getEntityClass();
		
		String tableName = entityClass.getSimpleName();
		if(entityClass.isAnnotationPresent(Table.class)) {
			tableName = entityClass.getAnnotation(Table.class).value();
		}
		
		Entity entity = entityClass.getAnnotation(Entity.class);
		PropertyWriter writer = Reflection.newInstance(entity.propertyWriter());
		PropertyReader reader = Reflection.newInstance(entity.propertyReader());
		TextFormat textFormat = entity.textFormat();
		
		Provider provider = Reflection.newInstance(entityClass.getAnnotation(Entity.class).provider());
		EntityCache cache = new EntityCache(entityClass, tableName, provider);
		
		FieldPropertyFactory fieldPropertyFactory = new FieldPropertyFactory();
		for(Field field : entityClass.getDeclaredFields()) {
			FieldProperty property = fieldPropertyFactory.create(writer, reader, textFormat, field);
			if(property instanceof PrimaryKeyFieldProperty) {
				cache.setPk((PrimaryKeyFieldProperty)property);
			} else {
				cache.addFieldProperty(property);
			}
		}
		
		return cache;
	}
	
	
	
}