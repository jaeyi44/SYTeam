package com.sist.web.vo;

public class InfoBoard {
	
	private String iseq;
	private String title;
	private String content;
	private String hit;
	private String vote;
	private String regdate;
	private String filesrc;
	private String kind;
	private String commentcnt;

	public String getIseq() {
		return iseq;
	}
	public void setIseq(String iseq) {
		this.iseq = iseq;
	}
	public String getCommentcnt() {
		return commentcnt;
	}
	public void setCommentcnt(String commentcnt) {
		this.commentcnt = commentcnt;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getFilesrc() {
		return filesrc;
	}
	public void setFilesrc(String filesrc) {
		this.filesrc = filesrc;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getHit() {
		return hit;
	}
	public void setHit(String hit) {
		this.hit = hit;
	}
	public String getVote() {
		return vote;
	}
	public void setVote(String vote) {
		this.vote = vote;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	@Override
	public String toString() {
		return "InfoBoard [i_seq=" + iseq + ", title=" + title + ", content=" + content + ", hit=" + hit + ", vote=" + vote
				+ ", regdate=" + regdate + "]";
	}


}
