package com.sist.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.sist.web.dao.WorkoutDAO;
import com.sist.web.vo.Member;
import com.sist.web.vo.WcntByMonth;
import com.sist.web.vo.Workout;
import com.sist.web.vo.WorkoutCnt;
import com.sist.web.vo.WorkoutGson;

@Controller
@RequestMapping("/training/*")
public class TrainingContoller {

	@Autowired
	private WorkoutDAO wdao;
	
	//운동데이터 저장
	@RequestMapping("addWorkout.do")
	@ResponseBody
	public String addWorkout(HttpServletRequest request,String kind, String regdate, String distance, String hour, String minute) throws IOException{

		HttpSession session = request.getSession();
	    String mid =(String)session.getAttribute("mid");
	    	      
		System.out.println("mid : "+ mid);
		System.out.println("kind : "+ kind);
		System.out.println("regdate : "+ regdate);
		System.out.println("distance : "+ distance);
		System.out.println("hour : "+ hour);
		
		Workout w = new Workout(null, mid, kind, hour,minute, distance, regdate.substring(0, 10));
		
		int af = wdao.addWorkout(w);

		if(af==1)
		{
			System.out.println("운동데이터 저장 성공");
			return "yes";
		}else
		{
			System.out.println("운동데이터 저장 실패");
			return null;
		}
	}
	
	//운동데이터 삭제
	@RequestMapping("delWorkout.do")
	@ResponseBody
	public String delWorkout(HttpServletRequest request,String wseq) throws IOException{

		System.out.println("wseq : "+ wseq);
	
		int af = wdao.delWorkout(wseq);

		if(af==1)
		{
			System.out.println("운동데이터 삭제 성공");
			return "yes";
		}else
		{
			System.out.println("운동데이터 삭제 실패");
			return null;
		}
	}
	
	@RequestMapping("editWorkout.do")
	@ResponseBody
	public String editWorkout(HttpServletRequest request,String kind, String regdate, String distance,
				String hour, String minute, String wseq) throws IOException{

		HttpSession session = request.getSession();
	    String mid =(String)session.getAttribute("mid");
	    String dbRegdate = regdate.replace("-", "/");
	    
		System.out.println("wseq : "+ wseq);
		System.out.println("kind : "+ kind);
		System.out.println("dbRegdate : "+ dbRegdate);
		System.out.println("distance : "+ distance);
		
		Workout w = new Workout(null, mid, kind, hour, minute, distance, dbRegdate);
		
		int af = wdao.editWorkout(w,wseq);
		
		
		if(af==1)
		{
			System.out.println("운동데이터 수정 성공");
			//와나 환장하겄네
			return "yes";
		}else
		{
			System.out.println("운동데이터 수정 실패");
			return null;
		}
	}
	
	@RequestMapping("workout.do")
	public String workout(HttpServletRequest request, Model model){
		
		HttpSession session = request.getSession();
	    String mid =(String)session.getAttribute("mid");
	    String calGsonData=null;
	    
	    Gson gson = new Gson();
	    

	    if(mid==null) {
	    	System.out.println("로그인을 하지 않았습니다.");
	    	
	    	model.addAttribute("mid","null");
	    	return "workout.jsp";
	    }else {
	    	List<Workout> wList = wdao.getWorkoutsById(mid);
	    	
	    	if(wList.size()<=0) {
	    		System.out.println("운동데이터가 없는 회원입니다.");
	    		
	    		model.addAttribute("mid", mid);
	    		model.addAttribute("wList", wList);
	    		model.addAttribute("calGsonData", " ");
	    		
	    		return "workout.jsp";
	    		
	    	}else {
	    		List<WorkoutGson> wGsonList= new ArrayList<WorkoutGson>();
	    		
	    		wGsonList = new ArrayList<WorkoutGson>();
	    		WorkoutGson wGson= null;
	    		
	    		System.out.println("운동데이터가 하나 이상 있습니다.");
    			for (int i = 0; i < wList.size(); i++) {
    				
    				wGson=new WorkoutGson();
    				
    				System.out.println("wList.get(i).getKind() : "+wList.get(i).getKind());
    				wGson.setTitle(wList.get(i).getKind());
    				String calRegdate = wList.get(i).getRegdate().replace("/", "-").substring(0, 10);
    				wGson.setStart(calRegdate);
    				int wseq = Integer.parseInt(wList.get(i).getWseq());
    				wGson.setId(wseq);
    				
    				wGsonList.add(wGson);	
    			}
    			
    			System.out.println("운동데이터가 뽑기 성공.");
				System.out.println("wGsonList.get(0).getTitle() : "+wGsonList.get(0).getTitle());
				
				
				calGsonData = gson.toJson(wGsonList);
				System.out.println(calGsonData);
			
				
				model.addAttribute("mid", mid);
				model.addAttribute("wList", wList);
				model.addAttribute("calGsonData", calGsonData);
				return "workout.jsp";
	    	}
	    }
	}
	
