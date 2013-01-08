package br.com.andorm.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.andorm.persistence.tablemanager.TableManager;

/**
 * 
 * @author Tiago.Emerick
 * @since 07/01/2013
 * @version 0.1
 * 
 *          Class that implements SQLiteOpenHelper. It's the standard way to
 *          create database and tables.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

	private PersistenceManagerCache cache;

	public SQLiteHelper(Context context, String databasePath, PersistenceManagerCache cache) {
		super(context, databasePath, null, 1);
		this.cache = cache;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		TableManager tm = new TableManager(cache, db);
		tm.createAll();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}

}
