package fr.project.ideerepas.meal;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import fr.project.ideerepas.database.DataBase;
import fr.project.ideerepas.database.TABLEMEAL;

public class Meals {

	private static String TAG = Meals.class.getName();
	private DataBase database;
	private SQLiteDatabase db;

	/**
	 * Meals Constructor.
	 * Create database if not exist.
	 * @param context
	 */
	public Meals(Context context) {
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
	 * Get all meals name.
	 * @return Meals list name.
	 */
	public String[] getNames() {

		try {
			open();
			Cursor c = this.db.query(
					TABLEMEAL.TAB_MEALS,                 // Table name.
					TABLEMEAL.ALL_COLUMNS,               // Columns.
					null,null, null, null, null, null);

			if( c.getCount() == 0) {
				return null;
			}
			String names[] = new String[c.getCount()];

			int i = 0;
			c.moveToFirst();
			while (c.isAfterLast() == false) {
				names[i] = c.getString(TABLEMEAL.NUM_COL_NAME);
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
	 * Get all couple name and picture.
	 * @return Meals list picture.
	 */
	public Map<String,String> getCoupleNamePicture() {

		Map<String,String> rows = new HashMap<String, String>();
		
		try {
			open();
			Cursor c = this.db.query(
					TABLEMEAL.TAB_MEALS,                 // Table name.
					TABLEMEAL.ALL_COLUMNS,               // Columns.
					null,null, null, null, null, null);

			if( c.getCount() == 0) {
				return null;
			}

			c.moveToFirst();
			while (c.isAfterLast() == false) {
				rows.put(c.getString(TABLEMEAL.NUM_COL_NAME), c.getString(TABLEMEAL.NUM_COL_PICTURE));
				c.moveToNext();
			}
			close();
			return rows;
			
		}
		catch(Exception e) {
			Log.i(TAG+"add", e.toString());
		}
		return null;
	}

	/**
	 * Add a new meal in the database.
	 */
	public void add(String name, String picture, int recipe) {

		try {
			open();
			ContentValues values = new ContentValues();

			values.put(TABLEMEAL.COL_NAME, name);
			values.put(TABLEMEAL.COL_PICTURE, picture);
			values.put(TABLEMEAL.COL_RECIPE_ID, recipe);

			this.db.insert(TABLEMEAL.TAB_MEALS, null,values);
			close();
		}
		catch(Exception e) {
			Log.i(TAG+"add", e.toString());
		}
	}
}
