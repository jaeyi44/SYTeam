package com.sist.web.dao;

import java.util.ArrayList;

import com.sist.web.vo.Member;

public interface MemberDAO {

	public ArrayList<Member> viewMembers();
	
	public Member viewMember(String mid);
	
	public Member viewMemberbyNick(String nickname);
	
	public int addMember(Member m);
	
	public int editMember(Member m);
	
	public int delMember(String mid);
}
