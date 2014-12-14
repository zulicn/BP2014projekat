package com.bpprojekat2014;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bpprojekat2014.classes.Aktivnost;
import com.bpprojekat2014.classes.AppController;
import com.bpprojekat2014.classes.Project;
import com.bpprojekat2014.classes.Projects;
import com.bpprojekat2014.classes.Task;
import com.bpprojekat2014.classes.adapter.NavDrawerListAdapter;
import android.app.Fragment;
import com.bpprojekat2014.classes.fragment.HomeFragment;
import com.bpprojekat2014.classes.model.NavDrawerItem;
//import com.bpprojekat2014.classes.Project;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.bpprojekat2014.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
public class HomeActivity extends Activity {
	

	private Projects projects = new Projects();
	
	private String TAG="JSON_TAG_PROJECTS";
	private String TAG2="JSON_TAG_GPROJECTS";
	private String username;
	private String key;
	private String  jsonResponse;
	
	//za one tabove
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
 
    // nav drawer title
    private CharSequence mDrawerTitle;
 
    // used to store app title
    private CharSequence mTitle;
 
    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
 
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		/*
		makeProjectsRequest();
		 txtResponse = (TextView) findViewById(R.id.txtResponse);
	     */  
	 //za tabove
		 mTitle = getTitle();
		 mDrawerTitle = getTitle();
	        // load slide menu items
	        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
	 
	        // nav drawer icons from resources
	        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
	 
	        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
	 
	        navDrawerItems = new ArrayList<NavDrawerItem>();
	 
