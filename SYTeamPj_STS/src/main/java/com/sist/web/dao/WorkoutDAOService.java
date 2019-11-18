package com.sist.web.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import com.sist.web.vo.WcntByMonth;
import com.sist.web.vo.Workout;
import com.sist.web.vo.WorkoutCnt;

public class WorkoutDAOService implements WorkoutDAO{
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public ArrayList<Workout> getWorkoutsById(String mid) {
		WorkoutDAO wdao = sqlSession.getMapper(WorkoutDAO.class);
		return wdao.getWorkoutsById(mid);
	}

	@Override
	public Workout getWorkout(String wseq) {
		WorkoutDAO wdao = sqlSession.getMapper(WorkoutDAO.class);
		return wdao.getWorkout(wseq);
	}

	@Override
	public int addWorkout(Workout w) {
		WorkoutDAO wdao = sqlSession.getMapper(WorkoutDAO.class);
		return wdao.addWorkout(w);
	}

	@Override
	public int editWorkout(Workout w,String wseq) {
		WorkoutDAO wdao = sqlSession.getMapper(WorkoutDAO.class);
		return wdao.editWorkout(w, wseq);
	}

	@Override
	public int delWorkout(String wseq) {
		WorkoutDAO wdao = sqlSession.getMapper(WorkoutDAO.class);
		return wdao.delWorkout(wseq);
	}
	
	@Override
	public ArrayList<WorkoutCnt> getWcntByKind(String mid) {
		WorkoutDAO wdao = sqlSession.getMapper(WorkoutDAO.class);
		return wdao.getWcntByKind(mid);
	}
	
	@Override
	public ArrayList<WcntByMonth> getWcntByMth(String mid, String kind, String sdate, String edate) {
		WorkoutDAO wdao = sqlSession.getMapper(WorkoutDAO.class);
		return wdao.getWcntByMth(mid, kind, sdate, edate);
	}
	
}
