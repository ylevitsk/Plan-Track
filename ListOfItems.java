package com.example.plantrack;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.plantrack.ItemView.OnItemChangeListener;

public class ListOfItems extends SherlockFragment implements LoaderManager.LoaderCallbacks<Cursor>, OnItemChangeListener {
	public Button addItem;
	public LinearLayout layout;
	protected ArrayList<Item> arrItemList;
	public ItemCursorAdapter quizAdapter;
	public ItemCursorAdapter hwAdapter;
	public ItemCursorAdapter projAdapter;
	public ItemCursorAdapter midtermAdapter;
	public ItemCursorAdapter finalAdapter;
	public ListView quiz_layout;
	public ListView hw_layout;
	public ListView proj_layout;
	public ListView midterm_layout;
	public ListView final_layout;
	public Item item;
	public TextView grade;
	public int class_id;
	private static final int LOADER_ID = 2;
	public static final String SHOW_ALL = "" + 4;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       return inflater.inflate(R.layout.item_layout, container, false);
	}
	public void onActivityCreated(Bundle savedInstanceState) {
	       super.onActivityCreated(savedInstanceState);
	       this.layout = (LinearLayout) getView().findViewById(R.id.item_layout);
	       this.arrItemList = new ArrayList<Item>();
	       this.quizAdapter = new ItemCursorAdapter(this.getActivity(), null, 0);
	       this.hwAdapter = new ItemCursorAdapter(this.getActivity(), null, 0);
	       this.projAdapter = new ItemCursorAdapter(this.getActivity(), null, 0);
	       this.midtermAdapter = new ItemCursorAdapter(this.getActivity(), null, 0);
	       this.finalAdapter = new ItemCursorAdapter(this.getActivity(), null, 0);
	       this.quiz_layout = (ListView) getView().findViewById(R.id.ViewGroup_quiz);
	       this.hw_layout = (ListView) getView().findViewById(R.id.ViewGroup_hw);
	       this.proj_layout = (ListView)getView().findViewById(R.id.ViewGroup_proj);
	       this.midterm_layout = (ListView) getView().findViewById(R.id.ViewGroup_midterm);
	       this.final_layout = (ListView) getView().findViewById(R.id.ViewGroup_final);
	       this.quiz_layout.setAdapter(this.quizAdapter);
	       this.hw_layout.setAdapter(hwAdapter);
	       this.proj_layout.setAdapter(projAdapter);
	       this.midterm_layout.setAdapter(midtermAdapter);
	       this.final_layout.setAdapter(finalAdapter);
	       this.getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);
	       this.class_id = Integer.parseInt(this.getTag());
	       addItem = (Button) getView().findViewById(R.id.addItemButton);
	       grade = (TextView) getView().findViewById(R.id.items_grade);
	       initListener();	
	       
	}
	private void initListener() {
		addItem.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ListOfItems.this.getActivity(), AddItemView.class);
				startActivityForResult(i, 1);
			}
			
		});
		/*
		weightTests.setOnKeyListener(new OnKeyListener(){
			public boolean onKey(View view, int keyCode, KeyEvent event) {
				if((keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
						&& event.getAction() == KeyEvent.ACTION_DOWN){
					grade.setText(calculateGrade());
					return true;
				 }
				return false;
			}
		});
		weightHw.setOnKeyListener(new OnKeyListener(){
			public boolean onKey(View view, int keyCode, KeyEvent event) {
				if((keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
						&& event.getAction() == KeyEvent.ACTION_DOWN){
					grade.setText(calculateGrade());
					return true;
				 }
				return false;
			}
		});
		weightProj.setOnKeyListener(new OnKeyListener(){
			public boolean onKey(View view, int keyCode, KeyEvent event) {
				if((keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
						&& event.getAction() == KeyEvent.ACTION_DOWN){
					grade.setText(calculateGrade());
					return true;
				 }
				return false;
			}
		});
		weightMidterms.setOnKeyListener(new OnKeyListener(){
			public boolean onKey(View view, int keyCode, KeyEvent event) {
				if((keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
						&& event.getAction() == KeyEvent.ACTION_DOWN){
					grade.setText(calculateGrade());
					return true;
				 }
				return false;
			}
		});
		weightFinal.setOnKeyListener(new OnKeyListener(){
			public boolean onKey(View view, int keyCode, KeyEvent event) {
				if((keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
						&& event.getAction() == KeyEvent.ACTION_DOWN){
					grade.setText(calculateGrade());
					return true;
				 }
				return false;
			}
		});*/
	}
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
	    builder.setTitle("Pick a Category");
	           builder.setItems(R.array.cat_array, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	               // The 'which' argument contains the index position
	               // of the selected item
	           }
	    });
	    return builder.create();
	}
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("" + requestCode + ", " + data);
		if (requestCode == 1) {
		if(resultCode == -1){      
				 /*Depending on which category they put the assignment in, we will put each grade in a different itemList
				  * we can create a hashtable of categories and keep track that way*/
		         String title = data.getStringExtra("title");
		         String category = data.getStringExtra("category");
		         String grade = data.getStringExtra("grade");
		         item = new Item(title, category, Double.parseDouble(grade));
		         
	        	 Uri uri = Uri.parse(GradesContentProvider.ITEM_CONTENT_URI + "/items/" + item.getItemId());
				 ContentValues values = new ContentValues();
				 values.put(ItemTable.ITEM_KEY_NAME, item.getTitle());
				 values.put(ItemTable.ITEM_KEY_CATEGORY, item.getCategory());
				 values.put(ItemTable.ITEM_KEY_GRADE, item.getPercent());
				 values.put(ItemTable.ITEM_KEY_CLASSID, class_id);
				 
				 uri = this.getActivity().getContentResolver().insert(uri, values);
				 String path = uri.getLastPathSegment();
				 int id = Integer.parseInt(path);
				 item.setItemId(id);
				 
				 if (item.getCategory().equals("Homework")) {
					 fillHwData();
				 }
				 else if (item.getCategory().equals("Quiz")) {
					 fillQuizData();
				 }
				 else if (item.getCategory().equals("Midterm")) {
					 fillMidtermData();
				 }
				 else if (item.getCategory().equals("Project")) {
					 fillProjectData();
				 }
				 else if (item.getCategory().equals("Final")) {
					 fillFinalData();
				 }
				 
	        	 
		     }
		     if (resultCode == 0) {    
		         //Write your code if there's no result
		     }
		  }
	}//onActivityResult
	
	@Override
	public void onItemChanged(ItemView view, Item item) {
		// TODO Auto-generated method stub
		Uri uri = Uri.parse(GradesContentProvider.ITEM_CONTENT_URI + "/items/" + item.getItemId());
		ContentValues values = new ContentValues();
		values.put(ItemTable.ITEM_KEY_NAME, item.getTitle());
		values.put(ItemTable.ITEM_KEY_CATEGORY, item.getCategory());
		values.put(ItemTable.ITEM_KEY_GRADE, item.getPercent());
		
		if (item.getCategory().equals("quizs")) {

			this.quizAdapter.setOnItemChangeListener(null);
			this.getActivity().getContentResolver().update(uri, values, null, null);
			fillQuizData();
		}
		else if (item.getCategory().equals("Midterms")) {
			this.midtermAdapter.setOnItemChangeListener(null);
			this.getActivity().getContentResolver().update(uri, values, null, null);
			fillMidtermData();
		}
		else if (item.getCategory().equals("Homework")) {
			this.hwAdapter.setOnItemChangeListener(null);
			this.getActivity().getContentResolver().update(uri, values, null, null);
			fillHwData();
		}
		else if (item.getCategory().equals("Projects")) {
			this.projAdapter.setOnItemChangeListener(null);
			this.getActivity().getContentResolver().update(uri, values, null, null);
			fillProjectData();
		}
		else if (item.getCategory().equals("Final")) {
			this.finalAdapter.setOnItemChangeListener(null);
			this.getActivity().getContentResolver().update(uri, values, null, null);
			fillFinalData();
		}

	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		// TODO Auto-generated method stub
		String[] strings = {ItemTable.ITEM_KEY_ID, ItemTable.ITEM_KEY_NAME,
		 ItemTable.ITEM_KEY_GRADE, ItemTable.ITEM_KEY_CATEGORY, ItemTable.ITEM_KEY_CLASSID};
		Uri uri = Uri.parse(GradesContentProvider.ITEM_CONTENT_URI + 
		 "/items_courseId/" + class_id);
		CursorLoader loader = new CursorLoader(this.getActivity(), uri, strings, null, null, null);
				
		return loader;
	}
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// TODO Auto-generated method stub
		if (item != null) {

			if (item.getCategory().equals("Homework")) {

				this.hwAdapter.swapCursor(cursor);
				this.hwAdapter.setOnItemChangeListener(this);
			}
			else if (item.getCategory().equals("Quiz")) {

				this.quizAdapter.swapCursor(cursor);
				this.quizAdapter.setOnItemChangeListener(this);
			}
			else if (item.getCategory().equals("Project")) {
				
				this.projAdapter.swapCursor(cursor);
				this.projAdapter.setOnItemChangeListener(this);
			}
			else if (item.getCategory().equals("Midterm")) {

				this.midtermAdapter.swapCursor(cursor);
				this.midtermAdapter.setOnItemChangeListener(this);
			}
			else if (item.getCategory().equals("Final")) {

				this.finalAdapter.swapCursor(cursor);
				this.finalAdapter.setOnItemChangeListener(this);
			}
		}
		else {

			this.hwAdapter.swapCursor(cursor);
			this.hwAdapter.setOnItemChangeListener(this);
			this.quizAdapter.swapCursor(cursor);
			this.quizAdapter.setOnItemChangeListener(this);
			this.projAdapter.swapCursor(cursor);
			this.projAdapter.setOnItemChangeListener(this);
			this.midtermAdapter.swapCursor(cursor);
			this.midtermAdapter.setOnItemChangeListener(this);
			this.finalAdapter.swapCursor(cursor);
			this.finalAdapter.setOnItemChangeListener(this);
		}
	}
	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		this.hwAdapter.swapCursor(null);
		this.quizAdapter.swapCursor(null);
		this.projAdapter.swapCursor(null);
		this.midtermAdapter.swapCursor(null);
		this.finalAdapter.swapCursor(null);
		
	}
	

	protected void fillQuizData() {
		this.getActivity().getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
		this.quiz_layout.setAdapter(this.quizAdapter);
		
	}
	
	protected void fillHwData() {
		this.getActivity().getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
		this.hw_layout.setAdapter(this.hwAdapter);
	}
	
	protected void fillProjectData() {
		this.getActivity().getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
		this.proj_layout.setAdapter(this.projAdapter);
	}
	
	protected void fillMidtermData() {
		this.getActivity().getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
		this.midterm_layout.setAdapter(this.midtermAdapter);
	}
	
	protected void fillFinalData() {
		this.getActivity().getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
		this.final_layout.setAdapter(this.finalAdapter);
	}
	
}
