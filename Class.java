package com.example.plantrack;

public class Class {
	private String title;
	private int id;
	
	public Class(String title) {
		this.title = title;
		this.id = 0;
	}
	
	public Class(String t, int id){
		title = t;
		this.id = id;
	}
	
	public void setClassId(int id) {
		this.id = id;
	}
	
	public int getClassId() {
		return this.id;
	}
	
	public void setTitle(String t){
		this.title = t;
	}
	
	public String getTitle(){
		return this.title;
	}
}
