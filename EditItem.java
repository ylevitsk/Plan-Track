package com.example.plantrack;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class EditItem extends Activity {
	private Item cls;
	private Button saveButton;
	EditText title, category, grade;
	Spinner spinner;
	List<String> list;
	
	public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.item_view);
		   saveButton = (Button) findViewById(R.id.addItButton);
		   saveButton.setText("Save");
		   title = (EditText) findViewById(R.id.class_title);
		   grade = (EditText) findViewById(R.id.class_grade);
		   addItemsOnSpinner2();
		   addListenerOnSpinnerItemSelection();
		   title.setText(getIntent().getStringExtra("title"));
		   grade.setText(getIntent().getStringExtra("percent"));
		   initAddClassListeners();
		   int i =0;
		   for(i = 0; i< list.size(); i++){
			   if(list.get(i).equalsIgnoreCase(getIntent().getStringExtra("category")))
				   break;
		   }
		   spinner.setSelection(i);
	}
	// add items into spinner dynamically
	  public void addItemsOnSpinner2() {
	 
		spinner = (Spinner) findViewById(R.id.spinner1);
		list = new ArrayList<String>();
		list.add("Test");
		list.add("Quiz");
		list.add("Projects");
		list.add("Homework");
		list.add("Midterms");
		list.add("Final");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
	  }
	 
	  public void addListenerOnSpinnerItemSelection() {
		spinner = (Spinner) findViewById(R.id.spinner1);
		spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	  }
	private void initAddClassListeners() {
		saveButton.setOnClickListener(new OnClickListener() {
			  public void onClick(View view) {
				  if(title.getText().toString().length() >0 &&
						  String.valueOf(spinner.getSelectedItem()).length() >0 &&
						  grade.getText().toString().length() > 0){
					  Intent returnIntent = new Intent();
					  returnIntent.putExtra("title", title.getText().toString());
					  returnIntent.putExtra("category", String.valueOf(spinner.getSelectedItem()));
					  returnIntent.putExtra("grade", grade.getText().toString());
					  setResult(RESULT_OK, returnIntent);     
					  finish();
				  }
			  }	
		});
		
	}
	
	public String getClassTitle(){
		return this.cls.getTitle();
	}
}
