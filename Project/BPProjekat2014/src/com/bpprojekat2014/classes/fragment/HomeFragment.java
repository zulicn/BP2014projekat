package com.bpprojekat2014.classes.fragment;

import com.bpprojekat2014.R;
import com.bpprojekat2014.classes.Project;
import com.bpprojekat2014.classes.Projects;
import com.bpprojekat2014.classes.Task;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
public class HomeFragment extends Fragment{
	public static Projects projects;
	
	public HomeFragment(Projects proj){
		projects = proj;
	}
	public HomeFragment(){
	}
	
	private void kreirajProjekat()
	{
			DefaultHttpClient httpClient = new DefaultHttpClient();
		    try {
		    	JSONObject project = new JSONObject();		
		    	project.put("name", "testaa");
		    	project.put("short_description", "test");
		    	project.put("long_description", "2014-10-18");
		    	project.put("start_date", "2014-10-18");
		    	project.put("end_date", "2014-10-18");
		    	project.put("duration", 123);
		    	project.put("member_count", 5);
		    	project.put("budget", 10.1);
		    	
		    	JSONObject jsonobj = new JSONObject();
		    	jsonobj.put("project", project);
		    	jsonobj.put("username", "ssuljic");
		    	jsonobj.put("key", "4073d3af798d33d05b5d3e356365e69a38021d322e57032882fee7be15ee6878");
		    	HttpPost httppostreq = new HttpPost("http://projectmng.herokuapp.com/projects.json");
		        StringEntity params =new StringEntity(jsonobj.toString());
		        httppostreq.setEntity(params);
		        params.setContentType("application/json;charset=UTF-8");
		        params.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
		        
		        HttpResponse response = httpClient.execute(httppostreq);

		        Toast toast = Toast.makeText(this.getActivity(), "Valja kreiranje projekta!", Toast.LENGTH_SHORT);
		    	toast.show();
		    	
		    }catch (Exception ex) {
		    	Toast toast = Toast.makeText(this.getActivity(), "Ne valja kreiranje projekta!", Toast.LENGTH_SHORT);
		    	toast.show();
		        ex.printStackTrace();
		    } finally {
		        httpClient.getConnectionManager().shutdown();
		    }
	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
			// Nek ispise bezveze
			String pom = "ackbascjbac " + projects.getProjects().get(0).getAktivnosti().get(0).getName();
			TextView sessionTitle = (TextView) rootView.findViewById(R.id.session1);
		    sessionTitle.setText(pom);
		    
		    
		    // 
		    //kreirajProjekat();
		    
          
        return rootView;
    }
	public Projects getProjects() {
		return projects;
	}
	public void setProjects(Projects projects) {
		this.projects = projects;
	}
}
