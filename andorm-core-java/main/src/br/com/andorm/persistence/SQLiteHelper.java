package br.com.andorm.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.andorm.persistence.tablemanager.PropertyCreationQueryBuilder;
import br.com.andorm.persistence.tablemanager.TableCreationQueryBuilder;

/**
 * 
 * @author Tiago.Emerick
 * @since 07/01/2013
 * @version 0.1
 *
 * Class that implements SQLiteOpenHelper.
 * It's the standard way to create database and tables. 
 */
public class SQLiteHelper extends SQLiteOpenHelper {

	private TableCreationQueryBuilder builder;
	private PersistenceManagerCache cache;
	
	public SQLiteHelper(Context context, String databasePath, PersistenceManagerCache cache) {
		super(context, databasePath, null, 1);
		this.cache = cache;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		builder = new TableCreationQueryBuilder(cache, new PropertyCreationQueryBuilder());
		for (Class<?> entityClass : cache.getAllEntities()) {
			String table_create_sql_command = builder.build(entityClass);
			db.execSQL(table_create_sql_command);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}

}
