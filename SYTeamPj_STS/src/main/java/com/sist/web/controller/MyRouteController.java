package com.sist.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.web.dao.MyRouteDAO;
import com.sist.web.util.ChangeURL;
import com.sist.web.vo.Bookmark;
import com.sist.web.vo.Member;
import com.sist.web.vo.MyRoute;
import com.sist.web.vo.Waypoints;

@Controller
@RequestMapping("/myRoute/*")
public class MyRouteController {
	
	@Autowired
	private MyRouteDAO mrdao;
	
	@RequestMapping(value={"findRoute.do"},method=RequestMethod.GET)
	public String findRoute(HttpServletRequest request,String pg,String f,String q,Model model
			) {
		HttpSession session = request.getSession();
		String mid = (String) session.getAttribute("mid");
		
		if (mid == null) {
			System.out.println("세션 X : 로그인 페이지로 이동");
			session.setAttribute("returnURL", "/myRoute/findRoute.do");
			return "redirect:../joinus/login.do";
		}else{
			
			int ipg;
			String urlStext="";
			if(pg!=null && !pg.equals("")){
				ipg = Integer.parseInt(pg);
			}else{
				ipg=1;
			}
			if(f==null || f.equals("")){
				f="rName";
			}
			if(q==null || q.equals("")){
				q="";
			}else{
				urlStext=ChangeURL.getURLformat(q);
			}
			
			int sPage = ipg -(ipg-1)%5;
			
			int seqCount  = mrdao.mrSeqCount(f, q);
			List<MyRoute> list = mrdao.getMyRoutes(ipg, f, q);
			
			int pgCount=0;
			if(seqCount%5!=0){
				pgCount=(seqCount/5)+1;
			}else{
				pgCount=(seqCount/5);
			}
			
			model.addAttribute("list",list);
			model.addAttribute("pg", ipg);
			model.addAttribute("f", f);
			model.addAttribute("q", q);
			model.addAttribute("urlStext", urlStext);
			model.addAttribute("sPage", sPage);
			model.addAttribute("pgCount", pgCount);
			model.addAttribute("seqCount", seqCount);
			
			
			
			return "findRoute.jsp";
		}
	}
	
	
	@RequestMapping(value={"createRoute.do"},method=RequestMethod.GET)
	public String createRoute(HttpServletRequest request,Model model) {
		HttpSession session = request.getSession();
		String mid = (String) session.getAttribute("mid");
		
		if (mid == null) {
			System.out.println("세션 X : 로그인 페이지로 이동");
			session.setAttribute("returnURL", "/myRoute/createRoute.do");
			return "redirect:../joinus/login.do";
		}else{
			return "createRoute.jsp";
		}
	}
	
	@RequestMapping(value={"createRoute.do"},method=RequestMethod.POST)
	public String createRouteProc(HttpServletRequest request,Model model,String rName,String activity,String OR,String DE,String log,String distance,String OR_latLng,String DE_latLng) {
		HttpSession session = request.getSession();
		String mid = (String) session.getAttribute("mid");
		MyRoute mr = null;
		Waypoints wpO =null;
		Waypoints wpD =null;
		
		if (mid == null) {
			System.out.println("세션 X : 로그인 페이지로 이동");
			session.setAttribute("returnURL", "/myRoute/createRoute.do");
			return "redirect:../joinus/login.do";
		}else{
			mr=new MyRoute();
			wpO=new Waypoints();
			wpD=new Waypoints();
			mr.setActivity(activity);
			mr.setDistance(distance);
			if(log!=null) {
				mr.setLog(log);
			}
			mr.setMid(mid);
			mr.setrName(rName);
			mrdao.setMyRoute(mr);
			
			String rSeq=mrdao.getRseq();
			
			wpO.setpName(OR);
			wpO.setLatLng(OR_latLng);
			wpO.setrSeq(rSeq);
			mrdao.setWaypoints(wpO);
			
			wpD.setpName(DE);
			wpD.setLatLng(DE_latLng);
			wpD.setrSeq(rSeq);
			mrdao.setWaypoints(wpD);
			
			System.out.println("루트 입력 성공");
			
			return "redirect:routeDetail.do?rSeq="+rSeq;
		}
	}
	
	@RequestMapping(value={"routeDetail.do"},method=RequestMethod.GET)
	public String routeDetail(HttpServletRequest request,Model model,String rSeq,String hit) {
		HttpSession session = request.getSession();
		String mid = (String) session.getAttribute("mid");
		MyRoute mr = new MyRoute();
		List<Waypoints> wlist = new ArrayList<Waypoints>();
		if (mid == null) {
			System.out.println("세션 X : 로그인 페이지로 이동");
			session.setAttribute("returnURL", "/myRoute/routeDetail.do?rSeq="+rSeq);
			return "redirect:../joinus/login.do";
		}else{
			
			
					mrdao.hitUp(rSeq);
					mr=mrdao.myRouteDetail(rSeq);
					wlist=mrdao.getWaypoints(rSeq);
					
					for (int i = 0; i < wlist.size(); i++) {
						wlist.get(i).getLatLng();
						System.out.println(wlist.get(i).getLatLng());
					}
					
					model.addAttribute("mr",mr);
					model.addAttribute("wlist",wlist);
					return "routeDetail.jsp";
		
		}
	}
	
