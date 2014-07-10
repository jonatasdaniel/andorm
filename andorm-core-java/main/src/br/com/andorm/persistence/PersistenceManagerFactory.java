package br.com.andorm.persistence;

import static br.com.andorm.reflection.Reflactor.in;
import static br.com.andorm.utils.NameResolver.toUnderscoreLowerCase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.ResourceBundle;

import android.content.Context;
import br.com.andorm.AfterDelete;
import br.com.andorm.AfterSave;
import br.com.andorm.AfterUpdate;
import br.com.andorm.AndOrmException;
import br.com.andorm.AutoInc;
import br.com.andorm.BeforeDelete;
import br.com.andorm.BeforeSave;
import br.com.andorm.BeforeUpdate;
import br.com.andorm.Column;
import br.com.andorm.DateTime;
import br.com.andorm.Entity;
import br.com.andorm.Enumerated;
import br.com.andorm.MappedSuperClass;
import br.com.andorm.NameType;
import br.com.andorm.PrimaryKey;
import br.com.andorm.Table;
import br.com.andorm.Transient;
import br.com.andorm.config.AndOrmConfiguration;
import br.com.andorm.config.EntityConfiguration;
import br.com.andorm.config.NameTypes;
import br.com.andorm.property.BigDecimalProperty;
import br.com.andorm.property.BooleanProperty;
import br.com.andorm.property.DateTimeProperty;
import br.com.andorm.property.EnumeratedProperty;
import br.com.andorm.property.PrimaryKeyProperty;
import br.com.andorm.property.Property;
import br.com.andorm.provider.DefaultProvider;
import br.com.andorm.provider.Provider;
import br.com.andorm.reflection.Reflactor;
import br.com.andorm.resources.ResourceBundleFactory;
import br.com.andorm.types.EnumType;
import br.com.andorm.types.TemporalType;

/**
 * 
 * @author jonatasdaniel
 * @since 18/02/2011
 * @version 0.1
 *
 * @author tiago.emerick
 * @modified 07/01/2013
 * @version 0.2
 * @changes
 * 		create method createPMDatabaseAndTablesIfNotExists.
 * 		Pass context to AndroidPersistenceManager to create database and tables if doesn't exists yet
 * 
 */
public final class PersistenceManagerFactory {
	
	private final static ResourceBundle bundle = ResourceBundleFactory.get();
	
	private static AndroidPersistenceManager manager;
	
	private PersistenceManagerFactory() {}
	
	public static PersistenceManager createPMDatabaseAndTablesIfNotExists(AndOrmConfiguration configuration, Context context) {
		manager = new AndroidPersistenceManager(configuration.getDatabasePath(), context);
		
		return create(configuration);
	}
	
	public static PersistenceManager create(AndOrmConfiguration configuration) {
		if (manager == null) {
			manager = new AndroidPersistenceManager(configuration.getDatabasePath());
		}
		
		PersistenceManagerCache persistenceManagerCache = new PersistenceManagerCache();
		
		for(EntityConfiguration conf : configuration.getEntityConfigurations()) {
			Class<?> clazz = conf.getEntityClass();
			
			Class<? extends Provider> providerClass = DefaultProvider.class;
			if(clazz.isAnnotationPresent(br.com.andorm.Provider.class)) {
				providerClass = clazz.getAnnotation(br.com.andorm.Provider.class).value(); 
			}
			
			if(!clazz.isAnnotationPresent(Entity.class))
				throw new AndOrmException(MessageFormat.format(bundle.getString("is_not_a_entity"), clazz.getName()));
			
			NameTypes nameTypes = NameTypes.Underscored;
			if(clazz.isAnnotationPresent(NameType.class)) {
				nameTypes = clazz.getAnnotation(NameType.class).value();
			}
			
			String tableName = null;
			if(clazz.isAnnotationPresent(Table.class)) {
				tableName = clazz.getAnnotation(Table.class).value();
			} else {
				if(nameTypes == NameTypes.Underscored) {
					tableName = toUnderscoreLowerCase(clazz.getSimpleName());
				} else {
					tableName = clazz.getSimpleName();
				}
			}
			
			Provider provider = Reflactor.newInstance(providerClass);
			EntityCache cache = new EntityCache(clazz, tableName, provider);
			
			reflectClass(clazz, cache, conf, nameTypes);
			
			if(cache.getPk() == null)
				throw new AndOrmException(MessageFormat.format(bundle.getString("has_not_a_pk"), clazz.getName()));
			
			persistenceManagerCache.add(cache);
		}
		
		manager.setCache(persistenceManagerCache);
		
		return manager;
	}
	
	private static void reflectClass(Class<?> clazz, EntityCache cache, EntityConfiguration configuration, NameTypes nameType) {
		if(clazz.getSuperclass().isAnnotationPresent(MappedSuperClass.class)) {
			reflectClass(clazz.getSuperclass(), cache, configuration, nameType);
		}
		
		for(Field field : clazz.getDeclaredFields()) {
			if(Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()) || field.isAnnotationPresent(Transient.class))
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
			
			if(column == null || columnName == null) {
				if(nameType == NameTypes.Underscored) {
					columnName = toUnderscoreLowerCase(field.getName());
				} else {
					columnName = field.getName();
				}
			}
			
			Method setMethod = in(clazz).returnSetMethodOf(field);
			Method getMethod = in(clazz).returnGetMethodOf(field);
			
			if(field.isAnnotationPresent(PrimaryKey.class)) {
				boolean isAutoInc = field.isAnnotationPresent(AutoInc.class);
				PrimaryKeyProperty pk = new PrimaryKeyProperty(columnName, field, getMethod, setMethod, isAutoInc);
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
			} else if(field.getType() == Enum.class || field.isAnnotationPresent(Enumerated.class)) {
				EnumType type = EnumType.Ordinal;
				if(field.isAnnotationPresent(Enumerated.class)) {
					Enumerated enumerated = field.getAnnotation(Enumerated.class);
					type = enumerated.type();
				}
				
				EnumeratedProperty property = new EnumeratedProperty(columnName, field, getMethod, setMethod, type);
				cache.add(property);
				
			} else if(field.getType() == Boolean.class) {
				BooleanProperty property = new BooleanProperty(columnName, field, getMethod, setMethod, nullable);
				
				cache.add(property);
			} else if(field.getType() == BigDecimal.class) {
				BigDecimalProperty property = new BigDecimalProperty(columnName, field, getMethod, setMethod, nullable);
				
				cache.add(property);
			} else {
				Property property = new Property(columnName, field, getMethod, setMethod, nullable);
				
				cache.add(property);
			}
		}
		
		if(configuration.getVerifyOperationMethods()) {
			for (Method method : clazz.getDeclaredMethods()) {
				if(method.isAnnotationPresent(BeforeSave.class)) {
					cache.setBeforeSaveMethod(method);
				} else if(method.isAnnotationPresent(AfterSave.class)) {
					cache.setAfterSaveMethod(method);
				} else if(method.isAnnotationPresent(BeforeUpdate.class)) {
					cache.setBeforeUpdateMethod(method);
				} else if(method.isAnnotationPresent(AfterUpdate.class)) {
					cache.setAfterUpdateMethod(method);
				} else if(method.isAnnotationPresent(BeforeDelete.class)) {
					cache.setBeforeDeleteMethod(method);
				} else if(method.isAnnotationPresent(AfterDelete.class)) {
					cache.setAfterDeleteMethod(method);
				}
			}
		}
	}

}