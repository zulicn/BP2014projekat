package com.bpprojekat2014.classes.fragment;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.bpprojekat2014.MainActivity;
import com.bpprojekat2014.MainPage;
import com.bpprojekat2014.R;
import com.bpprojekat2014.classes.AppController;
import com.bpprojekat2014.classes.Project;
import com.bpprojekat2014.classes.Projects;
import com.bpprojekat2014.classes.User;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
public class EditViewProjectFragment extends Fragment{
	public static Projects projects;
	public static User user;
	private static int indeksProjekta;
	private static View rootView;
	private EditText name;
	private EditText shortDescription;
	private EditText longDescription;
	private EditText startDate;
	private EditText endDate;
	private EditText duration;
	private EditText memberCount;
	private EditText budget;
	private CheckBox finishedProj;
	private Button btnUpdateProject;
	
	
	public EditViewProjectFragment(){}
	
	public EditViewProjectFragment(Projects proj, User user, int indeksProjekta){
		projects = proj;
		this.user = user;
		this.indeksProjekta = indeksProjekta;
	}
	// TBD Ako se radilo editovanje, promijeniti ovu metodu!
	
	private void UpdateProject(String name, String shDecs, String longDesc, String stDate, String endDate,
			int duration, int memCount, double budget, boolean finished)
	{
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
		    try {
		    	JSONObject project = new JSONObject();		
		    	project.put("name", name);
		    	project.put("short_description", shDecs);
		    	project.put("long_description", longDesc);
		    	project.put("start_date", stDate);
		    	project.put("end_date", endDate);
		    	project.put("duration", duration);
		    	project.put("member_count", memCount);
		    	project.put("budget", budget);
		    	project.put("finished",finished );
		    	JSONObject jsonobj = new JSONObject();
		    	jsonobj.put("project", project);
		    	jsonobj.put("username", user.getUsername());
		    	jsonobj.put("key", user.getKey());
		    	
		    	
		    	HttpPut httpputreq = new HttpPut("http://projectmng.herokuapp.com/projects/"+
		    	projects.getProjects().get(indeksProjekta).getProject_id() +".json");
		        StringEntity params =new StringEntity(jsonobj.toString());
		        httpputreq.setEntity(params);
		        params.setContentType("application/json;charset=UTF-8");
		        params.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
		        
		        HttpResponse response = httpClient.execute(httpputreq);
		        
		        Toast toast = Toast.makeText(this.getActivity(), "Projekat je uspjesno izmijenjen!", Toast.LENGTH_SHORT);
		    	toast.show();
		    	
		    }catch (Exception ex) {
		    	Toast toast = Toast.makeText(this.getActivity(), "Doslo je do greske, projekat nije izmijenjen!", Toast.LENGTH_SHORT);
		    	toast.show();
		        ex.printStackTrace();
		    } finally {
		        httpClient.getConnectionManager().shutdown();
		    }
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        rootView = inflater.inflate(R.layout.fragment_edit_view_project, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
        Project projekat = projects.getProjects().get(indeksProjekta);
        
        name=(EditText) rootView.findViewById(R.id.name);
        shortDescription=(EditText) rootView.findViewById(R.id.shortDescription);
        longDescription=(EditText) rootView.findViewById(R.id.longDescription);
        startDate=(EditText) rootView.findViewById(R.id.startDate);
        endDate=(EditText) rootView.findViewById(R.id.endDate);
        duration=(EditText) rootView.findViewById(R.id.duration);
        memberCount=(EditText) rootView.findViewById(R.id.memberCount);
        budget=(EditText) rootView.findViewById(R.id.budget);
        finishedProj = (CheckBox) rootView.findViewById(R.id.finishedProject);
        
        name.setText(projekat.getName());
        shortDescription.setText(projekat.getShort_description());
        longDescription.setText(projekat.getLong_description());
        startDate.setText(projekat.getStart_date());
        endDate.setText(projekat.getEnd_date());
        duration.setText(String.valueOf(projekat.getDuration()));
        memberCount.setText(String.valueOf(projekat.getMember_count()));
        budget.setText(String.valueOf(projekat.getBudget()));
        
        if(projekat.isFinished()){
        	finishedProj.setChecked(true);
        }
        else{finishedProj.setChecked(false);}
        
        btnUpdateProject= (Button) rootView.findViewById(R.id.buttonDone);
        btnUpdateProject.setOnClickListener(new OnClickListener()
        
	    {
		   @Override
	             public void onClick(View v)
	             {
			   		// TBD dodati poziv metode za editovanje podataka!
			   		UpdateProject(name.getText().toString(),shortDescription.getText().toString(),longDescription.getText().toString(),
			   				startDate.getText().toString(),endDate.getText().toString(),Integer.parseInt(duration.getText().toString()),
			   				Integer.parseInt(memberCount.getText().toString()),Double.parseDouble(budget.getText().toString()),finishedProj.isChecked());
	             	
	             } 
	    }); 
            
        return rootView;
    }
   
}
