package com.bpprojekat2014;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bpprojekat2014.classes.AppController;
import com.bpprojekat2014.classes.FragmentPageAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;  
import android.app.ActionBar.Tab;  
import android.app.FragmentTransaction;  
import android.os.Bundle;  
import android.support.v4.app.FragmentActivity;  
import android.support.v4.view.ViewPager;  

public class HomeActivity extends FragmentActivity implements ActionBar.TabListener {
	
	private String TAG="JSON_TAG_PROJECTS";
	private String username;
	private String key;
	private String  jsonResponse;
	private TextView txtResponse;
	
	ActionBar actionbar;  
    ViewPager viewpager;  
    FragmentPageAdapter ft; 
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		makeProjectsRequest();
		 txtResponse = (TextView) findViewById(R.id.txtResponse);
	       
		 viewpager = (ViewPager) findViewById(R.id.pager);  
         ft = new FragmentPageAdapter(getSupportFragmentManager());  
         actionbar = getActionBar();  
         viewpager.setAdapter(ft);  
         actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);  
         actionbar.addTab(actionbar.newTab().setText("Java").setTabListener(this));  
         actionbar.addTab(actionbar.newTab().setText("Php").setTabListener(this));  
         viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {  
              @Override  
              public void onPageSelected(int arg0) {  
              actionbar.setSelectedNavigationItem(arg0);  
              }  
              @Override  
              public void onPageScrolled(int arg0, float arg1, int arg2) {  
                   // TODO Auto-generated method stub  
              }  
              @Override  
              public void onPageScrollStateChanged(int arg0) {  
                   // TODO Auto-generated method stub  
              }  
         });  
	} 
	
	public void makeProjectsRequest(){
	
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
	    username=pref.getString("username",null);
		key=pref.getString("key",null);
		String url = String.format("https://projectmng.herokuapp.com/projects.json?username=%1$s&key=%2$s",username,key);
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
		                             jsonResponse = "";
		                             for (int i = 0; i < response.length(); i++) {
		      
		                                 JSONObject project = (JSONObject) response.get(i);
		                                 String name = project.getString("name");
		                                 String description = project.getString("short_description");
		                                 jsonResponse += "Name: " + name + "\n\n";
		                                 jsonResponse += "Project description: " + description + "\n\n";
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
		      
		                             txtResponse.setText(jsonResponse);
		      
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

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewpager.setCurrentItem(tab.getPosition()); 
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	

}
