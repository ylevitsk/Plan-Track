package com.example.plantrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemView extends LinearLayout {
	private Item item;
	private TextView assign;
	private TextView grade;
	private OnItemChangeListener m_onItemChangeListener;	
	
	public ItemView(Context context, Item item) {
		super(context);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.item_grade_view, this, true);
		assign = (TextView)findViewById(R.id.assign_name);
		grade = (TextView)findViewById(R.id.grade_earned);
		setItem(item);
	}
	
	
	public String getClassTitle(){
		return this.item.getTitle();
	}
	
	/**
	 * Mutator method for changing the Item object this View displays. This View
	 * will be updated to display the correct contents of the new Item.
	 * 
	 * @param item
	 *            The Item object which this View will display.
	 */
	public void setItem(Item item) {
		this.item = item;
		assign.setText(this.item.getTitle());
		grade.setText("" + this.item.getPercent());
		requestLayout();
	}
	
	/**
	 * Mutator method for changing the OnJokeChangeListener object this JokeView
	 * notifies when the state its underlying Joke object changes.
	 * 
	 * It is possible and acceptable for m_onJokeChangeListener to be null, you
	 * should allow for this.
	 * 
	 * @param listener
	 *            The OnJokeChangeListener object that should be notified when
	 *            the underlying Joke changes state.
	 */
	public void setOnItemChangeListener(OnItemChangeListener listener) {
		//TODO
		this.m_onItemChangeListener = listener;
	}

	/**
	 * This method should always be called after the state of m_joke is changed.
	 * 
	 * It is possible and acceptable for m_onJokeChangeListener to be null, you
	 * should test for this.
	 * 
	 * This method should not be called if setJoke(...) is called, since the
	 * internal state of the Joke object that m_joke references is not be
	 * changed. Rather, m_joke reference is being changed to reference a
	 * different Joke object.
	 */
	protected void notifyOnItemChangeListener() {
		// TODO
		if (this.m_onItemChangeListener != null) {
			this.m_onItemChangeListener.onItemChanged(this, this.item);
		}
	}

	/**
	 * Interface definition for a callback to be invoked when the underlying
	 * Joke is changed in this JokeView object.
	 */
	public static interface OnItemChangeListener {

		/**
		 * Called when the underlying Joke in a JokeView object changes state.
		 * 
		 * @param view
		 *            The JokeView in which the Joke was changed.
		 * @param joke
		 *            The Joke that was changed.
		 */
		public void onItemChanged(ItemView view, Item item);
	}


}
