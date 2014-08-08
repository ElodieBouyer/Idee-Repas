package fr.project.ideerepas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MenusDatabase {

	private DataBase database;
	private SQLiteDatabase db;
	private static int NB_DAYS = 7;
	private String []firstMealList = null;
	private String []seconMealList = null;
	private MealsDatabase mealsDatabase;

	public MenusDatabase(Context context) {
		this.database = new DataBase(context);
		this.mealsDatabase = new MealsDatabase(context);
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
	 * Delete the current menu to the database.
	 */
	public void deleteCourrentMenu() {
		try {
			open();
			int nbDeleting = this.db.delete(TABLEMENUS.TAB_MENU, " 1 " , null);
			Log.i("BOUH", "delete"+ Integer.toString(nbDeleting));
			close();
		}
		catch(Exception e) {
			Log.i("BOUH", "Error whith menu deleting" + e.toString());
		}
	}

	/**
	 * To add two meal of the database (for one day).
	 * @param idMeal1 First meal id.
	 * @param idMeal2 Second meal id.
	 * @param date Day of the week (0 to Monday, 6 to Sunday).
	 */
	public void addMenu(int idMeal1, int idMeal2, int date) {
		try {
			Log.i("BOUH", "Add one menu.");
			open();
			ContentValues values = new ContentValues();

			values.put(TABLEMENUS.COL_DATE, date);
			values.put(TABLEMENUS.COL_MENU_MEAL_ID_1, idMeal1);
			values.put(TABLEMENUS.COL_MENU_MEAL_ID_2, idMeal2);

			this.db.insert(TABLEMENUS.TAB_MENU, null,values);
			close();
		}
		catch(Exception e) {
			Log.i("BOUH", "Error with add one menu." + e.toString());
		}
	}

	/**
	 * Get the first meal list.
	 * @return Meal list.
	 */
	public String [] getFirstMealList() {
		try {
			if( firstMealList == null) firstMealList = new String[NB_DAYS];
			else return firstMealList;

			open();
			Cursor c = this.db.query(
					TABLEMENUS.TAB_MENU,                 
					TABLEMENUS.ALL_COLUMNS,               
					null,null, null, null, null, null);

			if( c.getCount() == 0) {
				return null;
			}

			int i = 0;
			c.moveToFirst();
			while (c.isAfterLast() == false) {
				firstMealList[i] = mealsDatabase.getName(c.getInt(TABLEMENUS.NUM_COL_MEAL_1));
				c.moveToNext();
				i++;
			}
			close();
			return firstMealList;
		}
		catch(Exception e) {
			Log.i("BOUH", "Error with getFirstMealList." + e.toString());
		}
		return firstMealList;
	}

	/**
	 * Get the second meal list.
	 * @return Meal list.
	 */
	public String [] getSecondMealList() {
		try {

			if( seconMealList == null) seconMealList = new String[NB_DAYS];
			else return seconMealList;

			open();
			Cursor c = this.db.query(
					TABLEMENUS.TAB_MENU,                 
					TABLEMENUS.ALL_COLUMNS,               
					null,null, null, null, null, null);

			if( c.getCount() == 0) {
				return null;
			}

			int i = 0;
			c.moveToFirst();
			while (c.isAfterLast() == false) {
				seconMealList[i] = mealsDatabase.getName(c.getInt(TABLEMENUS.NUM_COL_MEAL_2));
				c.moveToNext();
				i++;
			}
			close();
			return seconMealList;

		}
		catch(Exception e) {
			Log.i("BOUH", "Error with getSecondMealList." + e.toString());
		}
		return seconMealList;
	}

}
