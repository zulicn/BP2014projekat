package com.bpprojekat2014.classes;

public class Document {
	
	private int id;
	private int task_id;
	private String filename;
	
	public Document(){}
	public Document(int id, int task_id,String filename){
		this.id=id;
		this.task_id=task_id;
		this.filename=filename;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getTask_id() {
		return task_id;
	}

	public void settask_id(int task_id) {
		this.task_id = task_id;
	}
	
	public String getFilename(){
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}