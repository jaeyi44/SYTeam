package com.sist.web.vo;

public class Post {
	String pseq;
	String gid;
	String content;
	String regdate;
	String filesrc;
	String nickname;
	String likeit;
	String profilePic;
	String comNum;
	
	
	public String getComNum() {
		return comNum;
	}

	public void setComNum(String comNum) {
		this.comNum = comNum;
	}

	public Post(String pseq, String gid, String content, String regdate, String filesrc, String nickname, String likeit){
		this.pseq = pseq;
		this.gid = gid;
		this.content = content;
		this.regdate = regdate;
		this.filesrc = filesrc;
		this.nickname = nickname;
		this.likeit = likeit;

	}
	
	public Post(){
		this.pseq = null;
		this.gid = null;
		this.content = null;
		this.regdate = null;
		this.filesrc = null;
		this.nickname = null;
		this.likeit = null;
	}
	
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getLikeit() {
		return likeit;
	}
	public void setLikeit(String likeit) {
		this.likeit = likeit;
	}
	public String getPseq() {
		return pseq;
	}
	public void setPseq(String pseq) {
		this.pseq = pseq;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getFilesrc() {
		return filesrc;
	}
	public void setFilesrc(String filesrc) {
		this.filesrc = filesrc;
	}
	
	

}
