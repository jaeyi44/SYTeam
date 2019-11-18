package com.sist.web.dao;

import java.util.ArrayList;

import com.sist.web.vo.Qcomment;
import com.sist.web.vo.QnABoard;

public interface QnABoardDAO {
      //게시글 한 개 가져오기
      public QnABoard getQnABoard(String seq);
      
      //게시글 목록 가져오기
      public ArrayList<QnABoard> getQnABoards(int pg, String field, String query);

      //게시글 작성
      public int addQnABoard(QnABoard q);
      
      //게시글 수정
      public int editQnABoard(String title, String content, String fileSrc, String qSeq);

      //게시글 삭제
      public int delQnABoard(String qSeq);
      
      //게시글 숫자 카운트
      public int qnASeqCount(String kind, String search);
      
      //댓글 불러오기
      public Qcomment getQnAComment(String qSeq);
      
      //댓글 목록 가져오기
      public ArrayList<Qcomment> getQnAComments(int pg, String field, String query);
      
      //댓글 삭제
      public int delQnAComment(String cSeq);
         
      //댓글쓰기
      public int addReply(String qSeq, String cContent);
      
      public int changeStatus(String qSeq);
      
}