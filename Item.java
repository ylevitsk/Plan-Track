package com.example.plantrack;

public class Item {
	private String title;
	private String category;
	private double percent;
	private int id;
	
	public Item() {
		this.title = "";
		this.category = "";
		this.percent = 0;
		this.id = 0;
	}
	
	public Item(String title, String cat, double grade) {
		this.title = title;
		this.category = cat;
		this.percent = grade;
		this.id = 0;
	}
	
	public Item(String title, String cat, double grade, int id) {
		this.title = title;
		this.category = cat;
		this.percent = grade;
		this.id = id;
	}
	
	public void setItemId(int id) {
		this.id = id;
	}
	
	public int getItemId() {
		return this.id;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setCategory(String cat){
		this.category = cat;
	}
	
	public void setPercent(double grade){
		this.percent = grade;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getCategory(){
		return this.category;
	}
	
	public double getPercent(){
		return this.percent;
	}
	
}
