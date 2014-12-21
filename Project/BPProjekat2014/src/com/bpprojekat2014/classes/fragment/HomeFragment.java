package com.bpprojekat2014.classes.fragment;

import com.bpprojekat2014.R;
import com.bpprojekat2014.classes.Project;
import com.bpprojekat2014.classes.Projects;
import com.bpprojekat2014.classes.Task;
import com.bpprojekat2014.classes.User;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
	public static User user;
	public HomeFragment(Projects proj, User usr){
		projects = proj;
		user = usr;
	}
	public HomeFragment(){
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
			String pom = "ackbascjbac ";
			TextView sessionTitle = (TextView) rootView.findViewById(R.id.session1);
		    sessionTitle.setText(pom);
		   
          
        return rootView;
    }
	public Projects getProjects() {
		return projects;
	}
	public void setProjects(Projects projects) {
		this.projects = projects;
	}
}
