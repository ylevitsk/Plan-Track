package com.example.plantrack;

public class GpaItem {
	private String name;
	private double grade;
	private int units;
	public GpaItem(String n, double g, int u){
		this.name = n;
		this.grade = g;
		this.units= u;
	}
	public void setName(String n){
		this.name = n;
	}
	public void setUnits(int u){
		this.units = u;
	}
	public void setGrade(double g){
		this.grade = g;
	}
	public String getName(){
		return name;
	}
	public int getUnits(){
		return units;
	}
	public double getGrade(){
		return this.grade;
	}
}
