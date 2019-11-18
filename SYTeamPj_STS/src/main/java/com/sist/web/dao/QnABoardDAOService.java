package com.sist.web.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import com.sist.web.vo.Qcomment;
import com.sist.web.vo.QnABoard;

public class QnABoardDAOService implements QnABoardDAO{
   
   private SqlSession sqlSession;

   public void setSqlSession(SqlSession sqlSession) {
      this.sqlSession = sqlSession;
   }

   @Override
   public QnABoard getQnABoard(String qSeq) {
      QnABoardDAO qdao = sqlSession.getMapper(QnABoardDAO.class);
      return qdao.getQnABoard(qSeq);
   }

   @Override
   public ArrayList<QnABoard> getQnABoards(int pg, String field, String query) {
      QnABoardDAO qdao = sqlSession.getMapper(QnABoardDAO.class);
      return qdao.getQnABoards(pg, field, query);
   }

   @Override
   public int editQnABoard(String title, String content, String fileSrc, String qSeq) {
      QnABoardDAO qdao = sqlSession.getMapper(QnABoardDAO.class);
      return qdao.editQnABoard(title, content, fileSrc, qSeq);
   }

   @Override
   public int delQnABoard(String qSeq) {
      QnABoardDAO qdao = sqlSession.getMapper(QnABoardDAO.class);
      return qdao.delQnABoard(qSeq);
   }

   @Override
   public int qnASeqCount(String kind, String search) {
      QnABoardDAO qdao = sqlSession.getMapper(QnABoardDAO.class);
      return qdao.qnASeqCount(kind, search);
   }

   @Override
   public Qcomment getQnAComment(String qSeq) {
      QnABoardDAO qdao = sqlSession.getMapper(QnABoardDAO.class);
      return qdao.getQnAComment(qSeq);
   }

   @Override
   public ArrayList<Qcomment> getQnAComments(int pg, String field, String query) {
      QnABoardDAO qdao = sqlSession.getMapper(QnABoardDAO.class);
      return qdao.getQnAComments(pg, field, query);
   }

   @Override
   public int delQnAComment(String cSeq) {
      QnABoardDAO qdao = sqlSession.getMapper(QnABoardDAO.class);
      return qdao.delQnAComment(cSeq);
   }

   @Override
   public int addReply(String qSeq, String cContent) {
      QnABoardDAO qdao = sqlSession.getMapper(QnABoardDAO.class);
      return qdao.addReply(qSeq, cContent);
   }

   @Override
   public int addQnABoard(QnABoard q) {
      QnABoardDAO qdao = sqlSession.getMapper(QnABoardDAO.class);
      return qdao.addQnABoard(q);
   }
   
   public int changeStatus(String qSeq) {
      QnABoardDAO qdao = sqlSession.getMapper(QnABoardDAO.class);
      return qdao.changeStatus(qSeq);
   }


}