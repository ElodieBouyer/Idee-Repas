package fr.project.ideerepas.database;


public class TABLEMENUS {
	public static final String TAB_MENU = "MENU";

	public static final String COL_ID             = "MENU_ID"; 
	public static final String COL_DATE           = "MENU_DATE";
	public static final String COL_MENU_MEAL_ID_1 = "MENU_MEAL_ID_1";
	public static final String COL_MENU_MEAL_ID_2 = "MENU_MEAL_ID_2";

	public static final int NUM_COL_ID        = 0; 
	public static final int NUM_COL_DATE      = 1;
	public static final int NUM_COL_MEAL_1    = 2;
	public static final int NUM_COL_MEAL_2    = 3;


	public static final String[] ALL_COLUMNS = {
		COL_ID, COL_DATE, COL_MENU_MEAL_ID_1, COL_MENU_MEAL_ID_2
	};

	public static final String CREATE_TABLE_MENU = 
			"CREATE TABLE " + TAB_MENU + " (" 
					+ COL_ID             + " INTEGER PRIMARY KEY, " 
					+ COL_DATE           + " TEXT NOT NULL, "
					+ COL_MENU_MEAL_ID_1 + " INTEGER NOT NULL, "
					+ COL_MENU_MEAL_ID_2 + " INTEGER NOT NULL, "
					+ "FOREIGN KEY("   + COL_MENU_MEAL_ID_1 + ")" + " REFERENCES " + TABLEMEAL.TAB_MEALS + "(" + TABLEMEAL.COL_ID + ") ON DELETE CASCADE," 
					+ "FOREIGN KEY("   + COL_MENU_MEAL_ID_2 + ")" + " REFERENCES " + TABLEMEAL.TAB_MEALS + "(" + TABLEMEAL.COL_ID + ") ON DELETE CASCADE);";
}