	@RequestMapping("getWorkout.do")
	@ResponseBody
	public String getWorkout(HttpServletRequest request, Model model, String wseq){
		HttpSession session = request.getSession();
	    String mid =(String)session.getAttribute("mid");
	    
	    System.out.println("wseq : "+wseq);
	    Workout w = wdao.getWorkout(wseq);
	    
	    String calRegdate = w.getRegdate().replace("/", "-").substring(0, 10);
	    
	    w.setRegdate(calRegdate);
	    
	    Gson gson = new Gson();
		String wGson = gson.toJson(w);
	    System.out.println("wGson : "+wGson);
	    
	    
	    return wGson;
	}
	
	@RequestMapping("statistics.do")
	public String statistics(HttpServletRequest request, Model model){
		
		HttpSession session = request.getSession();
	    String mid =(String)session.getAttribute("mid");
	    
	    List<WorkoutCnt> wcList = wdao.getWcntByKind(mid);
	    int wcSize = wcList.size();
	    if(wcSize==0) {
	    	System.out.println("운동데이터가 없어용");
	    	
	    	return "statisticsNot.jsp";
	    }
	    
	    List<WcntByMonth> wbmListS = wdao.getWcntByMth(mid,"swimming","17/01/01","17/12/31");	    
	    List<WcntByMonth> wbmListR = wdao.getWcntByMth(mid,"running","17/01/01","17/12/31");
	    List<WcntByMonth> wbmListC = wdao.getWcntByMth(mid,"cycling","17/01/01","17/12/31");
	    List<WcntByMonth> wbmListT = wdao.getWcntByMth(mid,"tennis","17/01/01","17/12/31");
	    List<WcntByMonth> wbmListS2 = wdao.getWcntByMth(mid,"soccer","17/01/01","17/12/31");
	    List<WcntByMonth> wbmListW = wdao.getWcntByMth(mid,"walking","17/01/01","17/12/31");
	    List<WcntByMonth> wbmListf = wdao.getWcntByMth(mid,"fitness","17/01/01","17/12/31");
	    
	    int wbmListSSize = wbmListS.size();
	    int wbmListRSize = wbmListR.size();
	    int wbmListCSize = wbmListC.size();
	    int wbmListTSize = wbmListT.size();
	    int wbmListS2Size = wbmListS2.size();
	    int wbmListWSize = wbmListW.size();
	    int wbmListfSize = wbmListf.size();

	    
	    System.out.println("wbmListS.size() : "+wbmListS.size());
	    System.out.println("wbmListC.size() : "+wbmListC.size());
	    System.out.println("wbmListT.size() : "+wbmListT.size());
	    System.out.println("wbmListS2.size() : "+wbmListS2.size());
	    System.out.println("wbmListf.size() : "+wbmListf.size());
	    System.out.println("wbmListR.size() : "+wbmListR.size());
	    
	    if(wbmListSSize==0 || wbmListRSize==0 || wbmListCSize==0 || wbmListTSize==0 || wbmListS2Size==0 || wbmListWSize==0 || wbmListfSize==0) {
	    	System.out.println("종목별 데이터가 없어용");
	    	
	    	return "statisticsNot.jsp";
	    }
	    
	    for(int i=0; i<wcList.size(); i++) {
	    	wcList.get(i).setDrilldown(wcList.get(i).getName());
	    }

	    Gson gson = new Gson();
		String wcListGson = gson.toJson(wcList);

		System.out.println("wbmListR.get(0).getMonth() : "+wbmListR.get(0).getMonth());
		
		model.addAttribute("wcListGson", wcListGson);
		
		model.addAttribute("wbmListS", wbmListS);
		model.addAttribute("wbmListR", wbmListR);
		model.addAttribute("wbmListC", wbmListC);
		model.addAttribute("wbmListT", wbmListT);
		model.addAttribute("wbmListS2", wbmListS2);
		model.addAttribute("wbmListW", wbmListW);
		model.addAttribute("wbmListf", wbmListf);

		return "statistics.jsp";
	}
	
	@RequestMapping("statisticsNot.do")
	public String statisticsNot(HttpServletRequest request, Model model){
		
		return "statisticsNot.jsp";
	}
		
	
}








