package com.sist.web.vo;

public class WorkoutGson {
	
	String title;
	String start;

	int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public WorkoutGson(String title, String start){
		this.title = title;
		this.start = start;		
	}
	
	public WorkoutGson(){
		this.title = null;
		this.start = null;		
	}
	

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	
	

}
