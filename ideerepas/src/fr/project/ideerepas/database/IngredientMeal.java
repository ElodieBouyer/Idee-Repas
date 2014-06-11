package fr.project.ideerepas.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class IngredientMeal {
	private DataBase database;
	private SQLiteDatabase db;
	private Ingredients igdDatabase;

	public IngredientMeal(Context context) {
		this.database    = new DataBase(context);
		this.igdDatabase = new Ingredients(context);
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


	public List<String> getIngredient(String mealName) {
		List<String> igdList = new ArrayList<String>();
		try {
			// Opening to the database.
			open();

			// Find the meal ID.
			int idMeal = igdDatabase.getID(mealName);
			if( idMeal == -1 ) {
				close();
				return null;
			}

			String [] cols = {TABLEINGREDIENTMEAL.COL_ID_MEAL,TABLEINGREDIENTMEAL.COL_ID_INGREDIENT,TABLEINGREDIENT.COL_NAME};
			// Find ingredients.
			Cursor c = this.db.query(
					TABLEINGREDIENTMEAL.TAB_INGREDIENTMEAL + " " + TABLEINGREDIENT.TAB_INGREDIENTS,
					cols,
					TABLEINGREDIENTMEAL.COL_ID_MEAL + " = " + idMeal,
					null, null, null, null, null);

			if( c.getCount() == 0) {
				return null;
			}

			c.moveToFirst();
			while (c.isAfterLast() == false) {
				igdList.add(c.getString(2));
				c.moveToNext();
			}

			// Closing to the database.
			close();
			return igdList;
		}
		catch(Exception e) {
			Log.i("IngredientMeal.getIngredient", e.toString());
		}
		return null;
	}

	/**
	 * Add a new ingredient in the database.
	 */
	public void add(int idMeal, int idIngredient) {

		try {
			open();
			ContentValues values = new ContentValues();

			values.put(TABLEINGREDIENTMEAL.COL_ID_INGREDIENT, idIngredient);
			values.put(TABLEINGREDIENTMEAL.COL_ID_MEAL, idMeal);

			this.db.insert(TABLEINGREDIENTMEAL.TAB_INGREDIENTMEAL, null,values);
			close();
		}
		catch(Exception e) {
			Log.i("IngredientMeal.add", e.toString());
		}
	}


	public void delete(int idMeal, int idIngredient) {
		try {
			open();
			this.db.delete(TABLEINGREDIENTMEAL.TAB_INGREDIENTMEAL, 
					TABLEINGREDIENTMEAL.COL_ID_INGREDIENT + " = " + idIngredient + 
					TABLEINGREDIENTMEAL.COL_ID_MEAL       + " = " + idMeal , null);
			close();
		}
		catch(Exception e) {
			Log.i("Ingredients.delete", e.toString());
		}
	}
}
