package com.sist.web.dao;

import java.util.ArrayList;

import com.sist.web.vo.Comments;
import com.sist.web.vo.InfoBoard;

public interface InfoBoardDAO {

	public int infoSeqCount(String f,String q);
	
	public ArrayList<InfoBoard> getInfos(int ipg, String f, String q);
	
	public InfoBoard getInfo(String iseq);
	
	public int editInfo(String iseq,String content,String title);
	
	public int delInfo(String seq);
	
	public int delComment(String cseq);
	
	public int addComment(String ciseq, String cwriter, String ccontent);
	
	public ArrayList<Comments> getComments(String iseq);
	
	public int addInfo(InfoBoard i);
	
	public int hitUp(String iseq);
	
	public int voteUp(String iseq);
	
	public int commentUp(String iseq);
	
	public int commentDown(String iseq);
	
}
