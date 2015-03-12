package com.example.plantrack;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.example.plantrack.ClassView.OnClassChangeListener;

/**
 * MAY NEED MULTIPLE!
 * Class that functions similarly to JokeListAdapter, but instead uses a Cursor.
 * A Cursor is a list of rows from a database that acts as a medium between the
 * database and a ViewGroup (in this case, a SQLite database table containing rows
 * of jokes and a ListView containing JokeViews).
 */
public class ClassCursorAdapter extends CursorAdapter{

	private OnClassChangeListener m_listener;
	
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
	public ClassCursorAdapter(Context context, Cursor jokeCursor, int flags) {
		super(context, jokeCursor, flags);
	}

	public void setOnClassChangedListener(OnClassChangeListener mListener) {
		this.m_listener = mListener;
	 }

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		String class_name = cursor.getString(ClassTable.CLASS_COL_NAME);
		int id = cursor.getInt(ClassTable.CLASS_COL_ID);
		
		((ClassView) view).setOnClassChangeListener(null);
		((ClassView) view).setClass(new Class(class_name, id));
		((ClassView) view).setOnClassChangeListener(this.m_listener);
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		String class_name = cursor.getString(ClassTable.CLASS_COL_NAME);
		int id = cursor.getInt(ClassTable.CLASS_COL_ID);
		
		ClassView class_view = new ClassView(context,
		 new Class(class_name, id));
		
		class_view.setOnClassChangeListener(this.m_listener);
		
		return class_view;
	}
}