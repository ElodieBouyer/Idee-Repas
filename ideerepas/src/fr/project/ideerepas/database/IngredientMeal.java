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
	private Meals mealDatabase;

	public IngredientMeal(Context context) {
		this.database     = new DataBase(context);
		this.igdDatabase  = new Ingredients(context);
		this.mealDatabase = new Meals(context); 
	}

	/**
	 * Opens the database in writable, 
	 * and put its content into db.
	 */
	private void open() {
		this.db = this.database.getWritableDatabase();
	}

	/**
	 * Close access to the database db.
	 */
	private void close() {
		this.db.close();
	}


	public List<String> getIngredient(String mealName) {
		List<String> igdList = new ArrayList<String>();
		Log.i("getIngredient", "A la recherche de "+mealName);
		try {
			// Opening to the database.
			open();

			// Find the meal ID.
			int idMeal = mealDatabase.getId(mealName);
			if( idMeal == -1 ) {
				close();
				return null;
			}
			Log.i("getIngredient", "ID+ "+idMeal);

			// Find ingredients.
			Cursor c = this.db.query(
					TABLEINGREDIENTMEAL.TAB_INGREDIENTMEAL + " " + TABLEINGREDIENT.TAB_INGREDIENTS,
					TABLEINGREDIENTMEAL.ALL_COLUMNS,
					TABLEINGREDIENTMEAL.COL_ID_MEAL + " = " + idMeal,
					null, null, null, null, null);

			if( c.getCount() == 0) {
				close();
				return null;
			}

			c.moveToFirst();
			while (c.isAfterLast() == false) {
				igdList.add(igdDatabase.getName(c.getInt(TABLEINGREDIENTMEAL.NUM_COL_ID_INGREDIENT)));
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
	
	public Boolean contain(int mealName, String igdMeal ) {
		return contain(mealName, igdDatabase.getID(igdMeal));
	}
	
	public Boolean contain(String mealName, String igdMeal ) {
		return contain(mealDatabase.getId(mealName), igdDatabase.getID(igdMeal));
	}

	private Boolean contain(int idMeal, int idIngredient) {
		Log.i("IngredientMeal.contain", "contain");
		try {
			open();
			Cursor c = this.db.query(
					TABLEINGREDIENTMEAL.TAB_INGREDIENTMEAL,
					TABLEINGREDIENTMEAL.ALL_COLUMNS,
					TABLEINGREDIENTMEAL.COL_ID_MEAL       + " = " + idMeal + " AND " + 
					TABLEINGREDIENTMEAL.COL_ID_INGREDIENT + " = " + idIngredient ,
					null, null, null, null, null);

			if( c.getCount() == 0) {
				close();
				return false;
			}
			close();
		}
		catch(Exception e) {
			Log.i("IngredientMeal.add", e.toString());
		}
		return true;
	}

	/**
	 * Add a new ingredient in the database.
	 */
	public void add(int idMeal, int idIngredient) {

		Log.i("IngredientMeal.add", "add");
		try {
			if( contain(idMeal, idIngredient) ) return;
			
			open();
			ContentValues values = new ContentValues();

			values.put(TABLEINGREDIENTMEAL.COL_ID_INGREDIENT, idIngredient);
			values.put(TABLEINGREDIENTMEAL.COL_ID_MEAL, idMeal);

			this.db.insert(TABLEINGREDIENTMEAL.TAB_INGREDIENTMEAL, null,values);
			Log.i("IngredientMeal.add", "Insertion");
			close();
		}
		catch(Exception e) {
			Log.i("IngredientMeal.add", e.toString());
		}
	}


	public void delete(int idMeal, int idIngredient) {
		try {
			
			if( !contain(idMeal, idIngredient)) return;
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
