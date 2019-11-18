package com.sist.web.vo;

public class Event {
	private String seq;
	private String title;
	private String edate;
	private String url;
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Event [seq=" + seq + ", title=" + title + ", edate=" + edate + ", url=" + url + "]";
	}
	
	
	
	
	
}
