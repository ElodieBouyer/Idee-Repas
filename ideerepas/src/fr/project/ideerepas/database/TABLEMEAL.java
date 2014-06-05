package fr.project.ideerepas.database;

public class TABLEMEAL {

	public static final String TAB_MEALS = "MEALS";

	public static final String COL_ID        = "MEAL_ID"; 
	public static final String COL_NAME      = "MEAL_NAME";
	public static final String COL_PICTURE   = "MEAL_PICTURE";
	public static final String COL_RECIPE_ID = "MEAL_RECIPE_ID";
	
	public static final int NUM_COL_ID        = 0; 
	public static final int NUM_COL_NAME      = 1;
	public static final int NUM_COL_PICTURE   = 2;
	public static final int NUM_COL_RECIPE_ID = 3;
	
	public static final String[] ALL_COLUMNS = {
		COL_ID, COL_NAME, COL_PICTURE, COL_RECIPE_ID
	};

	public static final String CREATE_TABLE_MEALS = 
			"CREATE TABLE " + TAB_MEALS + " (" 
					+ COL_ID        + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
					+ COL_NAME      + " TEXT NOT NULL UNIQUE, " 
					+ COL_PICTURE   + " TEXT," 
					+ COL_RECIPE_ID + " INTEGER);";
}
