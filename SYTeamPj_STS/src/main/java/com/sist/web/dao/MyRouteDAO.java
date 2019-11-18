package com.sist.web.dao;

import java.util.List;
import com.sist.web.vo.MyRoute;
import com.sist.web.vo.Waypoints;
import com.sist.web.vo.Bookmark;
public interface MyRouteDAO {
	
	//해당 회원의 운동 루트
	public List<MyRoute> getMyRoute(String mid);
	
	//운동루트전체 OR 검색 결과
	public List<MyRoute> getMyRoutes(int pg,String field,String query);
	
	//클릭한 루트에 대한 지도 좌표 정보
	public List<Waypoints> getWaypoints(String rSeq);
	
	//운동 루트 입력
	public int setMyRoute(MyRoute mr);
	
	//운동 좌표 입력
	public int setWaypoints(Waypoints wp);
	
	//운동루트수정
	public int editMyRoute(String rSeq,MyRoute mr);

	//운동루트삭제
	public int delMyRoute(String rSeq);
	
	//운동좌표삭제
	public int delWaypoints(String rSeq);
	
	//클릭한 루트에 대한 상세정보
	public MyRoute myRouteDetail(String rSeq);
	
	//검색결과 루트 카운트
	public int mrSeqCount(String field,String query);
	
	//조회수 증가
	public int hitUp(String rSeq);
	
	//rSeq
	public String getRseq();
	
	//북마크 추가
	public int setBookmark(String rSeq,String mid);
	
	//북마크 삭제
	public int delBookmark(String bSeq);
	
	//북마크 조회
	public Bookmark getBookmark(String bSeq);
	
	public String getBseq(String mid,String rSeq);
	
	//회원 북마크 조회
	public  List<Bookmark> getBookmarks(String mid);
	

}
