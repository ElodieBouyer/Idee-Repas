package fr.project.ideerepas.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Menus {

	private DataBase database;
	private SQLiteDatabase db;

	public Menus(Context context) {
		this.database = new DataBase(context);
	}

	/**
	 * Opens the database in writable, 
	 * and put its content into db.
	 */
	public void open() {
		this.db = this.database.getWritableDatabase();
	}

	/**
	 * Close access to the database db.
	 */
	public void close() {
		this.db.close();
	}

}
