package com.sist.web.dao;

import java.util.ArrayList;

import com.sist.web.vo.GroupMember;

public interface GroupMemberDAO {

	//그룹 인원수 조회
	public int countMem(String gid);
	
	//그룹멤버 아이디 조회
	public ArrayList<String> searchMem(String gid);
	
	//그룹참여 (아이디삽입)
	public int addGroupMem(String gid, String mid, String gmseq);
	
	//내가 가입한 그룹의 gid 조회
	public ArrayList<String> viewMygroup(String mid);

	//GID,MEMID,GMSEQ 전체조회
	public ArrayList<GroupMember> getGroupMems();
}
