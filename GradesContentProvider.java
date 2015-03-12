package com.example.plantrack;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Class that provides content from a SQLite database to the application.
 * Provides joke information to a ListView through a CursorAdapter. The database stores
 * jokes in a two-dimensional table, where each row is a joke and each column is a property
 * of a joke (ID, joke text, joke rating, joke author).
 * 
 * Note that CursorLoaders require a ContentProvider, which is why this application wraps a
 * SQLite database into a content provider instead of managing the database<-->application
 * transactions manually.
 */
public class GradesContentProvider extends ContentProvider {

	/** The joke database. */
	private GradesDatabaseHelper class_database;
	private GradesDatabaseHelper item_database;
	
	/** Values for the URIMatcher. */
	private static final int CLASS_ID = 1;
	private static final int ITEM_ID = 2;
	private static final int ITEM_CLASS_ID = 3;
	
	/** The authority for this content provider. */
	private static final String AUTHORITY = "com.example.plantrack.contentprovider";
	
	/** The database table to read from and write to, and also the root path for use in the URI matcher.
	 * This is essentially a label to a two-dimensional array in the database filled with rows of jokes
	 * whose columns contain joke data. */
	private static final String CLASS_BASE_PATH = "class_table";
	private static final String ITEM_BASE_PATH = "item_table";
	
