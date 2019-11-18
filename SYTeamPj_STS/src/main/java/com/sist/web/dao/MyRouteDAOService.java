package com.sist.web.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.sist.web.vo.MyRoute;
import com.sist.web.vo.Waypoints;
import com.sist.web.vo.Bookmark;


public class MyRouteDAOService implements MyRouteDAO{

	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<MyRoute> getMyRoute(String mid) {
		MyRouteDAO mrdao = sqlSession.getMapper(MyRouteDAO.class);
		return mrdao.getMyRoute(mid);
	}

	@Override
	public int setMyRoute(MyRoute mr) {
		MyRouteDAO mrdao = sqlSession.getMapper(MyRouteDAO.class);
		return mrdao.setMyRoute(mr);
	}

	@Override
	public int editMyRoute(String rSeq, MyRoute mr) {
		MyRouteDAO mrdao = sqlSession.getMapper(MyRouteDAO.class);
		return mrdao.editMyRoute(rSeq, mr);
	}
	@Override
	public int delMyRoute(String rSeq) {
		MyRouteDAO mrdao = sqlSession.getMapper(MyRouteDAO.class);
		return mrdao.delMyRoute(rSeq);
	}

	@Override
	public List<MyRoute> getMyRoutes(int pg, String field, String query) {
		MyRouteDAO mrdao = sqlSession.getMapper(MyRouteDAO.class);
		return mrdao.getMyRoutes(pg, field, query);
	}

	@Override
	public int mrSeqCount(String field, String query) {
		MyRouteDAO mrdao = sqlSession.getMapper(MyRouteDAO.class);
		return mrdao.mrSeqCount(field, query);
	}

	@Override
	public int hitUp(String rSeq) {
		MyRouteDAO mrdao = sqlSession.getMapper(MyRouteDAO.class);
		return mrdao.hitUp(rSeq);
	}

	@Override	
	public List<Waypoints> getWaypoints(String rSeq) {
		MyRouteDAO mrdao = sqlSession.getMapper(MyRouteDAO.class);
		return mrdao.getWaypoints(rSeq);
	}

	@Override
	public int setWaypoints(Waypoints wp) {
		MyRouteDAO mrdao = sqlSession.getMapper(MyRouteDAO.class);
		return mrdao.setWaypoints(wp);
	}

	@Override
	public int delWaypoints(String rSeq) {
		MyRouteDAO mrdao = sqlSession.getMapper(MyRouteDAO.class);
		return mrdao.delWaypoints(rSeq);
	}

	@Override
	public MyRoute myRouteDetail(String rSeq) {
		MyRouteDAO mrdao = sqlSession.getMapper(MyRouteDAO.class);
		return mrdao.myRouteDetail(rSeq);
	}

	@Override
	public String getRseq() {
		MyRouteDAO mrdao = sqlSession.getMapper(MyRouteDAO.class);
		return mrdao.getRseq();
	}

	@Override
	public int setBookmark(String rSeq,String mid) {
		MyRouteDAO mrdao = sqlSession.getMapper(MyRouteDAO.class);
		return mrdao.setBookmark(rSeq,mid);
		
	}

	@Override
	public int delBookmark(String bSeq) {
		MyRouteDAO mrdao = sqlSession.getMapper(MyRouteDAO.class);
		return mrdao.delBookmark(bSeq);
	}

	@Override
	public Bookmark getBookmark(String bSeq) {
		MyRouteDAO mrdao = sqlSession.getMapper(MyRouteDAO.class);
		return mrdao.getBookmark(bSeq);
	}

	@Override
	public List<Bookmark> getBookmarks(String mid) {
		MyRouteDAO mrdao = sqlSession.getMapper(MyRouteDAO.class);
		return mrdao.getBookmarks(mid);
	}

	@Override
	public String getBseq(String mid,String rSeq) {
		MyRouteDAO mrdao = sqlSession.getMapper(MyRouteDAO.class);
		return mrdao.getBseq(mid,rSeq);
	}
}
