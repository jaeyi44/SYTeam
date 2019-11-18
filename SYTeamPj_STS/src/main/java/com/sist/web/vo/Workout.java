package com.sist.web.vo;

public class Workout {
	
	private String wseq;
	private String mid;
	private String kind;
	private String hour;
	private String minute;
	private String dist;
	private String regdate;
	
	public Workout(String wseq, String mid, String kind, String hour, String minute, String dist, String regdate){
		this.wseq = wseq;
		this.mid = mid;
		this.kind = kind;
		this.hour = hour;
		this.minute = minute;
		this.dist = dist;
		this.regdate = regdate;
		
	}
	public Workout(){
		this.wseq = null;
		this.mid = null;
		this.kind = null;
		this.hour = null;
		this.minute = null;
		this.dist = null;
		this.regdate = null;
		
	}
	public String getWseq() {
		return wseq;
	}
	public void setWseq(String wseq) {
		this.wseq = wseq;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getMinute() {
		return minute;
	}
	public void setMinute(String minute) {
		this.minute = minute;
	}
	public String getDist() {
		return dist;
	}
	public void setDist(String dist) {
		this.dist = dist;
	}

	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	

}
