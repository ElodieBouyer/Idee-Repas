package fr.project.ideerepas.controller;

import android.content.Context;
import fr.project.ideerepas.database.IngredientMeal;
import fr.project.ideerepas.database.Ingredients;
import fr.project.ideerepas.database.Meals;

public class DatabaseController {

	private static Meals          mealsDatabase   = null;
	private static Ingredients    igdDatabase     = null;
	private static IngredientMeal igdMealDatabase = null;

	private DatabaseController() {}

	public static Meals getInstanceMeals(Context context) {
		if( mealsDatabase == null ) mealsDatabase = new Meals(context);
		return mealsDatabase;
	}
	
	public static Ingredients getInstanceIngredient(Context context) {
		if( igdDatabase == null ) igdDatabase = new Ingredients(context);
		return igdDatabase;
	}
	
	public static IngredientMeal getInstanceIngredientMeal(Context context) {
		if( igdMealDatabase == null ) igdMealDatabase = new IngredientMeal(context);
		return igdMealDatabase;
	}
	

}
