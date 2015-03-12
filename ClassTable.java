package com.example.plantrack;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Class that provides helpful database table accessor variables and manages
 * basic required database functionality.  
 */
public class ClassTable {
	
	/** Joke table in the database. */
	public static final String DATABASE_TABLE_CLASS = "class_table";
	
	/** Joke table column names and IDs for database access. */
	public static final String CLASS_KEY_ID = "_id";
	public static final int CLASS_COL_ID = 0;
	
	public static final String CLASS_KEY_NAME = "class_name";
	public static final int CLASS_COL_NAME = CLASS_COL_ID + 1;
	
	public static final String CLASS_KEY_QUARTER = "quarter";
	public static final int CLASS_COL_QUARTER = CLASS_COL_ID + 2;
	
	/** SQLite database creation statement. Auto-increments IDs of inserted jokes.
	 * Joke IDs are set after insertion into the database. */
	public static final String DATABASE_CREATE = "create table " + DATABASE_TABLE_CLASS + " (" + 
			CLASS_KEY_ID + " integer primary key autoincrement, " + 
			CLASS_KEY_NAME	+ " text not null, " + 
			CLASS_KEY_QUARTER + " text);";
	
	/** SQLite database table removal statement. Only used if upgrading database. */
	public static final String DATABASE_DROP = "drop table if exists " + DATABASE_TABLE_CLASS;
	
	/**
	 * Initializes the database.
	 * 
	 * @param database
	 * 				The database to initialize.	
	 */
	public static void onCreate(SQLiteDatabase database) {
		// TODO
		database.execSQL(DATABASE_CREATE);
	}
	
	/**
	 * Upgrades the database to a new version.
	 * 
	 * @param database
	 * 					The database to upgrade.
	 * @param oldVersion
	 * 					The old version of the database.
	 * @param newVersion
	 * 					The new version of the database.
	 */
	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		// TODO
		Log.w(ClassTable.class.getName(), "Warning: database being upgraded from" + oldVersion + " to " + newVersion);
		database.execSQL(DATABASE_DROP);
		onCreate(database);
	}
}
