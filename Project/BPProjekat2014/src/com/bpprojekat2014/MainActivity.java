package com.bpprojekat2014;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bpprojekat2014.RegisterActivity;
import com.bpprojekat2014.classes.AppController;
import com.bpprojekat2014.classes.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity implements OnClickListener {

	/*
	@Override
	public void onClick(View v) {
			
	}*/
	
	User user;
	static EditText username;
	static EditText password;
	private String urlJSON="https://projectmng.herokuapp.com/login.json";
	private Button btnMakeJSONRequest;
	private ProgressDialog pDialog;

 
 // Tag used to cancel the request
    String tag_json_obj = "json_obj_req";
    private String TAG="TAG_JSON";
    private String key;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
	    btnMakeJSONRequest = (Button) findViewById(R.id.buttonLogin);
		btnMakeJSONRequest.setOnClickListener(this);
		username = (EditText) this.findViewById(R.id.userNameLogin);
		password = (EditText) this.findViewById(R.id.passwordLogin);
		 pDialog = new ProgressDialog(this);
	        pDialog.setMessage("Please wait...");
	        pDialog.setCancelable(false);
	       
	 
	} 
	 
		 @Override
		    public void onClick(View v) {
		       makeJsonRequest();
		    }
	       
		     
	    public void makeJsonRequest() {
	    	
	    	  StringRequest jsonObjReq = new StringRequest(Method.POST,
	                  urlJSON,
	                  new Response.Listener<String>() {
	   
	                      @Override
	                      public void onResponse(String response) {
	                          Log.d(TAG, response.toString());
	                          try {
								final JSONObject obj = new JSONObject(response);
								String priv=obj.getString("key");
		                          pDialog.hide();
		                          SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
		                           Editor editor = pref.edit();
		                          editor.putString("username",username.getText().toString() );
		                          editor.putString("key", priv);
		                          editor.commit();
		                          Intent intent = new Intent(MainActivity.this, MainPage.class);
		                          startActivity(intent);
		                          finish();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	                          
	                          
	                        

	                          
	                      }
	                  }, new Response.ErrorListener() {
	   
	                      @Override
	                      public void onErrorResponse(VolleyError error) {
	                          VolleyLog.d(TAG, "Error: " + error.getMessage());
	                          pDialog.hide();
	                          Toast.makeText(getApplicationContext(), "Invalid username or password!", Toast.LENGTH_LONG).show();
	                          
	                      }
	                  }) {
	   
	              @Override
	              protected Map<String, String> getParams() {
	                  Map<String, String> params = new HashMap<String, String>();
	                  params.put("username", username.getText().toString());
	                  params.put("password", password.getText().toString());
	   
	                  return params;
	              }
	   
	          };
	   
	  // Adding request to request queue
	  AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
	    	
	    }
	  
	    public void showRegistrationView(View v) {
		    Intent intent;
		    intent = new Intent(this, RegisterActivity.class);
		    startActivity(intent);
		}

	    public void showHomeView(View v) {
		    Intent intent;
		    intent = new Intent(this, MainPage.class);
		    startActivity(intent);
		}

	// --------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
