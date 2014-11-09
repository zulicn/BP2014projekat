package com.bpprojekat2014.classes;


public class User {

	private String username = "";
	// private String password= "";
	
	public User(String u){
		username = u;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	// Za password ne trebaju ni getteri ni setteri, jedino da cuvamo neku hashiranu vrijednost
	// Dodati ako bude trebalo
}
