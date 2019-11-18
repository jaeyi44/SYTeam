package com.sist.web.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import com.sist.web.vo.Comments;
import com.sist.web.vo.InfoBoard;

public class InfoBoardDAOService implements InfoBoardDAO {
	
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public int infoSeqCount(String f, String q) {
		InfoBoardDAO idao = sqlSession.getMapper(InfoBoardDAO.class);
		return idao.infoSeqCount(f, q);
	}

	@Override
	public ArrayList<InfoBoard> getInfos(int ipg, String f, String q) {
		InfoBoardDAO idao = sqlSession.getMapper(InfoBoardDAO.class);
		return idao.getInfos(ipg, f, q);
	}

	@Override
	public InfoBoard getInfo(String iseq) {
		InfoBoardDAO idao = sqlSession.getMapper(InfoBoardDAO.class);
		return idao.getInfo(iseq);
	}

	@Override
	public int editInfo(String iseq, String content, String title) {
		InfoBoardDAO idao = sqlSession.getMapper(InfoBoardDAO.class);
		return idao.editInfo(iseq, content, title);
	}

	@Override
	public int delInfo(String seq) {
		InfoBoardDAO idao = sqlSession.getMapper(InfoBoardDAO.class);
		return idao.delInfo(seq);
	}

	@Override
	public int delComment(String cseq) {
		InfoBoardDAO idao = sqlSession.getMapper(InfoBoardDAO.class);
		return idao.delComment(cseq);
	}

	@Override
	public int addComment(String iseq, String writer, String content) {
		InfoBoardDAO idao = sqlSession.getMapper(InfoBoardDAO.class);
		return idao.addComment(iseq, writer, content);
	}

	@Override
	public ArrayList<Comments> getComments(String iseq) {
		InfoBoardDAO idao = sqlSession.getMapper(InfoBoardDAO.class);
		return idao.getComments(iseq);
	}

	@Override
	public int addInfo(InfoBoard i) {
		InfoBoardDAO idao = sqlSession.getMapper(InfoBoardDAO.class);
		return idao.addInfo(i);
	}

	@Override
	public int hitUp(String iseq) {
		InfoBoardDAO idao = sqlSession.getMapper(InfoBoardDAO.class);
		return idao.hitUp(iseq);
	}

	@Override
	public int voteUp(String iseq) {
		InfoBoardDAO idao = sqlSession.getMapper(InfoBoardDAO.class);
		return idao.voteUp(iseq);
	}

	@Override
	public int commentUp(String iseq) {
		InfoBoardDAO idao = sqlSession.getMapper(InfoBoardDAO.class);
		return idao.commentUp(iseq);
	}

	@Override
	public int commentDown(String iseq) {
		InfoBoardDAO idao = sqlSession.getMapper(InfoBoardDAO.class);
		return idao.commentDown(iseq);
	}

}
