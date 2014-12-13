package com.bpprojekat2014.classes;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;

public class Projects{
	
	private ArrayList<Project> projects;
	
	public Projects()
	{
		this.projects= new ArrayList<Project>();
	}

	public ArrayList<Project> getProjects() {
		return projects;
	}

	public void setProjects(ArrayList<Project> projects) {
		this.projects = projects;
	}
	

}
