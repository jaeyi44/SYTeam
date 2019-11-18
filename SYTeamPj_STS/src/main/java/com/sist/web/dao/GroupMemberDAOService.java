package com.sist.web.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import com.sist.web.vo.GroupMember;

public class GroupMemberDAOService implements GroupMemberDAO{

	private SqlSession sqlsession;

	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

	@Override
	public int addGroupMem(String gid, String mid, String gmseq) {
		GroupMemberDAO gmdao = sqlsession.getMapper(GroupMemberDAO.class);
		return gmdao.addGroupMem(gid, mid, gmseq);
	}

	@Override
	public ArrayList<String> viewMygroup(String mid) {
		GroupMemberDAO gmdao = sqlsession.getMapper(GroupMemberDAO.class);
		return gmdao.viewMygroup(mid);
	}

	@Override
	public ArrayList<String> searchMem(String gid) {
		GroupMemberDAO gmdao = sqlsession.getMapper(GroupMemberDAO.class);
		return gmdao.searchMem(gid);
	}

	@Override
	public int countMem(String gid) {
		GroupMemberDAO gmdao = sqlsession.getMapper(GroupMemberDAO.class);
		return gmdao.countMem(gid);
	}

	@Override
	public ArrayList<GroupMember> getGroupMems() {
		GroupMemberDAO gmdao = sqlsession.getMapper(GroupMemberDAO.class);
		return gmdao.getGroupMems();
	}
	
	
	
	
	
}
