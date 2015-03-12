package com.example.plantrack;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.plantrack.ItemView.OnItemChangeListener;

/**
 * MAY NEED MULTIPLE!
 * Class that functions similarly to JokeListAdapter, but instead uses a Cursor.
 * A Cursor is a list of rows from a database that acts as a medium between the
 * database and a ViewGroup (in this case, a SQLite database table containing rows
 * of jokes and a ListView containing JokeViews).
 */
public class ItemCursorAdapter extends CursorAdapter{

	private OnItemChangeListener m_listener;
	
	/**
	 * Parameterized constructor that takes in the application Context in which
	 * it is being used and the Collection of Joke objects to which it is bound.
	 * 
	 * @param context
	 *            The application Context in which this JokeListAdapter is being
	 *            used.
	 * 
	 * @param jokeCursor
	 *            A Database Cursor containing a result set of Jokes which
	 *            should be bound to JokeViews.
	 *            
	 * @param flags
	 * 			  A list of flags that decide this adapter's behavior.
	 */
	public ItemCursorAdapter(Context context, Cursor jokeCursor, int flags) {
		super(context, jokeCursor, flags);
	}

	public void setOnItemChangeListener(OnItemChangeListener mListener) {
		this.m_listener = mListener;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		String item_name = cursor.getString(ItemTable.ITEM_COL_NAME);
		String item_category = cursor.getString(ItemTable.ITEM_COL_CATEGORY);
		double grade = cursor.getInt(ItemTable.ITEM_COL_GRADE);
		int id = cursor.getInt(ItemTable.ITEM_COL_ID);
		
		((ItemView) view).setOnItemChangeListener(null);
		((ItemView) view).setItem(new Item(item_name, item_category, grade, id));
		((ItemView) view).setOnItemChangeListener(this.m_listener);
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		String item_name = cursor.getString(ItemTable.ITEM_COL_NAME);
		double grade = cursor.getInt(ItemTable.ITEM_COL_GRADE);
		String item_category = cursor.getString(ItemTable.ITEM_COL_CATEGORY);
		int id = cursor.getInt(ItemTable.ITEM_COL_ID);
		
		ItemView item_view = new ItemView(context,
		 new Item(item_name, item_category, grade, id));
		
		item_view.setOnItemChangeListener(this.m_listener);
		
		return item_view;
	}
}