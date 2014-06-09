package fr.project.ideerepas.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

	private static final String DATABASE_NAME    = "ideerepas.db";
	private static final int    DATABASE_VERSION = 1;

	public DataBase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLEMEAL.CREATE_TABLE_MEALS);
		db.execSQL(TABLEMENUS.CREATE_TABLE_MENU);
		db.execSQL(TABLEINGREDIENT.CREATE_TABLE_INGREDIENTS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLEMEAL.TAB_MEALS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLEMENUS.TAB_MENU);
		db.execSQL("DROP TABLE IF EXISTS " + TABLEINGREDIENT.CREATE_TABLE_INGREDIENTS);
	    onCreate(db);
	}

	public void delete(SQLiteDatabase db) {
		db.execSQL("DROP TABLE " 
				+ TABLEMEAL.TAB_MEALS + " " 
				+ TABLEMENUS.TAB_MENU + " "
				+ TABLEINGREDIENT.CREATE_TABLE_INGREDIENTS + ";");
		onCreate(db);
	}
}
