package com.sist.web.dao;

import java.util.ArrayList;

import com.sist.web.vo.Comments;
import com.sist.web.vo.Pcomments;
import com.sist.web.vo.Post;

public interface PostDAO {
	
	public int addPost(Post p);
	
	public int editPost(Post p);
	
	public int delPost(Post p);
	
	public ArrayList<String> getRank();
	
	public Post getPost(String pseq);
	
	public ArrayList<Post> getMyPosts(String nickname);
	
	public ArrayList<Post> getPosts(String gid);
	
	public int likeUp(String pseq);
	
	public int comUp(String pseq);
	
	public int delComment(String pcSeq);
	
	public int addComment(String pseq, String writer, String content);
	
	public int comNum(String pseq);
	
	public ArrayList<Pcomments> getComments(String pseq);
	
	
}
