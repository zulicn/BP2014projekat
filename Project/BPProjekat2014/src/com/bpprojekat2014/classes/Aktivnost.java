package com.bpprojekat2014.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Aktivnost{ // Mora se zvati ovako jer "Activity" je nesto drugo kod anrdoida
	
	private int activity_id;
	private int project_id;
	private String name;
	private String description;
	private int duration = 0;
	boolean finished = false;
	private String created_at;
	private String updated_at;

	private ArrayList<Task> taskovi;
	
	public Aktivnost()
	{
		this.taskovi = new ArrayList<Task>();
	}
	public int countTasks() {
		int count= taskovi.size();
		return count;
		
	}
	@Override
	public String toString(){
		return description;
	}

	public int getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(int activity_id) {
		this.activity_id = activity_id;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public ArrayList<Task> getTaskovi() {
		return taskovi;
	}

	public void setTaskovi(ArrayList<Task> taskovi) {
		this.taskovi = taskovi;
	}

}
