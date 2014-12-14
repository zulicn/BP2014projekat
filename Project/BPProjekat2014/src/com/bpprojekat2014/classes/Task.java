package com.bpprojekat2014.classes;

import java.io.Serializable;


public class Task{
	
	private int task_id;
	private int activity_id;
	private int user_id;
	private String name;
	private String description;
	private int duration = 0;
	private String deadline;
	private float status;
	private float real_duration;
	private String created_at;
	private String updated_at;

	// TBD treba dodati!
	//boolean finished = false;
	
	public Task(){}
	@Override
	public String toString(){
		return description;
	}

	public int getTask_id() {
		return task_id;
	}

	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}

	public int getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(int activity_id) {
		this.activity_id = activity_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
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
	
	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	
	public float getStatus() {
		return status;
	}

	public void setStatus(float status) {
		this.status = status;
	}

	public float getReal_duration() {
		return real_duration;
	}

	public void setReal_duration(float real_duration) {
		this.real_duration = real_duration;
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
	
	/*
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
	}
	
	 public static final Parcelable.Creator<Task> CREATOR
	     = new Parcelable.Creator<Task>() {
	 
		 public Task createFromParcel(Parcel in) {
	     return new Task(in);
	 }
	
	 public Task[] newArray(int size) {
	     return new Task[size];
	 }
	};
	// Parcelling part
		public Task(Parcel in){
			
			task_id = in.readInt();
			activity_id = in.readInt();
			in.read
		}
	*/
}