	@RequestMapping(value={"routeEdit.do"},method=RequestMethod.GET)
	public String routeEdit(HttpServletRequest request,Model model,String rSeq) {
		HttpSession session = request.getSession();
		String mid = (String) session.getAttribute("mid");
		MyRoute mr = new MyRoute();
		List<Waypoints> wlist = new ArrayList<Waypoints>();
		if (mid == null) {
			System.out.println("세션 X : 로그인 페이지로 이동");
			session.setAttribute("returnURL", "/myRoute/routeEdit.do?rSeq="+rSeq);
			return "redirect:../joinus/login.do";
		}else{
			
			mr=mrdao.myRouteDetail(rSeq);
			wlist=mrdao.getWaypoints(rSeq);
			
			for (int i = 0; i < wlist.size(); i++) {
				wlist.get(i).getLatLng();
				System.out.println(wlist.get(i).getLatLng());
			}
			
			model.addAttribute("mr",mr);
			model.addAttribute("wlist",wlist);
			return "routeEdit.jsp";
		}
	}
	
	@RequestMapping(value={"routeEdit.do"},method=RequestMethod.POST)
	public String routeEditProc(HttpServletRequest request,Model model,String rSeq,String rName,String activity,String OR,String DE,String log,String distance,String OR_latLng,String DE_latLng) {
		HttpSession session = request.getSession();
		String mid = (String) session.getAttribute("mid");
		
		/*System.out.println("OR_latLng : "+OR_latLng);
		System.out.println("DE_latLng : "+DE_latLng);*/
		MyRoute mr = null;
		Waypoints wpO =null;
		Waypoints wpD =null;
		List<Waypoints> wlist = new ArrayList<Waypoints>();
		if (mid == null) {
			System.out.println("세션 X : 로그인 페이지로 이동");
			session.setAttribute("returnURL", "/myRoute/routeEdit.do?rSeq="+rSeq);
			return "redirect:../joinus/login.do";
		}else{
			
			wlist=mrdao.getWaypoints(rSeq);
			if(OR_latLng=="" ||OR_latLng==null) {
				/*System.out.println("wlist.get(0).getLatLng() : "+wlist.get(0).getLatLng());
				System.out.println("wlist.get(1).getLatLng() : "+wlist.get(1).getLatLng());*/
				OR_latLng = wlist.get(0).getLatLng();
				DE_latLng = wlist.get(1).getLatLng();
				
			}
				mrdao.delWaypoints(rSeq);
				
				
				mr=new MyRoute();
				wpO=new Waypoints();
				wpD=new Waypoints();
				mr.setActivity(activity);
				mr.setDistance(distance);
				if(log!=null) {
					mr.setLog(log);
				}
				mr.setMid(mid);
				mr.setrName(rName);
				mrdao.editMyRoute(rSeq, mr);
				
				
				
				wpO.setpName(OR);
				wpO.setLatLng(OR_latLng);
				wpO.setrSeq(rSeq);
				mrdao.setWaypoints(wpO);
				
				wpD.setpName(DE);
				wpD.setLatLng(DE_latLng);
				wpD.setrSeq(rSeq);
				mrdao.setWaypoints(wpD);
				
				System.out.println("루트 수정 성공");
			}
			return "redirect:routeDetail.do?rSeq="+rSeq;
			
			
	}
	
	@RequestMapping(value={"routeDel.do"},method=RequestMethod.GET)
	public String routeDel(HttpServletRequest request,Model model,String rSeq) {
			int af1 = mrdao.delWaypoints(rSeq);
			int af2 = mrdao.delMyRoute(rSeq);
			
			if(af1+af2==3) {
				System.out.println("글 삭제 성공");
				return "redirect:findRoute.do";
			}else {
				System.out.println("글 삭제 실패");
				return "redirect:findRoute.do";
			}
		
		
	}
	
	@RequestMapping(value={"myRoute.do"},method=RequestMethod.GET)
	public String myRoute(HttpServletRequest request,Model model,String rSeq) {
		HttpSession session = request.getSession();
		String mid = (String)session.getAttribute("mid");
		if(mid == null){
			return "redirect:../joinus/login.do";
		}
		
		else{

			List<Bookmark> bmlist = new ArrayList<Bookmark>();
			List<MyRoute> mrlist = new ArrayList<MyRoute>();
			List<MyRoute> bList = new ArrayList<MyRoute>();
			MyRoute r = new MyRoute();
			bmlist=mrdao.getBookmarks(mid);
			for (int i = 0; i < bmlist.size(); i++) {
				r=mrdao.myRouteDetail(bmlist.get(i).getrSeq());
				bList.add(r);
			}
			mrlist=mrdao.getMyRoute(mid);
			model.addAttribute("mrlist",mrlist);
			model.addAttribute("bList",bList);
			
			return "myRoute.jsp";

		}
		
		
	}
	
	@RequestMapping("bookmark.do")
	@ResponseBody
	public String bookmark(String mid,String rSeq) {
		
		String bSeq=mrdao.getBseq(mid, rSeq);
		if(bSeq==null) {
			
			int af = mrdao.setBookmark(rSeq, mid);
			if(af==1) {
				System.out.println("bookmark 추가 성공");
				return "addOk";
			}else {
				return "addError";
			}
			
		}else {
			int af = mrdao.delBookmark(bSeq);
			if(af==1) {
				System.out.println("bookmark 추가 성공");
				return "delOk";
			}else {
				return "delError";
			}
			
		}
	}

	
	
	
}
