package com.sist.web.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import com.sist.web.vo.Comments;
import com.sist.web.vo.Pcomments;
import com.sist.web.vo.Post;

public class PostDAOService implements PostDAO{
	
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public int addPost(Post p) {
		PostDAO pdao = sqlSession.getMapper(PostDAO.class);
		return pdao.addPost(p);
	}

	@Override
	public int editPost(Post p) {
		PostDAO pdao = sqlSession.getMapper(PostDAO.class);
		return pdao.editPost(p);
	}

	@Override
	public int delPost(Post p) {
		PostDAO pdao = sqlSession.getMapper(PostDAO.class);
		return pdao.delPost(p);
	}

	@Override
	public Post getPost(String pseq) {
		PostDAO pdao = sqlSession.getMapper(PostDAO.class);
		return pdao.getPost(pseq);
	}

	@Override
	public ArrayList<Post> getPosts(String gid) {
		PostDAO pdao = sqlSession.getMapper(PostDAO.class);
		return pdao.getPosts(gid);
	}
	
	@Override
	public ArrayList<Post> getMyPosts(String nickname) {
		PostDAO pdao = sqlSession.getMapper(PostDAO.class);
		return pdao.getMyPosts(nickname);
	}
	
	@Override
	public int likeUp(String pseq) {
		PostDAO pdao = sqlSession.getMapper(PostDAO.class);
		return pdao.likeUp(pseq);
	}
	
	@Override
	public int delComment(String pcSeq) {
		PostDAO pdao = sqlSession.getMapper(PostDAO.class);
		return pdao.delComment(pcSeq);
	}
	
	@Override
	public int addComment(String pseq, String pcWriter, String pcContent) {
		PostDAO pdao = sqlSession.getMapper(PostDAO.class);
		return pdao.addComment(pseq,pcWriter,pcContent);
	}
	
	@Override
	public int comNum(String pseq) {
		PostDAO pdao = sqlSession.getMapper(PostDAO.class);
		return pdao.comNum(pseq);
	}
	
	@Override
	public ArrayList<Pcomments> getComments(String pseq) {
		PostDAO pdao = sqlSession.getMapper(PostDAO.class);
		return pdao.getComments(pseq);
	}

	@Override
	public int comUp(String pseq) {
		PostDAO pdao = sqlSession.getMapper(PostDAO.class);
		return pdao.comUp(pseq);
	}

	@Override
	public ArrayList<String> getRank() {
		PostDAO pdao = sqlSession.getMapper(PostDAO.class);
		return pdao.getRank();
	}


}
