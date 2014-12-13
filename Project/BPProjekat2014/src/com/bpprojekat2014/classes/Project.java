package com.bpprojekat2014.classes;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;

public class Project{
	
	private int project_id;
	private String name;
	private String short_description;
	private String long_description;
	private Date start_date;
	private Date end_date;
	private int duration = 0;
	private int member_count = 0;
	private float budget  = 0;
	boolean finished = false;
	private Date created_at;
	private Date updated_at;
	private ArrayList<Aktivnost> aktivnosti;
	
	public Project()
	{
		this.aktivnosti= new ArrayList<Aktivnost>();
	}
	@Override
	public String toString(){
		return name+" "+short_description;
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

	public String getShort_description() {
		return short_description;
	}

	public void setShort_description(String short_description) {
		this.short_description = short_description;
	}

	public String getLong_description() {
		return long_description;
	}

	public void setLong_description(String long_description) {
		this.long_description = long_description;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getMember_count() {
		return member_count;
	}

	public void setMember_count(int member_count) {
		this.member_count = member_count;
	}

	public float getBudget() {
		return budget;
	}

	public void setBudget(float budget) {
		this.budget = budget;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	public ArrayList<Aktivnost> getAktivnosti() {
		return aktivnosti;
	}
	public void setAktivnosti(ArrayList<Aktivnost> aktivnosti) {
		this.aktivnosti = aktivnosti;
	}
	

}
