package com.example.plantrack;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.example.plantrack.ClassView.OnClassChangeListener;


public class Grades extends SherlockFragment implements LoaderManager.LoaderCallbacks<Cursor>, OnClassChangeListener{	

	private ClassCursorAdapter classAdapter;
	protected ArrayList<Class> arrClassList;
	private Class course;
	public ListView gradeLayout;
	public Button addClass;
	public Button classButton;	
	protected int selected_position;
	protected int selected_pos;
	private ClassView ite;
	private int pos;
	private static final int LOADER_ID = 1;
	public static final String SHOW_ALL = "" + 4;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       return inflater.inflate(R.layout.grades_layout, container, false);
    }

	public void onActivityCreated(Bundle savedInstanceState){		
		super.onActivityCreated(savedInstanceState);
	    this.arrClassList = new ArrayList<Class>();
	    this.gradeLayout = (ListView) getView().findViewById(R.id.ClassListViewGroup);
	    this.classAdapter = new ClassCursorAdapter(this.getActivity(), null, 0);
	    this.gradeLayout.setAdapter(this.classAdapter);
	    addClass = (Button) getView().findViewById(R.id.addClassButton);
	    registerForContextMenu(this.gradeLayout);
	    getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);
	    initListener();
		
	}
	
	@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
        pos = info.position;
        ite = (ClassView) this.gradeLayout.getChildAt(pos);
        menu.add(Menu.NONE, R.id.edit, Menu.NONE, "Edit");
        menu.add(Menu.NONE, R.id.remove, Menu.NONE, "Remove");
    }
	
	@Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
        switch(item.getItemId()) {
        case R.id.edit:
        	 Intent i = new Intent(getActivity(), EditClass.class);
             i.putExtra("title", ite.getClassTitle());
             startActivityForResult(i, 2);
            return true;
        case R.id.remove:
        	removeClass(((ClassView)this.gradeLayout.getChildAt(pos)).getCourse());
        }
        return super.onContextItemSelected(item);
    }
	
	public void removeClass(Class course) {
		Uri uri = Uri.parse(GradesContentProvider.CLASS_CONTENT_URI + "/classes/" + course.getClassId());
		this.getActivity().getContentResolver().delete(uri, null, null);
		
		fillData();
	}
	
	private void initListener() {
		this.addClass.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Grades.this.getActivity(), AddClassView.class);
				startActivityForResult(i, 1);	
				//addClassView();
			}
			
		});
		 this.gradeLayout.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {	
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				ListOfItems listofitems = new ListOfItems();				
				ft.addToBackStack(null);
				Fragment f = fm.findFragmentById(R.id.pager);
				ft.remove(f);
				//ft.add(listofitems,  "" + ((ClassView)gradeLayout.getChildAt(position)).getCourse().getClassId());
				ft.replace(R.id.pager, listofitems, "" + ((ClassView)gradeLayout.getChildAt(position)).getCourse().getClassId());
				ft.commit();
			} 
		 });
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		  if (requestCode == 1) {
		     if(resultCode == -1){      
		    	 /*TO-DO
		    	  * DON'T FORGET TOGET WEIGHTS FROM RESULT TO WEIGHT THE CLASS PROPERLY
		    	  * MAY NEED TO ADD WEIGHT TABLE COLUMNS: id, category, weight, class_id*/
		         String class_Name = data.getStringExtra("result");
		         course = new Class(class_Name);
		 		Uri uri = Uri.parse(GradesContentProvider.CLASS_CONTENT_URI + "/classes/" + course.getClassId());
				ContentValues values = new ContentValues();
				values.put(ClassTable.CLASS_KEY_NAME, course.getTitle());
				
				uri = this.getActivity().getContentResolver().insert(uri, values);
				String path = uri.getLastPathSegment();
				int id = Integer.parseInt(path);
				course.setClassId(id);
				
				fillData();
		     }
		     if (resultCode == 0) {    
		     }
		  }
		  
		  if (requestCode == 2) {
				if(resultCode == -1){      
					String result = data.getStringExtra("result");
					Class course = new Class(result);
					ite.setClass(course);
					
					Uri uri = Uri.parse(GradesContentProvider.CLASS_CONTENT_URI + "/classes/" + ite.getCourse().getClassId());
					ContentValues values = new ContentValues();
					values.put(ClassTable.CLASS_KEY_NAME, ite.getClassTitle());
					
					this.getActivity().getContentResolver().update(uri, values, null, null);
					fillData();
				}
				if (resultCode == 0) {    
				}
			}
		}//onActivityResult
	
	public int getCourseId() {
		if (this.course == null) {
			return 4;
		}
		else {
			return this.course.getClassId();
		}
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		// TODO Auto-generated method stub
		String[] strings = {ClassTable.CLASS_KEY_ID, ClassTable.CLASS_KEY_NAME,
		 ClassTable.CLASS_KEY_QUARTER};
		Uri uri = Uri.parse(GradesContentProvider.CLASS_CONTENT_URI + 
		 "/classes/" + SHOW_ALL);
		CursorLoader loader = new CursorLoader(this.getActivity(), uri, strings, null, null, null);
		
		return loader;
	}
	
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// TODO Auto-generated method stub

		this.classAdapter.swapCursor(cursor);
		this.classAdapter.setOnClassChangedListener(this);
		
	}
	
	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		this.classAdapter.swapCursor(null);
		
	}

	@Override
	public void onClassChanged(ClassView view, Class course) {
		// TODO Auto-generated method stub
		Uri uri = Uri.parse(GradesContentProvider.CLASS_CONTENT_URI + "/classes/" + course.getClassId());
		ContentValues values = new ContentValues();
		values.put(ClassTable.CLASS_KEY_NAME, course.getTitle());
		
		this.classAdapter.setOnClassChangedListener(null);
		this.getActivity().getContentResolver().update(uri, values, null, null);
	
		fillData();
	}
	
	protected void fillData() {
		this.getActivity().getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
		this.gradeLayout.setAdapter(classAdapter);
		
	}

}