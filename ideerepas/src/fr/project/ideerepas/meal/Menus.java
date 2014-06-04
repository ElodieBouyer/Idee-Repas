package fr.project.ideerepas.meal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import fr.project.ideerepas.database.DataBase;
import fr.project.ideerepas.database.TABLEMENUS;

public class Menus implements Functionality {

	private static String TAG = Menus.class.getName();
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

	/**
	 * Get all menu name.
	 * @return Menu list name.
	 */
	@Override
	public String[] getNames() {
		try {
			open();
			Cursor c = this.db.query(
					TABLEMENUS.TAB_MENU,                 // Table name.
					TABLEMENUS.ALL_COLUMNS,              // Columns.
					null,null, null, null, null, null);

			if( c.getCount() == 0) {
				return null;
			}
			String names[] = new String[c.getCount()];

			int i = 0;
			c.moveToFirst();
			while (c.isAfterLast() == false) {
				names[i] = c.getString(TABLEMENUS.NUM_COL_NAME);
				c.moveToNext();
				i++;
			}
			close();
			return names;

		}
		catch(Exception e) {
			Log.i(TAG+"add", e.toString());
		}
		return null;
	}

	/**
	 * Add a new meal in the database.
	 */
	@Override
	public void add(String name, String picture, int recipe) {
		try {
			open();
			ContentValues values = new ContentValues();
			values.put(TABLEMENUS.COL_NAME, name);

			this.db.insert(TABLEMENUS.TAB_MENU, null,values);
			close();
		}
		catch(Exception e) {
			Log.i(TAG+"add", e.toString());
		}
	}
}
