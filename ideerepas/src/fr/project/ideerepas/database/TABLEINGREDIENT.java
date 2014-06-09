package fr.project.ideerepas.database;

public class TABLEINGREDIENT {
	public static final String TAB_INGREDIENTS = "INGREDIENTS";

	public static final String COL_ID        = "INGREDIENT_ID"; 
	public static final String COL_NAME      = "INGREDIENT_NAME";

	public static final int NUM_COL_ID        = 0; 
	public static final int NUM_COL_NAME      = 1;

	public static final String[] ALL_COLUMNS = { COL_ID, COL_NAME };

	public static final String CREATE_TABLE_INGREDIENTS = 
			"CREATE TABLE " + TAB_INGREDIENTS + " (" 
					+ COL_ID        + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
					+ COL_NAME      + " TEXT NOT NULL UNIQUE);";
}
