package com.example.plantrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditClass extends Activity{
	private Class cls;
	private EditText text;
	private Button addButton;
	
	public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.add_class_view);
	       text = (EditText) findViewById(R.id.newClassEditText);
		   addButton = (Button) findViewById(R.id.addClassButton);
		   addButton.setText("Save");
		   text.setText(getIntent().getStringExtra("title"));
		   initAddClassListeners();
	}
	public String getClassTitle(){
		return this.cls.getTitle();
	}
	
	protected void initAddClassListeners() {
		addButton.setOnClickListener(new OnClickListener() {
			  public void onClick(View view) {
				  if(text.getText().toString().length() >0) {
					  setClass();
					  Intent returnIntent = new Intent();
					  returnIntent.putExtra("result", getClassTitle());
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
