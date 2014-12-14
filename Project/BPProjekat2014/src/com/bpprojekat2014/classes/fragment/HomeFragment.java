package com.bpprojekat2014.classes.fragment;

import com.bpprojekat2014.R;
import com.bpprojekat2014.classes.Project;
import com.bpprojekat2014.classes.Projects;
import com.bpprojekat2014.classes.Task;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class HomeFragment extends Fragment{
	public static Projects projects;
	
	public HomeFragment(Projects proj){
		projects = proj;
	}
	public HomeFragment(){
	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
			// Nek ispise bezveze
			String pom = "ackbascjbac " + projects.getProjects().get(0).getAktivnosti().get(0).getName();
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
