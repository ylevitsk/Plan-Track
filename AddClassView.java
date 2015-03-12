package com.example.plantrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddClassView extends Activity{
	private Class cls;
	private EditText text;
	private Button addButton;
	private EditText projects;
	private EditText quizzes;
	private EditText midterms;
	private EditText homework;
	private EditText labs;
	private EditText final_exam;
	
	public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.add_class_view);
	       text = (EditText) findViewById(R.id.newClassEditText);
		   addButton = (Button) findViewById(R.id.addClassButton);
		   projects = (EditText) findViewById(R.id.weight_projects);
		   quizzes = (EditText) findViewById(R.id.weight_quizzes);
		   midterms = (EditText) findViewById(R.id.weight_midterms);
		   homework = (EditText) findViewById(R.id.weight_hw);
		   labs = (EditText) findViewById(R.id.weight_labs);
		   final_exam = (EditText) findViewById(R.id.weight_final);
		   initAddClassListeners();
	}
	
	public String getClassTitle(){
		return this.cls.getTitle();
	}
	
	protected void initAddClassListeners() {
		addButton.setOnClickListener(new OnClickListener() {
			  public void onClick(View view) {
				  if(text.getText().toString().length() > 0) {
					  setClass();
					  Intent returnIntent = new Intent();
					  returnIntent.putExtra("result", getClassTitle());
					  if (projects.getText().toString().length() > 0) {

						  returnIntent.putExtra("project_weight", projects.getText().toString());
					  }
					  if (quizzes.getText().toString().length() > 0) {

						  returnIntent.putExtra("quizzes_weight", quizzes.getText().toString());
					  }
					  if (midterms.getText().toString().length() > 0) {

						  returnIntent.putExtra("midterms_weight", midterms.getText().toString());
					  }
					  if (homework.getText().toString().length() > 0) {

						  returnIntent.putExtra("homework_weight", homework.getText().toString());
					  }
					  if (labs.getText().toString().length() > 0) {

						  returnIntent.putExtra("labs_weight", labs.getText().toString());
					  }
					  if (final_exam.getText().toString().length() > 0 ) {

						  returnIntent.putExtra("final_exam_weight", final_exam.getText().toString());
					  }
					  if (returnIntent.getExtras().size() < 2) {
						  Toast.makeText(AddClassView.this, "Please enter weights for categories in your class", Toast.LENGTH_SHORT).show();
					  }
					  setResult(RESULT_OK, returnIntent);     
					  finish();
				  }
			  }
		});
	}
	public void setClass() {
		cls = new Class(text.getText().toString());
		
	}
	public Class getC(){
		return cls;
	}

}
