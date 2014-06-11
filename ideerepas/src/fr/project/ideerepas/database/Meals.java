package fr.project.ideerepas.database;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

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

	public Uri getPicture(String name) {
		try {
			open();
			Cursor c = this.db.query(
					TABLEMEAL.TAB_MEALS,                  // Table name.
					TABLEMEAL.ALL_COLUMNS,                // Columns.
					TABLEMEAL.COL_NAME + " LIKE \"" + name + "\"", // Selection.
					null, null, null, null, null);

			if( c.getCount() == 0) {
				return null;
			}

			c.moveToFirst();
			String path = c.getString(TABLEMEAL.NUM_COL_PICTURE);
			close();
			if( path == null ) return null;
			return Uri.parse(path);

		}
		catch(Exception e) {
			Log.i(TAG+"add", e.toString());
		}
		return null;
	}
	
	public int getId(String name) {
		try {
			open();
			Cursor c = this.db.query(
					TABLEMEAL.TAB_MEALS,                  // Table name.
					TABLEMEAL.ALL_COLUMNS,                // Columns.
					TABLEMEAL.COL_NAME + " LIKE \"" + name + "\"", // Selection.
					null, null, null, null, null);

			if( c.getCount() == 0) {
				return -1;
			}

			c.moveToFirst();
			int id = c.getInt(TABLEMEAL.NUM_COL_ID);
			
			close();
			return id;

		}
		catch(Exception e) {
			Log.i(TAG+"add", e.toString());
		}
		return -1;
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

	public void update(int id, String name, String picture, int recipe) {
		try {
			open();
			ContentValues values = new ContentValues();

			values.put(TABLEMEAL.COL_NAME, name);
			values.put(TABLEMEAL.COL_PICTURE, picture);
			values.put(TABLEMEAL.COL_RECIPE_ID, recipe);

			int nbModif = this.db.update(TABLEMEAL.TAB_MEALS, values, 
					TABLEMEAL.COL_ID + " = " + id , null);
			Log.i(TAG+"update", Integer.toString(nbModif));
			close();
		}
		catch(Exception e) {
			Log.i(TAG+"update", e.toString());
		}
	}
	
	public void delete(String name) {
		try {
			if( getPicture(name) != null ) {
				File file = new File(getPicture(name).getPath());
				if( file.exists() ) {
					file.delete();
				}
			}
			
			open();
			int nbModif = this.db.delete(TABLEMEAL.TAB_MEALS, 
					TABLEMEAL.COL_NAME + " LIKE \"" + name + "\"" , null);
			Log.i(TAG+"delete", Integer.toString(nbModif));
			close();
		}
		catch(Exception e) {
			Log.i(TAG+"delete", e.toString());
		}
	}
}
