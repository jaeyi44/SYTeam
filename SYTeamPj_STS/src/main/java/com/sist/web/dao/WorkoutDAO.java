package com.sist.web.dao;

import java.util.ArrayList;

import com.sist.web.vo.WcntByMonth;
import com.sist.web.vo.Workout;
import com.sist.web.vo.WorkoutCnt;

public interface WorkoutDAO {
		//운동데이터 리스트찾기
		public ArrayList<Workout> getWorkoutsById(String mid);

		///운동데이터 하나찾기
		public Workout getWorkout(String wseq);
		
		//////운동정보 삽입
		public int addWorkout(Workout w);
		
		///운동정보수정
		public int editWorkout(Workout w,String wseq);
		
		///운동정보 삭제
		public int delWorkout(String wseq);

		//종목별 운동 횟수 뽑기
		public ArrayList<WorkoutCnt> getWcntByKind(String mid);
		
		//종목별 운동 월별 횟수 뽑기
		public ArrayList<WcntByMonth> getWcntByMth(String mid, String kind, String sdate, String edate);

}
