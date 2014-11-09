package com.bpprojekat2014.classes;


public class User {

	private String username = "";
	private String fullName = ""; // ime i prezime
	// private String password= "";
	
	public User(String username){
		this.username = username;
	}
	public User(String username, String fullName){
		this.username = username;
		this.fullName = fullName;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	// Za password ne trebaju ni getteri ni setteri, jedino da cuvamo neku hashiranu vrijednost
	// ili mozda saljemo bazi na registraciju.
	// Dodati ako bude trebalo
}
