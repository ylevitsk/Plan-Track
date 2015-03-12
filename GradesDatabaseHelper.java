package com.example.plantrack;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class that hooks up to the JokeContentProvider for initialization and
 * maintenance. Uses JokeTable for assistance.
 */
public class GradesDatabaseHelper extends SQLiteOpenHelper{

	/** The name of the database. */
	public static final String CLASS_DATABASE_NAME = "classdatabase.db";
	public static final String ITEM_DATABASE_NAME = "itemdatabase.db";
	
	/** The starting database version. */
	public static final int CLASS_DATABASE_VERSION = 1;
	public static final int ITEM_DATABASE_VERSION = 1;
	
	/**
	 * Create a helper object to create, open, and/or manage a database.
	 * 
	 * @param context
	 * 					The application context.
	 * @param name
	 * 					The name of the database.
	 * @param factory
	 * 					Factory used to create a cursor. Set to null for default behavior.
	 * @param version
	 * 					The starting database version.
	 */
	public GradesDatabaseHelper(Context context, String name,
		CursorFactory factory, int version) {
		// TODO
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		ClassTable.onCreate(db);
		ItemTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		ClassTable.onUpgrade(db, oldVersion, newVersion);
		ItemTable.onUpgrade(db, oldVersion, newVersion);
	}
}
