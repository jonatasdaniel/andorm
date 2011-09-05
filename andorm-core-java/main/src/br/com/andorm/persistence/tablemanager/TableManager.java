package br.com.andorm.persistence.tablemanager;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import br.com.andorm.AndOrmException;
import br.com.andorm.persistence.PersistenceManagerCache;
import br.com.andorm.resources.ResourceBundleFactory;

/**
 * 
 * @author jonatasdaniel
 * @since 08/03/2011
 * @version 0.1
 *
 */
public final class TableManager {

	private final ResourceBundle bundle = ResourceBundleFactory.get();
	
	private final PersistenceManagerCache cache;
	private final SQLiteDatabase database;
	
	public TableManager(PersistenceManagerCache cache, SQLiteDatabase database) {
		this.cache = cache;
		this.database = database;
	}
	
	public void create(Class<?> entityClass) {
		PropertyCreationQueryBuilder propertyQueryBuilder = new PropertyCreationQueryBuilder();
		TableCreationQueryBuilder tableQueryBuilder = new TableCreationQueryBuilder(this.cache, propertyQueryBuilder);
		 
		String createQuery = tableQueryBuilder.build(entityClass);
		
		try {
			database.execSQL(createQuery);
		} catch(SQLiteException e) {
			throw new AndOrmException(MessageFormat.format(bundle.getString("create_table_error"), entityClass.getCanonicalName(), e.getMessage()));
		}
	}
	
	public void createAll() {
		for (Class<?> entityClass : cache.getAllEntities()) {
			create(entityClass);
		}
	}
	
	public void drop(Class<?> entityClass) {
		
	}
	
}