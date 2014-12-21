package com.bpprojekat2014.classes.fragment;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.bpprojekat2014.R;
import com.bpprojekat2014.classes.Project;
import com.bpprojekat2014.classes.Projects;
import com.bpprojekat2014.classes.User;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class CreateNewProjectFragment extends Fragment{
	public static Projects projects;
	public static User user;
	private static View rootView;
	private EditText name;
	private EditText shortDescription;
	private EditText longDescription;
	private EditText startDate;
	private EditText endDate;
	private EditText duration;
	private EditText memberCount;
	private EditText budget;
	private Button btnCreateProject;
	
	
	public CreateNewProjectFragment(){}
	
	public CreateNewProjectFragment(Projects proj, User user){
		projects = proj;
		this.user = user;
	}
	private void kreirajProjekat(String name, String shDecs, String longDesc, String stDate, String endDate,
			int duration, int memCount, double budget)
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
		    	
		    	JSONObject jsonobj = new JSONObject();
		    	jsonobj.put("project", project);
		    	jsonobj.put("username", user.getUsername());
		    	jsonobj.put("key", user.getKey());
		    	HttpPost httppostreq = new HttpPost("http://projectmng.herokuapp.com/projects.json");
		        StringEntity params =new StringEntity(jsonobj.toString());
		        httppostreq.setEntity(params);
		        params.setContentType("application/json;charset=UTF-8");
		        params.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
		        
		        HttpResponse response = httpClient.execute(httppostreq);
		        
		        Project proj = new Project();
		        
		        Toast toast = Toast.makeText(this.getActivity(), "Projekat je uspjesno kreiran!", Toast.LENGTH_SHORT);
		    	toast.show();
		    	
		    }catch (Exception ex) {
		    	Toast toast = Toast.makeText(this.getActivity(), "Doslo je do greske, projekat nije kreiran!", Toast.LENGTH_SHORT);
		    	toast.show();
		        ex.printStackTrace();
		    } finally {
		        httpClient.getConnectionManager().shutdown();
		    }
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        rootView = inflater.inflate(R.layout.fragment_create_new_project, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
        name=(EditText) rootView.findViewById(R.id.name);
        shortDescription=(EditText) rootView.findViewById(R.id.shortDescription);
        longDescription=(EditText) rootView.findViewById(R.id.longDescription);
        startDate=(EditText) rootView.findViewById(R.id.startDate);
        endDate=(EditText) rootView.findViewById(R.id.endDate);
        duration=(EditText) rootView.findViewById(R.id.duration);
        memberCount=(EditText) rootView.findViewById(R.id.memberCount);
        budget=(EditText) rootView.findViewById(R.id.budget);
     
        btnCreateProject= (Button) rootView.findViewById(R.id.buttonDone);
        btnCreateProject.setOnClickListener(new OnClickListener()
        
	    {
		   @Override
	             public void onClick(View v)
	             {
			   		kreirajProjekat(name.getText().toString(),shortDescription.getText().toString(),longDescription.getText().toString(),
			   				startDate.getText().toString(),endDate.getText().toString(),Integer.parseInt(duration.getText().toString()),
			   				Integer.parseInt(memberCount.getText().toString()),Double.parseDouble(budget.getText().toString()));
	             } 
	    }); 
            
        return rootView;
    }
   
}
