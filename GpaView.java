package com.example.plantrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GpaView extends LinearLayout {
	private GpaItem gpa;
	private TextView name;
	private TextView grade;
	private TextView units;
	
	
	public GpaView(Context context, GpaItem item) {
		super(context);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.gpa_view, this, true);
		name = (TextView)findViewById(R.id.gpa_name);
		grade = (TextView)findViewById(R.id.gpa_grade);
		units = (TextView)findViewById(R.id.gpa_units);
		setItem(item);
	}
	
	
	/**
	 * Mutator method for changing the Item object this View displays. This View
	 * will be updated to display the correct contents of the new Item.
	 * 
	 * @param item
	 *            The Item object which this View will display.
	 */
	public void setItem(GpaItem item) {
		this.gpa = item;
		name.setText(this.gpa.getName());
		grade.setText(Double.toString(this.gpa.getGrade()));
		units.setText(Integer.toString(this.gpa.getUnits()));
		requestLayout();
	}
}
