package fr.project.ideerepas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by elodie on 28/02/14.
 */
public class IngredientsDatabase {

	private DataBase database;
	private SQLiteDatabase db;

	public IngredientsDatabase(Context context) {
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

	
	public int getID(String name) {
		try {
			open();
			Cursor c = this.db.query(
					TABLEINGREDIENT.TAB_INGREDIENTS,           // Table name.
					TABLEINGREDIENT.ALL_COLUMNS,               // Columns.
					TABLEINGREDIENT.COL_NAME + " LIKE \"" + name + "\"",
					null, null, null, null, null);

			if( c.getCount() == 0) {
				close();
				return -1;
			}
			
			c.moveToFirst();
			int id = c.getInt(TABLEINGREDIENT.NUM_COL_ID);

			close();
			return id;

		}
		catch(Exception e) {
			Log.i("Ingredients.getNames", e.toString());
		}
		return -1;
	}
	
	public String getName(int id) {
		try {
			open();
			Cursor c = this.db.query(
					TABLEINGREDIENT.TAB_INGREDIENTS,           // Table name.
					TABLEINGREDIENT.ALL_COLUMNS,               // Columns.
					TABLEINGREDIENT.COL_ID + " = " + id,
					null, null, null, null, null);

			if( c.getCount() == 0) {
				close();
				return null;
			}
			
			c.moveToFirst();
			String name = c.getString(TABLEINGREDIENT.NUM_COL_NAME);

			close();
			return name;

		}
		catch(Exception e) {
			Log.i("Ingredients.getNames", e.toString());
		}
		return null;
	}
	
	/**
	 * Get all ingredients name.
	 * @return ingredients list name.
	 */
	public String[] getNames() {

		try {
			open();
			Cursor c = this.db.query(
					TABLEINGREDIENT.TAB_INGREDIENTS,           // Table name.
					TABLEINGREDIENT.ALL_COLUMNS,               // Columns.
					null,null, null, null, null, null);

			if( c.getCount() == 0) {
				close();
				return null;
			}
			String names[] = new String[c.getCount()];

			int i = 0;
			c.moveToFirst();
			while (c.isAfterLast() == false) {
				names[i] = c.getString(TABLEINGREDIENT.NUM_COL_NAME);
				c.moveToNext();
				i++;
			}
			close();
			return names;

		}
		catch(Exception e) {
			Log.i("Ingredients.getNames", e.toString());
		}
		return null;
	}

	private Boolean exist(String name) {
		open();
		Cursor c = this.db.query(
				TABLEINGREDIENT.TAB_INGREDIENTS,           // Table name.
				TABLEINGREDIENT.ALL_COLUMNS,               // Columns.
				TABLEINGREDIENT.COL_NAME + " LIKE \"" + name + "\"",
				null, null, null, null, null);

		if( c.getCount() == 0) {
			close();
			return false;
		}
		close();
		return true;
	}
	
	
	/**
	 * Add a new ingredient in the database.
	 */
	public long add(String name) {

		try {
			
			if( exist(name) ) return getID(name);
			open();
			ContentValues values = new ContentValues();

			values.put(TABLEINGREDIENT.COL_NAME, name);

			long id = this.db.insert(TABLEINGREDIENT.TAB_INGREDIENTS, null,values);
			close();
			return id;
		}
		catch(Exception e) {
			Log.i("Ingredients.add", e.toString());
		}
		return -1;
	}


	public void delete(String name) {
		try {
			open();
			this.db.delete(TABLEINGREDIENT.TAB_INGREDIENTS, 
					TABLEINGREDIENT.COL_NAME + " LIKE \"" + name + "\"" , null);
			close();
		}
		catch(Exception e) {
			Log.i("Ingredients.delete", e.toString());
		}
	}
}
