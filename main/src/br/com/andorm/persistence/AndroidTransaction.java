package br.com.andorm.persistence;

import android.database.sqlite.SQLiteDatabase;


public class AndroidTransaction implements Transaction {

	private final SQLiteDatabase database;
	
	protected AndroidTransaction(SQLiteDatabase db) {
		database = db;
	}
	
	@Override
	public void begin() {
		database.beginTransaction();
	}

	@Override
	public void commit() {
		database.setTransactionSuccessful();
	}

	@Override
	public void end() {
		database.endTransaction();
	}

}
