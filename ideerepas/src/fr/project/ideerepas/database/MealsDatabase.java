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

public class MealsDatabase {

	private static String TAG = MealsDatabase.class.getName();
	private DataBase database;
	private SQLiteDatabase db;
	private int [] frequency = null;

	/**
	 * Meals Constructor.
	 * Create database if not exist.
	 * @param context
	 */
	public MealsDatabase(Context context) {
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
	 * Check up if a meal exist or not.
	 * @param name Name of the meal to check.
	 * @return true or false.
	 */
	public boolean exist(String name) {
		try {
			open();
			Cursor c = this.db.query(
					TABLEMEAL.TAB_MEALS,                  
					TABLEMEAL.ALL_COLUMNS,              
					TABLEMEAL.COL_NAME + " LIKE \"" + name + "\"",
					null, null, null, null, null);

			if( c.getCount() == 0) {
				close();
				return false;
			}
		}
		catch(Exception e) {
			Log.i(TAG+"add", e.toString());
		}
		close();
		return true;
	}

	/**
	 * Get the picture url of a meal.
	 * @param name Name of the meal.
	 * @return Url picture.
	 */
	public Uri getPicture(String name) {
		try {
			if( !exist(name)) return null;

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

	/**
	 * Get the meal id.
	 * @param name Name of the meal.
	 * @return Meal id.
	 */
	public int getId(String name) {
		try {
			if( !exist(name)) return -1;
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
	 * Get the meal frequency.
	 * @param name Name of the meal.
	 * @return Meal frequency.
	 */
	public int getFrequency(String name) {
		try {
			if( !exist(name)) return -1;
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
			int frequency = c.getInt(TABLEMEAL.NUM_COL_FREQUENCY);
			close();

			return frequency;

		}
		catch(Exception e) {
			Log.i(TAG+"add", e.toString());
		}
		return -1;
	}

	/**
	 * Get all meal's id.
	 * @return Id list.
	 */
	public int [] getIds() {
		try {
			open();
			Cursor c = this.db.query(
					TABLEMEAL.TAB_MEALS,                  // Table name.
					TABLEMEAL.ALL_COLUMNS,                // Columns.
					null, null, null, null, null, null);

			if( c.getCount() == 0) {
				return null;
			}
			int[] ids = new int [c.getCount()];
			int i = 0;

			c.moveToFirst();
			while (c.isAfterLast() == false) {
				ids[i] = c.getInt(TABLEMEAL.NUM_COL_ID);
				c.moveToNext();
				i++;
			}

			close();
			return ids;

		}
		catch(Exception e) {
			Log.i(TAG+"add", e.toString());
		}
		return null;
	}

	/**
	 * Get all meals's name.
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
	 * Get a meal name with a id.
	 * @return Meal name.
	 */
	public String getName(int id) {

		try {
			open();
			Cursor c = this.db.query(
					TABLEMEAL.TAB_MEALS,                
					TABLEMEAL.ALL_COLUMNS,               
					TABLEMEAL.COL_ID + " = " + id ,null, null, null, null, null);

			if( c.getCount() == 0) {
				return null;
			}

			c.moveToFirst();
			String n = c.getString(TABLEMEAL.NUM_COL_NAME);
			close();
			return n;

		}
		catch(Exception e) {
			Log.i(TAG+"add", e.toString());
		}
		return null;
	}

	/**
	 * Get all couple name/picture.
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

			frequency = new int[c.getCount()];

			c.moveToFirst();
			for(int i = 0; c.isAfterLast() == false ; i++) {
				rows.put(c.getString(TABLEMEAL.NUM_COL_NAME), c.getString(TABLEMEAL.NUM_COL_PICTURE));
				frequency[i] = c.getInt(TABLEMEAL.NUM_COL_FREQUENCY);
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

	public int[] getFrequency() {
		return frequency;
	}

	/**
	 * Add a new meal in the database.
	 * @param name New name of the meal.
	 * @param picture New picture of the meal.
	 * @param recipe New recipe id of the meal.
	 * @param frequency Frequency of the meal.
	 * @return The meal id.
	 */
	public int add(String name, String picture, int recipe, int frequency) {

		try {
			if(exist(name)) return -1;
			open();
			ContentValues values = new ContentValues();

			values.put(TABLEMEAL.COL_NAME, name);
			values.put(TABLEMEAL.COL_PICTURE, picture);
			values.put(TABLEMEAL.COL_RECIPE_ID, recipe);
			values.put(TABLEMEAL.COL_FREQUENCY, frequency);

			int id = (int) this.db.insert(TABLEMEAL.TAB_MEALS, null,values);
			close();
			return id;
		}
		catch(Exception e) {
			Log.i(TAG+"add", e.toString());
		}
		return -1;
	}

	/**
	 * Update a meal.
	 * @param id Id of the meal to change.
	 * @param name New name of the meal.
	 * @param picture New picture of the meal.
	 * @param frequency Frequency of the meal.
	 * @param recipe New recipe id of the meal.
	 */
	public void update(int id, String name, String picture, int recipe, int frequency) {
		try {
			open();
			ContentValues values = new ContentValues();

			values.put(TABLEMEAL.COL_NAME, name);
			values.put(TABLEMEAL.COL_PICTURE, picture);
			values.put(TABLEMEAL.COL_RECIPE_ID, recipe);
			values.put(TABLEMEAL.COL_FREQUENCY, frequency);

			int nbModif = this.db.update(TABLEMEAL.TAB_MEALS, values, 
					TABLEMEAL.COL_ID + " = " + id , null);
			Log.i(TAG+"update", Integer.toString(nbModif));
			close();
		}
		catch(Exception e) {
			Log.i(TAG+"update", e.toString());
		}
	}

	/**
	 * Delete a meal with its name.
	 * @param name Name of the meal.
	 */
	public void delete(String name) {
		try {
			if( getPicture(name) != null ) {
				File file = new File(getPicture(name).getPath());
				if( file.exists() ) {
					file.delete();
				}
			}
			if( !exist(name)) return;
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