	/** This provider's content location. Used by accessing applications to
	 * interact with this provider. */
	public static final Uri CLASS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CLASS_BASE_PATH);
	public static final Uri ITEM_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + ITEM_BASE_PATH);

	/** Matches content URIs requested by accessing applications with possible
	 * expected content URI formats to take specific actions in this provider. */
	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, CLASS_BASE_PATH + "/classes/#", CLASS_ID);
		sURIMatcher.addURI(AUTHORITY, ITEM_BASE_PATH + "/items_courseId/#", ITEM_CLASS_ID);
		sURIMatcher.addURI(AUTHORITY, ITEM_BASE_PATH + "/items/#", ITEM_ID);
		// TODO
	}
	
	
	@Override
	public boolean onCreate() {
		// TODO
		class_database = new GradesDatabaseHelper(this.getContext(), 
		 GradesDatabaseHelper.CLASS_DATABASE_NAME, null, GradesDatabaseHelper.CLASS_DATABASE_VERSION);
		item_database = new GradesDatabaseHelper(this.getContext(),
		 GradesDatabaseHelper.ITEM_DATABASE_NAME, null, GradesDatabaseHelper.ITEM_DATABASE_VERSION);
		return true;
	}

	/**
	 * Fetches rows from the joke table. Given a specified URI that contains a
	 * filter, returns a list of jokes from the joke table matching that filter in the
	 * form of a Cursor.<br><br>
	 * 
	 * Overrides the built-in version of <b>query(...)</b> provided by ContentProvider.<br><br>
	 * 
	 * For more information, read the documentation for the built-in version of this
	 * method by hovering over the method name in the method signature below this
	 * comment block in Eclipse and clicking <b>query(...)</b> in the Overrides details.
	 * */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		for (String s : projection) {
			System.out.println(s);
		}
		checkColumns(projection, uri);
		Cursor cursor;
		
		int uriType = sURIMatcher.match(uri);
		
		switch (uriType) {
		case CLASS_ID:
			queryBuilder.setTables(ClassTable.DATABASE_TABLE_CLASS);
			String class_filter = uri.getLastPathSegment();
			if (!(class_filter.equals(Grades.SHOW_ALL))) {
				queryBuilder.appendWhere(ClassTable.CLASS_KEY_ID + "=" +
				 class_filter);
			}
			else {
				selection = null;
			}
			SQLiteDatabase class_db = this.class_database.getWritableDatabase();
			cursor = queryBuilder.query(class_db, projection, selection, null, null, null, null);
			cursor.setNotificationUri(getContext().getContentResolver(), uri);	
			break;
		case ITEM_ID:
			queryBuilder.setTables(ItemTable.DATABASE_TABLE_ITEM);
			String item_filter = uri.getLastPathSegment();
			if (!(item_filter.equals(ListOfItems.SHOW_ALL))) {
				queryBuilder.appendWhere(ItemTable.ITEM_KEY_ID + "=" +
				 item_filter);
			}
			else {
				selection = null;
			}
			SQLiteDatabase item_db = this.item_database.getWritableDatabase();
			cursor = queryBuilder.query(item_db, projection, selection, null, null, null, null);
			cursor.setNotificationUri(getContext().getContentResolver(), uri);	
			break;
		case ITEM_CLASS_ID:
			queryBuilder.setTables(ItemTable.DATABASE_TABLE_ITEM);
			String class_id = uri.getLastPathSegment();
			queryBuilder.appendWhere(ItemTable.ITEM_KEY_CLASSID + "=" + class_id);
			SQLiteDatabase itemclassId_db = this.item_database.getWritableDatabase();
			cursor = queryBuilder.query(itemclassId_db, projection, selection, null, null, null, null);
			cursor.setNotificationUri(getContext().getContentResolver(), uri);	
			break;
			
			default: throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		return cursor;
	}
	
	/** We don't really care about this method for this application. */
	@Override
	public String getType(Uri uri) {
		return null;
	}
	
	/**
	 * Inserts a joke into the joke table. Given a specific URI that contains a
	 * joke and the values of that joke, writes a new row in the table filled
	 * with that joke's information and gives the joke a new ID, then returns a URI
	 * containing the ID of the inserted joke.<br><br>
	 * 
	 * Overrides the built-in version of <b>insert(...)</b> provided by ContentProvider.<br><br>
	 * 
	 * For more information, read the documentation for the built-in version of this
	 * method by hovering over the method name in the method signature below this
	 * comment block in Eclipse and clicking <b>insert(...)</b> in the Overrides details.
	 * */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		/** Open the database for writing. */
		// TODO
		SQLiteDatabase sqlDB;
		Uri new_uri;
		
		/** Will contain the ID of the inserted joke. */
		long id = 0;
		
		/** Match the passed-in URI to an expected URI format. */
		int uriType = sURIMatcher.match(uri);
		
		switch(uriType)	{
		
		/** Expects a joke ID, but we will do nothing with the passed-in ID since
		 * the database will automatically handle ID assignment and incrementation.
		 * IMPORTANT: joke ID cannot be set to -1 in passed-in URI; -1 is not interpreted
		 * as a numerical value by the URIMatcher. */
		case CLASS_ID:
			sqlDB = this.class_database.getWritableDatabase();
			/** Perform the database insert, placing the joke at the bottom of the table. */
			id = sqlDB.insert(ClassTable.DATABASE_TABLE_CLASS, null, values);
			new_uri = Uri.parse(CLASS_CONTENT_URI + "/classes/" + id);
			break;
		case ITEM_ID:
			sqlDB = this.item_database.getWritableDatabase();
			id = sqlDB.insert(ItemTable.DATABASE_TABLE_ITEM, null, values);
			new_uri = Uri.parse(ITEM_CONTENT_URI + "/items/" + id);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		/** Alert any watchers of an underlying data change for content/view refreshing. */
		getContext().getContentResolver().notifyChange(uri, null);
		
		return new_uri;
	}

	/**
	 * Removes a row from the joke table. Given a specific URI containing a joke ID,
	 * removes rows in the table that match the ID and returns the number of rows removed.
	 * Since IDs are automatically incremented on insertion, this will only ever remove
	 * a single row from the joke table.<br><br>
	 * 
	 * Overrides the built-in version of <b>delete(...)</b> provided by ContentProvider.<br><br>
	 * 
	 * For more information, read the documentation for the built-in version of this
	 * method by hovering over the method name in the method signature below this
	 * comment block in Eclipse and clicking <b>delete(...)</b> in the Overrides details.
	 * */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO
		SQLiteDatabase sqlDB;
		int deletedRows = 0;
		
		int uriType = sURIMatcher.match(uri);
		switch (uriType) {
		case CLASS_ID:
			String class_id = uri.getLastPathSegment();			
			sqlDB = this.class_database.getWritableDatabase();
			deletedRows = sqlDB.delete(ClassTable.DATABASE_TABLE_CLASS, 
			 ClassTable.CLASS_KEY_ID + "=" + class_id, null);
			break;
		case ITEM_ID:
			String item_id = uri.getLastPathSegment();
			sqlDB = this.item_database.getWritableDatabase();
			deletedRows = sqlDB.delete(ItemTable.DATABASE_TABLE_ITEM, 
			 ItemTable.ITEM_KEY_ID + "=" + item_id, null);
			break;
			default: throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		if (deletedRows > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		
		return deletedRows;
	}

	/**
	 * Updates a row in the joke table. Given a specific URI containing a joke ID and the
	 * new joke values, updates the values in the row with the matching ID in the table. 
	 * Since IDs are automatically incremented on insertion, this will only ever update
	 * a single row in the joke table.<br><br>
	 * 
	 * Overrides the built-in version of <b>update(...)</b> provided by ContentProvider.<br><br>
	 * 
	 * For more information, read the documentation for the built-in version of this
	 * method by hovering over the method name in the method signature below this
	 * comment block in Eclipse and clicking <b>update(...)</b> in the Overrides details.
	 * */
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO
		SQLiteDatabase sqlDB;
		int updatedRows = 0;
		
		int uriType = sURIMatcher.match(uri);
		switch(uriType) {
		case CLASS_ID:
			String class_id = uri.getLastPathSegment();
			sqlDB = this.class_database.getWritableDatabase();
			updatedRows = sqlDB.update(ClassTable.DATABASE_TABLE_CLASS, values, 
			 ClassTable.CLASS_KEY_ID + "=" + class_id, null);
			break;
		case ITEM_ID:
			String item_id = uri.getLastPathSegment();
			sqlDB = this.item_database.getWritableDatabase();
			updatedRows = sqlDB.update(ItemTable.DATABASE_TABLE_ITEM, values,
			 ItemTable.ITEM_KEY_ID + "=" + item_id, null);
			break;
			default: throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		if (updatedRows > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		
		return updatedRows;
	}

	/**
	 * Verifies the correct set of columns to return data from when performing a query.
	 * 
	 * @param projection
	 * 						The set of columns about to be queried.
	 */
	private void checkColumns(String[] projection, Uri uri) {
		String[] class_available = {ClassTable.CLASS_KEY_ID, ClassTable.CLASS_KEY_NAME, ClassTable.CLASS_KEY_QUARTER};
		String[] item_available = {ItemTable.ITEM_KEY_ID, ItemTable.ITEM_KEY_NAME, 
		 ItemTable.ITEM_KEY_GRADE, ItemTable.ITEM_KEY_CATEGORY, ItemTable.ITEM_KEY_CLASSID};
		
		if(projection != null) {
			if (sURIMatcher.match(uri) == CLASS_ID) {

				HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
				HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(class_available));
				
				if(!availableColumns.containsAll(requestedColumns))	{
					throw new IllegalArgumentException("Unknown columns in projection");
				}
			}
			else if (sURIMatcher.match(uri) == ITEM_ID) {

				HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
				HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(item_available));
				
				if(!availableColumns.containsAll(requestedColumns))	{
					throw new IllegalArgumentException("Unknown columns in projection");
				}
			}
		}
	}
}
