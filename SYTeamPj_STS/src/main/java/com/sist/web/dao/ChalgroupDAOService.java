package com.sist.web.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import com.sist.web.vo.Chalgroup;

public class ChalgroupDAOService implements ChalgroupDAO{

   private SqlSession sqlsession;

   public void setSqlsession(SqlSession sqlsession) {
      this.sqlsession = sqlsession;
   }


	@Override
	public ArrayList<Chalgroup> getGroups(int pg,String field,String query) {
		ChalgroupDAO gdao = sqlsession.getMapper(ChalgroupDAO.class);
		return gdao.getGroups(pg,field,query);
	}


   @Override
   public Chalgroup getGroupByGid(String gid) {
      ChalgroupDAO gdao = sqlsession.getMapper(ChalgroupDAO.class);
      return gdao.getGroupByGid(gid);
   }

   @Override
   public ArrayList<Chalgroup> getGroupByAdmin(String mid) {
      ChalgroupDAO gdao = sqlsession.getMapper(ChalgroupDAO.class);
      return gdao.getGroupByAdmin(mid);
   }

   @Override
   public ArrayList<Chalgroup> getGroupsByKind(String kind) {
      ChalgroupDAO gdao = sqlsession.getMapper(ChalgroupDAO.class);
      return gdao.getGroupsByKind(kind);
   }

   @Override
   public int addGroup(Chalgroup cg) {
      ChalgroupDAO gdao = sqlsession.getMapper(ChalgroupDAO.class);
      return gdao.addGroup(cg);
   }

   @Override
   public int delGroup(String gid) {
      ChalgroupDAO gdao = sqlsession.getMapper(ChalgroupDAO.class);
      return gdao.delGroup(gid);
   }

   @Override
   public int likeUp(String gid) {
      ChalgroupDAO gdao = sqlsession.getMapper(ChalgroupDAO.class);
      return gdao.likeUp(gid);
   }

	@Override
	public int cgSeqCount(String field, String query) {
		ChalgroupDAO gdao = sqlsession.getMapper(ChalgroupDAO.class);
		return gdao.cgSeqCount(field, query);
	}


}