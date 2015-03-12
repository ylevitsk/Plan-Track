package com.example.plantrack;

import java.util.List;

import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * This class binds the visual JokeViews and the data behind them (Jokes).
 */
public class GradeClassAdapter extends BaseAdapter{

	/** The application Context in which this JokeListAdapter is being used. */
	private Context m_context;

	/** The data set to which this JokeListAdapter is bound. */
	private List<Item> gpaList;

	/**
	 * Parameterized constructor that takes in the application Context in which
	 * it is being used and the Collection of Joke objects to which it is bound.
	 * m_nSelectedPosition will be initialized to Adapter.NO_SELECTION.
	 * 
	 * @param context
	 *            The application Context in which this JokeListAdapter is being
	 *            used.Intent i = new Intent(this, AddClassView.class);
		startActivityForResult(i, 1);
	 * 
	 * @param jokeList
	 *            The Collection of Joke objects to which this JokeListAdapter
	 *            is bound.
	 */
	public GradeClassAdapter(Context context, List<Item> gpaList) {
		this.m_context = context;
		this.gpaList = gpaList;
	}

	@Override
	public int getCount() {
		return this.gpaList.size();
	}

	@Override
	public Object getItem(int position) {
		return this.gpaList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemView classview = (ItemView)convertView;
		if(convertView == null){
			classview = new ItemView(m_context, (Item)getItem(position)); 
		}
		else{
			classview.setItem((Item)getItem(position));
		}
		return classview;
	}
}
