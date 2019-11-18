package com.sist.web.dao;

import java.util.ArrayList;

import com.sist.web.vo.Chalgroup;

public interface ChalgroupDAO {


	//전체그룹목록조회 or 검색
	public ArrayList<Chalgroup> getGroups(int pg,String field,String query);
	
	//검색결과 루트 카운트
	public int cgSeqCount(String field,String query);
	
	//GID로 특정 그룹 조회
	public Chalgroup getGroupByGid(String gid);
	
	//Admin으로 특정 그룹 조회
	public ArrayList<Chalgroup> getGroupByAdmin(String mid);
	
	//kind로 종목별 그룹 조회
	public ArrayList<Chalgroup> getGroupsByKind(String kind);


   //그룹생성
   public int addGroup(Chalgroup cg);
   
   //그룹삭제
   public int delGroup(String gid);

   //Like 증가시키기
   public int likeUp(String gid);
   
}