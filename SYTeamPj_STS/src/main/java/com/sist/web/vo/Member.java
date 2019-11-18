package com.sist.web.vo;

public class Member {
	private String mid;
	private String pwd;
	private String nickname;
	private String myLevel;
	private String gender;
	private String contact;
	private String dob;
	private String height;
	private String weight;
	private String purpose;
	private String profilePic;
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getMyLevel() {
		return myLevel;
	}
	public void setMyLevel(String myLevel) {
		this.myLevel = myLevel;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	
	public Member() {
		this(null,null,null,null,null,null,null,null,null,null,null);
	}
	
	public Member(String mid, String pwd, String nickname, String myLevel, String gender, String contact, String dob, String height, String weight, String purpose, String profilePic) {
		this.mid=mid;
		this.pwd=pwd;
		this.nickname=nickname;
		this.myLevel=myLevel;
		this.gender=gender;
		this.contact=contact;
		this.dob=dob;
		this.height=height;
		this.weight=weight;
		this.purpose=purpose;
		this.profilePic=profilePic;
	
	}
	
	
	@Override
	public String toString() {
		return "Member [mid=" + mid + ", pwd=" + pwd + ", nickname=" + nickname + ", myLevel=" + myLevel + ", gender="
				+ gender + ", contact=" + contact + ", dob=" + dob + ", height=" + height + ", weight=" + weight
				+ ", purpose=" + purpose + ", profilePic=" + profilePic + "]";
	}
	
	
	
}