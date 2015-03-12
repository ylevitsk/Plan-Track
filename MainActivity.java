package com.example.plantrack;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockFragmentActivity {
	private ViewPager mViewPager;
    private TabsAdapter mTabsAdapter;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private static String AddFriendMenuEditTextValue;
    protected static MenuItem searchMenuItem;
    protected static MenuItem addFriendMenuItem;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       settings = PreferenceManager.getDefaultSharedPreferences(this);

       Intent i = getIntent();
       if (savedInstanceState == null) {
          initLayout();
          initTabs();
       }
    }

    public void setupUI(View view) {
       //Set up touch listener for non-text box views to hide keyboard.
       if(!(view instanceof EditText)) {
          view.setOnTouchListener(new OnTouchListener() {
    	     @Override
             public boolean onTouch(View v, MotionEvent event) {
    	    	 return false;
             }
          });
       }
       //If a layout container, iterate over children and seed recursion.
       if (view instanceof ViewGroup) {
          for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
             View innerView = ((ViewGroup) view).getChildAt(i);
             setupUI(innerView);
          }
       }
    }

    private void initLayout() {
       mViewPager = new ViewPager(this);
       mViewPager.setId(R.id.pager);
       setContentView(mViewPager);
       setupUI(findViewById(R.id.pager));
    }
    private void initTabs() {
       ActionBar actionBar = getSupportActionBar();
       actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
       mTabsAdapter = new TabsAdapter(this, mViewPager);
       mTabsAdapter.addTab(actionBar.newTab().setText("Planner"), Planner.class, null);
       mTabsAdapter.addTab(actionBar.newTab().setText("Grades"), Grades.class, null);
       mTabsAdapter.addTab(actionBar.newTab().setText("GPA"), Gpa.class, null);
       mViewPager.setCurrentItem(0);
    }

    static class TabsAdapter extends FragmentStatePagerAdapter  implements ActionBar.TabListener, ViewPager.OnPageChangeListener {
       private  Context mContext;
       private  ActionBar mActionBar;
       private  ViewPager mViewPager;
       private  ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
			
       static final class TabInfo {
          private final Bundle args;
          public java.lang.Class cl4ss;
          TabInfo(java.lang.Class clss, Bundle _args){
             cl4ss = clss;
             args = _args;
          }
       }

       public TabsAdapter(SherlockFragmentActivity activity, ViewPager pager) {
          super(activity.getSupportFragmentManager());
          mContext = activity;
          mActionBar = activity.getSupportActionBar();
          mViewPager = pager;
          mViewPager.setAdapter(this);
          mViewPager.setOnPageChangeListener(this);
       }

       public  void addTab(ActionBar.Tab tab, java.lang.Class clss, Bundle args) {
          TabInfo info = new TabInfo(clss, args);
          tab.setTag(info);
          tab.setTabListener(this);
          mTabs.add(info);
          mActionBar.addTab(tab);
          notifyDataSetChanged();
       }

       @Override
       public int getCount() {
          return mTabs.size();
       }
       @Override
       public Fragment getItem(int position) {
          TabInfo info = mTabs.get(position);
          return Fragment.instantiate(mContext, info.cl4ss.getName(), info.args);
       }
       public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {                
       }
       public void onPageSelected(int position) {
          mActionBar.setSelectedNavigationItem(position);
       }
       public void onTabSelected(Tab tab, FragmentTransaction ft) {
          Object tag = tab.getTag();
          for (int i = 0; i < mTabs.size(); i++){
             if (mTabs.get(i) == tag){
                mViewPager.setCurrentItem(i);
             }
          }
       }
       public void onTabUnselected(Tab tab, FragmentTransaction ft) {                
       }
       public void onTabReselected(Tab tab, FragmentTransaction ft) {                
       }
       @Override
       public void onPageScrollStateChanged(int arg0) {                
       }
   }
}
//	 public static String TAG = "EPE";
//
//	    @Override
//	 public void onCreate(Bundle savedInstanceState) {
//	     super.onCreate(savedInstanceState);
//
//	   // setup action bar for tabs
//	      setContentView(R.layout.activity_main);
//
//	      ActionBar actionBar = this.getSupportActionBar();
//	      actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//	      actionBar.setDisplayShowTitleEnabled(false);
//
//	      actionBar.setTitle("A and B");
//
//	      Tab plannerTab = actionBar
//	              .newTab()
//	              .setText("Planner");
//	       plannerTab.setTabListener(new PlannerTabListener(this));
//
//	       actionBar.addTab(plannerTab);
//
//	       Tab gradesTab = actionBar
//	                    .newTab()
//	                    .setText("Grades");
//	       gradesTab.setTabListener(new GradesTabListener(this));
//	       actionBar.addTab(gradesTab);
//	       
//	       Tab gpaTab = actionBar
//                   .newTab()
//                   .setText("GPA");
//	       gpaTab.setTabListener(new GpaTabListener(this));
//	       actionBar.addTab(gpaTab);
//	    }
//	    
//	    
//
//		 public static class PlannerTabListener implements ActionBar.TabListener {
//
//		        // FIXME: is this really needed?
//		        private static final String plannerTag = "Planner";
//		        private final SherlockFragmentActivity mActivity;
//		        private ArrayList<Fragment> fragList;
//
//		        public PlannerTabListener(SherlockFragmentActivity activity) {
//		            mActivity = activity;
//		            fragList  = null;
//		        }
//
//		        public void onTabReselected(Tab tab, FragmentTransaction ft) {
//		            // Reselected don't do anything 
//		            Log.d(TAG, "Tab A: on Tab reselected");
//		        }
//
//		        
//		        public void onTabSelected(Tab tab, FragmentTransaction ft) {
//
//		            Log.d(TAG, "Tab A: on Tab Selected");
//
//	                Planner plannerFrag = new Planner();
//	              
//
//		            // attach all the fragments
//		            if(fragList == null) {
//
//		                fragList  = new ArrayList<Fragment>();
//		                ft.replace(R.id.container_widgets, plannerFrag,  plannerTag);
//		            
//		                fragList.add(plannerFrag);
//		                Log.d(TAG, "Tab A: Added fragments to the ArrayList");
//
//		            } else {
//
//		                Iterator<Fragment> iter = fragList.iterator();
//
//		                while (iter.hasNext()) {
//		                    Log.d(TAG, "Tab A: Attaching fragments");
//		                    ft.attach(iter.next());
//		                    ft.replace(R.id.container_widgets, plannerFrag);
//		                }
//		            }
//		        }
//
//		        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
//		            Log.d(TAG, "Tab A: on Tab Unselected");
//		            {  
//		                Iterator<Fragment> iter = fragList.iterator();
//		                while (iter.hasNext())
//		                {
//		                    Log.d(TAG, "Tab A: Fragments detached");
//		                    ft.detach(iter.next());
//		                }
//		            }
//		        }
//		    }	    
//
//
//	    public class GradesTabListener implements ActionBar.TabListener {
//
//	        private final SherlockFragmentActivity mActivity;
//	        private ArrayList<Fragment> fragList;
//	        private static final String gradesTag = "Grades";
//
//	        public GradesTabListener(SherlockFragmentActivity activity) {
//	            mActivity = activity;
//	            fragList = null;
//	        }
//
//	        public void onTabReselected(Tab tab, FragmentTransaction ft) {
//	            // Reselected don't do anything
//	            Log.d(TAG, "Tab B: on Tab reselected");
//	        }
//
//	        public void onTabSelected(Tab tab, FragmentTransaction ft) {
//	        	Grades gradeFrag = new Grades();
//	            Log.d(TAG, "Tab B: on Tab Selected");
//
//	            if (fragList == null) {
//	                fragList = new ArrayList<Fragment>();
//	                ft.replace(R.id.container_widgets, gradeFrag, gradesTag);
//	                fragList.add(gradeFrag); 
//	                Log.d(TAG, "Tab B: Added fragments to the ArrayList");
//	            } else {
//	                Iterator<Fragment> iter = fragList.iterator();
//	                while (iter.hasNext()) {
//	                    Log.d(TAG, "Tab B: Attaching fragments");
//	                    ft.attach((Fragment) iter.next());
//	                    ft.replace(R.id.container_widgets, gradeFrag, gradesTag);
//	                }
//	            }
//	        }
//	        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
//	            Log.d(TAG, "Tab B: on Tab Unselected");
//
//	            if (fragList != null) {
//	                Iterator<Fragment> iter = fragList.iterator();
//	                while (iter.hasNext()) {
//	                    Log.d(TAG, "Tab B: Fragments detached");
//	                    ft.detach((Fragment) iter.next());
//	                }
//	            }
//	        }
//	    }
//
//
//	    public static class GpaTabListener implements ActionBar.TabListener {
//
//	        // FIXME: is this really needed?
//	        private final SherlockFragmentActivity mActivity;
//	        private ArrayList<Fragment> fragList;
//
//	        public GpaTabListener(SherlockFragmentActivity activity) {
//	            this.mActivity = activity;
//	            fragList = null;
//	        }
//
//	        public void onTabReselected(Tab tab, FragmentTransaction ft) {
//	            // Reselected don't do anything
//	            Log.d(TAG, "Tab C: on Tab reselected");
//	        }
//
//	        public void onTabSelected(Tab tab, FragmentTransaction ft) {
//	            Log.d(TAG, "Tab C: on Tab Selected");
//	            Gpa gpaFrag = new Gpa();
//	            if (fragList == null) {
//
//	                fragList = new ArrayList<Fragment>();	                
//	                ft.replace(R.id.container_widgets, gpaFrag);
//	                
//	                fragList.add(gpaFrag);
//	                
//	                Log.d(TAG, "Tab C: Added fragments to the ArrayList");
//
//	            } else {
//	                Iterator<Fragment> iter = fragList.iterator();
//	                while (iter.hasNext()) {
//	                    Log.d(TAG, "Tab B: Attaching fragments");
//	                    ft.attach((Fragment) iter.next());    
//	                }
//	                ft.replace(R.id.container_widgets, gpaFrag);
//	            }
//	        }
//	        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
//	            Log.d(TAG, "Tab B: on Tab Unselected");
//
//	            if (fragList != null) {
//	                Iterator<Fragment> iter = fragList.iterator();
//	                while (iter.hasNext()) {
//	                    Log.d(TAG, "Tab C: Fragments detached");
//	                    ft.detach((Fragment) iter.next());
//	                }
//	            }
//	        }
//	    }
//
//		public void onBackPressed() {
//			FragmentManager fm = getSupportFragmentManager();
//			if(fm.getBackStackEntryCount() >0) {
//				fm.popBackStack();
//			} 
//			else {
//				super.onBackPressed();
//			}
//			
//		}
//}
//
//
