package fr.project.ideerepas.database;

public class TABLEINGREDIENTMEAL {
	public static final String TAB_INGREDIENTMEAL = "INGREDIENTMEAL";

	public static final String COL_ID_MEAL        = "IM_MEAL_ID"; 
	public static final String COL_ID_INGREDIENT  = "IM_INGREDIENT_ID";

	public static final int NUM_COL_ID_MEAL        = 0; 
	public static final int NUM_COL_ID_INGREDIENT  = 1;

	public static final String[] ALL_COLUMNS = { COL_ID_MEAL, COL_ID_INGREDIENT };

	public static final String CREATE_TABLE_INGREDIENTMEAL = 
			"CREATE TABLE " + TAB_INGREDIENTMEAL + " (" 
					+ COL_ID_MEAL      + " INTEGER NOT NULL, " 
					+ COL_ID_MEAL      + " INTEGER NOT NULL, "
					+ "FOREIGN KEY("   + COL_ID_MEAL       + ")" + "REFERENCES" + TABLEMEAL.TAB_MEALS             + "(" + TABLEMEAL.COL_ID        + ")," 
					+ "FOREIGN KEY("   + COL_ID_INGREDIENT + ")" + "REFERENCES" + TABLEINGREDIENT.TAB_INGREDIENTS + "(" + TABLEINGREDIENT.COL_ID  + "));";
}
