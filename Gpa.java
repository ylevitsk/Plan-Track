package com.example.plantrack;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;

public class Gpa extends SherlockFragment{
	ListView layout;
	Button addButton;
	EditText name;
	EditText grade;
	EditText units;
	TextView totalGpa;
	GpaItem gpa;
	protected ArrayList<GpaItem> arrGpaList;
	GpaAdapter gpaAdapter;
	private ListView itemSelected;
	private int pos;
	private GpaItem ite;

	@Override	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.gpa_layout, container, false);
	}

	public void onActivityCreated(Bundle savedInstanceState){		
		super.onActivityCreated(savedInstanceState);
		layout = (ListView) getView().findViewById(R.id.GPAViewGroup);
		this.arrGpaList = new ArrayList<GpaItem>();
		addButton = (Button) getView().findViewById(R.id.button1);
		name = (EditText) getView().findViewById(R.id.name);
		grade = (EditText) getView().findViewById(R.id.grade);
		units = (EditText) getView().findViewById(R.id.units);
		totalGpa = (TextView) getView().findViewById(R.id.total_gpa);
		this.gpaAdapter = new GpaAdapter(getActivity(), this.arrGpaList);
		layout.setAdapter(gpaAdapter);
		registerForContextMenu(this.layout);
		initListener();
	}
	
	private void initListener() {
		this.addButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				if(name.getText().toString().length()>0 &&
						grade.getText().toString().length()>0 && units.getText().toString().length()>0){
					if(addButton.getText().equals("Save")){
						ite.setGrade(Double.parseDouble(grade.getText().toString()));
						ite.setName(name.getText().toString());
						ite.setUnits(Integer.parseInt(units.getText().toString()));
					}
					else{
						gpa = new GpaItem(name.getText().toString(), Double.parseDouble(grade.getText().toString()),
						Integer.parseInt(units.getText().toString()));
						arrGpaList.add(gpa);
						
					}
					gpaAdapter.notifyDataSetChanged();
					name.setText("");
					grade.setText("");
					units.setText("");
					addButton.setText(R.string.class_add);
					calculateGpa();
				}
			}	
		});
	}
	
	@Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        itemSelected = (ListView)v;
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
        pos = info.position;
        ite = (GpaItem) this.gpaAdapter.getItem(pos);
        menu.add(Menu.NONE, R.id.edit, Menu.NONE, "Edit");
        menu.add(Menu.NONE, R.id.remove, Menu.NONE, "Remove");
    }
	
	@Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
        switch(item.getItemId()) {
        case R.id.edit:
        	addButton.setText("Save");
        	name.setText(ite.getName());
        	units.setText(Integer.toString(ite.getUnits()));
        	grade.setText(Double.toString(ite.getGrade()));
        	calculateGpa();
            return true;
        case R.id.remove:
        	this.arrGpaList.remove(pos);
        	this.gpaAdapter.notifyDataSetChanged();
        	calculateGpa();
        	return true;
        }
        
        return super.onContextItemSelected( item);
    }
	
	public void calculateGpa(){
		int totalUnits = 0, i;
		double gradePoint = 0;
		for(i = 0; i< gpaAdapter.getCount(); i++){
			GpaItem itemGrade = (GpaItem) this.gpaAdapter.getItem(i);
			totalUnits = totalUnits + itemGrade.getUnits();
			double g = itemGrade.getGrade();
			if(g >= 93)
				gradePoint = gradePoint + itemGrade.getUnits() * 4;
			else if( g>=90 && g <93)
				gradePoint = gradePoint + itemGrade.getUnits() * 3.7;
			else if(g>=87 && g < 90)
				gradePoint = gradePoint + itemGrade.getUnits() * 3.3;
			else if(g>=83 && g< 87)
				gradePoint = gradePoint + itemGrade.getUnits() * 3;
			else if(g >= 80 && g < 83)
				gradePoint = gradePoint + itemGrade.getUnits() * 2.7;
			else if (g>=77 && g < 80)
				gradePoint = gradePoint + itemGrade.getUnits() * 2.3;
			else if(g >= 73 && g < 77)
				gradePoint = gradePoint + itemGrade.getUnits() * 2;
			else if (g >=70 && g < 73)
				gradePoint = gradePoint + itemGrade.getUnits() * 1.7;
			else if (g>= 67 && g <70)
				gradePoint = gradePoint + itemGrade.getUnits() * 1.3;
			else if( g >= 63 && g < 67)
				gradePoint = gradePoint + itemGrade.getUnits() * 1;
			else if (g >= 60 && g < 63)
				gradePoint = gradePoint + itemGrade.getUnits() * 0.7;
			else if (g <60)
				gradePoint = gradePoint + itemGrade.getUnits() * 0;
		}
		if(i == 0)
			totalGpa.setText("0");
		else{
			DecimalFormat df = new DecimalFormat("##.###");
			totalGpa.setText((df.format(gradePoint / totalUnits)));
		}
	}
}
