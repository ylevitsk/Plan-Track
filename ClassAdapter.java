package com.example.plantrack;

import java.util.List;

import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * This class binds the visual JokeViews and the data behind them (Jokes).
 */
public class ClassAdapter extends BaseAdapter{

	/** The application Context in which this JokeListAdapter is being used. */
	private Context m_context;

	/** The data set to which this JokeListAdapter is bound. */
	private List<Class> classList;

	/**
	 * Parameterized constructor that takes in the application Context in which
	 * it is being used and the Collection of Joke objects to which it is bound.
	 * m_nSelectedPosition will be initialized to Adapter.NO_SELECTION.
	 * 
	 * @param context
	 *            The application Context in which this JokeListAdapter is being
	 *            used.
	 * 
	 * @param jokeList
	 *            The Collection of Joke objects to which this JokeListAdapter
	 *            is bound.
	 */
	public ClassAdapter(Context context, List<Class> classList) {
		this.m_context = context;
		this.classList = classList;
	}

	@Override
	public int getCount() {
		return this.classList.size();
	}

	@Override
	public Object getItem(int position) {
		return this.classList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ClassView classview = (ClassView)convertView;
		if(convertView == null){
			classview = new ClassView(m_context, (Class)getItem(position)); 
		}
		else{
			classview.setClass((Class)getItem(position));
		}
		return classview;
	}
}