	        // adding nav drawer items to array
	        // Home
	        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
	        // My profile
	        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
	        // Create new project
	        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
	        // Notifications Will add a counter here
	        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
	        // My projects
	        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
	        // Assigned projects  We  will add a counter here
	        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));
	         //Archieved projects 
	        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1), true, "0"));
	        
	 
	        // Recycle the typed array
	        navMenuIcons.recycle();
	        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
	        // setting the nav drawer list adapter
	        adapter = new NavDrawerListAdapter(getApplicationContext(),
	                navDrawerItems);
	        mDrawerList.setAdapter(adapter);
	 
	        // enabling action bar app icon and behaving it as toggle button
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setHomeButtonEnabled(true);
	 
	        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
	                R.drawable.ic_drawer, //nav menu toggle icon
	                R.string.app_name, // nav drawer open - description for accessibility
	                R.string.app_name // nav drawer close - description for accessibility
	        ){
	            public void onDrawerClosed(View view) {
	                getActionBar().setTitle(mTitle);
	                // calling onPrepareOptionsMenu() to show action bar icons
	                invalidateOptionsMenu();
	            }
	 
	            public void onDrawerOpened(View drawerView) {
	                getActionBar().setTitle(mDrawerTitle);
	                // calling onPrepareOptionsMenu() to hide action bar icons
	                invalidateOptionsMenu();
	            }
	        };
	        mDrawerLayout.setDrawerListener(mDrawerToggle);
	 
	        if (savedInstanceState == null) {
	            // on first time display view for first nav item
	            displayView(0);
	        }

	} 
	//this retreives all projects of user, no meter if they are archived 
	public void makeProjectsRequest(){
	
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
	    username=pref.getString("username",null);
		key=pref.getString("key",null);
		String url = String.format("http://projectmng.herokuapp.com/projects/all.json?username=%1$s&key=%2$s",username,key);
		String tag_json_arry = "json_array_req";
		final ProgressDialog pDialog = new ProgressDialog(this);   
		
		try
		{	
			// mora da mu ide string!
			String jsonStr = "{\"projects\":[{\"id\":1,\"name\":\"BP project\",\"short_description\":" +
					"\"Project management and meeting scheduling tool\",\"long_description\":null,\"start_date\"" +
					":\"2014-11-02\",\"end_date\":\"2015-01-02\",\"duration\":20000,\"member_count\":5,\"budget\":0.0," +
					"\"finished\":false,\"created_at\":\"2014-11-02T17:30:12.000Z\",\"updated_at\":\"2014-11-02T17:30:12.000Z\"," +
					"\"activities\":[{\"id\":2,\"project_id\":1,\"name\":\"Implementation\",\"description\":\"" +
					"Implement the Rails web app, and Android app\",\"duration\":5,\"finished\":false,\"created_at\":\"2014-11-02T19:09:55.000Z\"" +
					",\"updated_at\":\"2014-11-02T19:09:55.000Z\",\"tasks\":[]}]},{\"id\":3,\"name\":\"test\",\"" +
				"short_description\":\"test\",\"long_description\":\"teste\",\"start_date\":\"" +
						"2014-11-12\",\"end_date\":null,\"duration\":39,\"member_count\":5,\"budget\":1000.0," +
						"\"finished\":false,\"created_at\":\"2014-12-11T09:54:57.893Z\",\"updated_at\":\"" +
						"2014-12-11T09:54:57.893Z\",\"activities\":[]}]}";
			JSONObject obj = new JSONObject(jsonStr);
			JSONArray jProjects = obj.getJSONArray("projects");
			
			for (int i = 0; i < jProjects.length(); i++)
			{
				Project p = new Project();
				JSONObject pr = null;
				if(jProjects.isNull(i) == false)
				{
					pr = (JSONObject) jProjects.get(i);
				    p.setProject_id(pr.getInt("id"));
					p.setName(pr.getString("name"));
					p.setShort_description(pr.getString("short_description"));
					p.setLong_description(pr.getString("long_description"));
					p.setStart_date(pr.getString("start_date"));
					p.setEnd_date(pr.getString("end_date"));
					p.setDuration(pr.getInt("duration"));
					p.setMember_count(pr.getInt("member_count"));
					p.setBudget(pr.getDouble("budget"));
					p.setFinished(pr.getBoolean("finished"));
					p.setCreated_at(pr.getString("created_at"));
					p.setUpdated_at(pr.getString("updated_at"));
				}
				else{break;}
				
				JSONArray jAktivnosti = pr.getJSONArray("activities");
				
				for (int j = 0; i < jAktivnosti.length(); j++)
				{
					JSONObject a1 = null;
					Aktivnost a = new Aktivnost();
					if(jAktivnosti.isNull(j) == false)
					{
						a1 = (JSONObject) jAktivnosti.get(j);
						
						a.setActivity_id(a1.getInt("id"));
						a.setProject_id(a1.getInt("project_id"));
						a.setName(a1.getString("name"));
						a.setDescription(a1.getString("description"));
						a.setDuration(a1.getInt("duration"));
						a.setFinished(a1.getBoolean("finished"));
						a.setCreated_at(a1.getString("created_at"));
						a.setUpdated_at(a1.getString("updated_at"));
					}
					else{break;}
					
					JSONArray jTasks = a1.getJSONArray("tasks");
					
					for (int k = 0; i < jTasks.length(); k++)
					{
						JSONObject t1 = null;
						
						if(jTasks.isNull(5) == false)
						{
							Task t = new Task();
							t1 = (JSONObject) jTasks.get(k);
							
							t.setTask_id(t1.getInt("id"));
							t.setActivity_id(t1.getInt("activity_id"));
							t.setUser_id(t1.getInt("user_id"));
							t.setName(t1.getString("name"));
							t.setDescription(t1.getString("description"));
							t.setDuration(t1.getInt("duration"));
							t.setDeadline(t1.getString("deadline"));
							t.setStatus(t1.getInt("status"));
							t.setReal_duration(t1.getInt("real_duration"));
							t.setCreated_at(t1.getString("created_at"));
							t.setUpdated_at(t1.getString("updated_at"));
							a.getTaskovi().add(t);
						}
						else{break;}
					}
					p.getAktivnosti().add(a);
				}
				projects.getProjects().add(p);
		    }
		}
		catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "Error: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
		
	}
	
	/**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
        case R.id.action_settings:
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
 
    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
    private Projects kreirajPomocniObjekat()
    {
    	Projects projekti = new Projects();
    	Project projekat = new Project();
    	Aktivnost akt1 = new Aktivnost();
    	Task task1 = new Task();

    	task1.setTask_id(1);
    	task1.setName("NerminKralj");
    	task1.setActivity_id(1);
    	task1.setCreated_at("aaa");
    	task1.setDeadline("aaa");
    	task1.setDescription("ahcaksjcb");
    	task1.setDuration(5);
    	task1.setStatus((float)5.5);
    	task1.setUpdated_at("aaa");
    	task1.setUser_id(1);
    	
    	// dodavanje taska u aktivnost
    	akt1.getTaskovi().add(0,task1);
    	akt1.setActivity_id(1);
    	akt1.setCreated_at("aaa");
    	akt1.setDescription("akcjbasjc");
    	akt1.setDuration(5);
    	akt1.setFinished(false);
    	akt1.setName("nesto");
    	akt1.setProject_id(1);
    	akt1.setUpdated_at("aaa");
    	
    	projekat.getAktivnosti().add(0,akt1);
    	projekat.setBudget(5);
    	projekat.setCreated_at("aaa");
    	projekat.setDuration(55);
    	projekat.setEnd_date("aaa");
    	projekat.setFinished(false);
    	projekat.setLong_description("ascascas");
    	projekat.setMember_count(5);
    	projekat.setName("Da prodje");
    	projekat.setProject_id(1);
    	projekat.setShort_description("prodje");
    	projekat.setStart_date("aaa");
    	projekat.setUpdated_at("aaa");
    	
    	projekti.getProjects().add(0,projekat);
    	
    	return projekti;
    }
    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
        case 0:
        	makeProjectsRequest();
        	//fragment = new HomeFragment(kreirajPomocniObjekat());
        	fragment = new HomeFragment(projects);
        	break;
        /*case 1:
            fragment = new FindPeopleFragment();
            break;
        case 2:
            fragment = new PhotosFragment();
            break;
        case 3:
            fragment = new CommunityFragment();
            break;
        case 4:
            fragment = new PagesFragment();
            break;
        case 5:
            fragment = new WhatsHotFragment();
            break;*/
 
        default:
            break;
        }
 
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
 
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
 
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
 
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
 
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
 
}
