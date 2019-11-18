package com.sist.web.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import com.sist.web.vo.Member;

public class MemberDAOService implements MemberDAO{
	
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public ArrayList<Member> viewMembers() {
		MemberDAO mdao = sqlSession.getMapper(MemberDAO.class);
		return mdao.viewMembers();
	}

	@Override
	public Member viewMember(String mid) {
		MemberDAO mdao = sqlSession.getMapper(MemberDAO.class);
		return mdao.viewMember(mid);
	}

	@Override
	public Member viewMemberbyNick(String nickname) {
		MemberDAO mdao = sqlSession.getMapper(MemberDAO.class);
		return mdao.viewMemberbyNick(nickname);
	}

	@Override
	public int addMember(Member m) {
		MemberDAO mdao = sqlSession.getMapper(MemberDAO.class);
		return mdao.addMember(m);
	}

	@Override
	public int editMember(Member m) {
		MemberDAO mdao = sqlSession.getMapper(MemberDAO.class);
		return mdao.editMember(m);
	}

	@Override
	public int delMember(String mid) {
		MemberDAO mdao = sqlSession.getMapper(MemberDAO.class);
		return mdao.delMember(mid);
	}

}
