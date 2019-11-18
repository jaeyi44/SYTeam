package com.sist.web.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import com.sist.web.vo.Event;

public class EventDAOService implements EventDAO{

	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	} 
	
	@Override
	public ArrayList<Event> getDates() {
		EventDAO edao = sqlSession.getMapper(EventDAO.class);
		return edao.getDates();
	}

	@Override
	public int addEvent(Event event) {
		EventDAO edao = sqlSession.getMapper(EventDAO.class);
		return edao.addEvent(event);
	}

	@Override
	public int delEvent(String seq) {
		EventDAO edao = sqlSession.getMapper(EventDAO.class);
		return edao.delEvent(seq);
	}

	
	
	
	
	
}
