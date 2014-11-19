package com.bpprojekat2014;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.bpprojekat2014.classes.AppController;
import com.bpprojekat2014.classes.adapter.NavDrawerListAdapter;
import android.app.Fragment;
import com.bpprojekat2014.classes.fragment.HomeFragment;
import com.bpprojekat2014.classes.model.NavDrawerItem;
import com.bpprojekat2014.classes.Project;

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
	
	private String TAG="JSON_TAG_PROJECTS";
	private String TAG2="JSON_TAG_GPROJECTS";
	private String username;
	private String key;
	private String  jsonResponse;
	private TextView txtResponse;
	
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
		pDialog.setMessage("Loading...");
		pDialog.show();    
		
		         
		JsonArrayRequest req = new JsonArrayRequest(url,
		                new Response.Listener<JSONArray>() {
		                    @Override
		                    public void onResponse(JSONArray response) {
		                    	
		                    	 try {
		                             // Parsing json array response
		                             // loop through each json object
		                    		// ovdje sam uspjela da projekte mapriam preko GSona u nas model Project (klasu)
		                    		 //stavila sma samo naziv i desription al mogu i ostali podaci, ali
		                    		 // ne znam jos kako sad mapirat aktivnosti, jer su to sad liste objekata unutar objekta
		                             jsonResponse = "";
		                             String jsonResponse2=" ";
		                             jsonResponse2=response.toString();
		                             GsonBuilder gsonBuilder = new GsonBuilder();
                                     Gson gson = gsonBuilder.create();
                                     List<Project> projects = new ArrayList<Project>();
                                     projects = Arrays.asList(gson.fromJson(jsonResponse2, Project[].class));// ovdje mapira u klasu Projekt, sad ce ova lsiat sadrzavat projekte
                                     for(int j=0;j<projects.size();j++){ Log.d(TAG2, projects.get(j).toString());}// ovo samo ispise naziv i opis u logCat da vidim radil
		                             for (int i = 0; i < response.length(); i++) {
		      
		                                 JSONObject project = (JSONObject) response.get(i);
		                                 String name = project.getString("name");
		                                 String description = project.getString("short_description");
		                                 jsonResponse += "Name: " + name + "\n\n";
		                                 jsonResponse += "Project description: " + description + "\n\n";// ovo sam ostavila kao prije, ovaj jsonresponse smao ono redom ispis ekao string
		                                 jsonResponse+="Activities:\n\n";
		                                 JSONArray activities = project.getJSONArray("activities");
		                                 for (int j = 0; j < activities.length(); j++) {
		                                	 JSONObject activity = (JSONObject) activities.get(j);
			                                 String nameActivity = activity.getString("name");
			                                 String descriptionActivity = activity.getString("description");
			                                 jsonResponse += "Name: " + nameActivity + "\n\n";
			                                 jsonResponse += "Description: " + descriptionActivity + "\n\n";
			                                 jsonResponse+="Tasks:\n\n";
			                                 JSONArray tasks = activity.getJSONArray("tasks");
			                                 for (int k = 0; k < tasks.length(); k++) {
			                                	 JSONObject task = (JSONObject) tasks.get(k);
				                                 String nameTask = task.getString("name");
				                                 String descriptionTask = task.getString("description");
				                                 jsonResponse += "Name: " + nameTask + "\n\n";
				                                 jsonResponse += "Description: " + descriptionTask + "\n\n";
			                                
			                                 }
		                                
		                                
		      
		                             }}
		                             Log.d(TAG, jsonResponse);
		                             //txtResponse.setText(jsonResponse);
		      
		                         } catch (JSONException e) {
		                             e.printStackTrace();
		                             Toast.makeText(getApplicationContext(),
		                                     "Error: " + e.getMessage(),
		                                     Toast.LENGTH_LONG).show();
		                         }
		                        Log.d(TAG, response.toString());        
		                        pDialog.hide();
		                       
		                    }
		                }, new Response.ErrorListener() {
		                    @Override
		                    public void onErrorResponse(VolleyError error) {
		                       Log.d(TAG, "Error: " + error.getMessage());
		                        pDialog.hide();
		                    }
		                });
		 
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(req, tag_json_arry);
		
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
 
    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
        case 0:
        	makeProjectsRequest();
            Bundle bundle = new Bundle();
            bundle.putString("myString", jsonResponse);
            fragment = new HomeFragment();
            fragment.setArguments(bundle);
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
