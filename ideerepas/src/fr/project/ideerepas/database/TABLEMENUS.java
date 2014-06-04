package fr.project.ideerepas.database;

public class TABLEMENUS {
	public static final String TAB_MENU = "MENU";

	public static final String COL_ID        = "MEAL_ID"; 
	public static final String COL_NAME      = "MEAL_NAME";

	public static final int NUM_COL_ID        = 0; 
	public static final int NUM_COL_NAME      = 1;


	public static final String[] ALL_COLUMNS = {
		COL_ID, COL_NAME
	};

	public static final String CREATE_TABLE_MENU = 
			"CREATE TABLE " + TAB_MENU + " (" 
					+ COL_ID        + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
					+ COL_NAME      + " TEXT NOT NULL);";
}
