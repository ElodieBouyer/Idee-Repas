package fr.project.ideerepas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by elodie on 28/02/14.
 */
public class Ingredients {

	private DataBase database;
	private SQLiteDatabase db;

	public Ingredients(Context context) {
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


	/**
	 * Add a new ingredient in the database.
	 */
	public void add(String name) {

		try {
			open();
			ContentValues values = new ContentValues();

			values.put(TABLEINGREDIENT.COL_NAME, name);

			this.db.insert(TABLEINGREDIENT.TAB_INGREDIENTS, null,values);
			close();
		}
		catch(Exception e) {
			Log.i("Ingredients.add", e.toString());
		}
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
