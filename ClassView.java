package com.example.plantrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ClassView extends LinearLayout {
	private Class cls;
	private TextView classButton;
	private OnClassChangeListener m_onClassChangeListener;
	
	public ClassView(Context m_context, Class cl) {
		super(m_context);
		LayoutInflater inflater = (LayoutInflater)m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.class_view, this, true);
		this.classButton = (TextView) findViewById(R.id.class_title_button);
		setClass(cl);
	}
	
	public String getClassTitle(){
		return this.cls.getTitle();
	}
	
	/**
	 * Mutator method for changing the Joke object this View displays. This View
	 * will be updated to display the correct contents of the new Joke.
	 * 
	 * @param joke
	 *            The Joke object which this View will display.
	 */
	public void setClass(Class cl) {
		this.cls = cl;
		this.classButton.setText(this.cls.getTitle());
		requestLayout();
	}
	
	public Class getCourse() {
		return this.cls;
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
	public void setOnClassChangeListener(OnClassChangeListener listener) {
		//TODO
		this.m_onClassChangeListener = listener;
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
	protected void notifyOnClassChangeListener() {
		// TODO
		if (this.m_onClassChangeListener != null) {
			this.m_onClassChangeListener.onClassChanged(this, this.cls);
		}
	}

	/**
	 * Interface definition for a callback to be invoked when the underlying
	 * Joke is changed in this JokeView object.
	 */
	public static interface OnClassChangeListener {

		/**
		 * Called when the underlying Joke in a JokeView object changes state.
		 * 
		 * @param view
		 *            The JokeView in which the Joke was changed.
		 * @param joke
		 *            The Joke that was changed.
		 */
		public void onClassChanged(ClassView view, Class course);
	}

}
