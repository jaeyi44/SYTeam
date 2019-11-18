package com.sist.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.web.dao.ChalgroupDAO;
import com.sist.web.dao.MemberDAO;
import com.sist.web.dao.PostDAO;
import com.sist.web.util.CookieManager;
import com.sist.web.vo.Chalgroup;
import com.sist.web.vo.Member;

@Controller
public class JoinusController {
	
	@Autowired
	public MemberDAO mdao;
	
	@Autowired
	public PostDAO pdao;
	
	@Autowired
	public ChalgroupDAO cgdao;
	
	@RequestMapping("/index.do")
	public String index(HttpServletRequest request, Model model){
		  HttpSession session = request.getSession();
	      String mid =(String)session.getAttribute("mid"); 
	      
	      Cookie[] coos = request.getCookies();
	      
	      String cookieMid = CookieManager.getCookie(coos,"mid");
	      String cookiePwd= CookieManager.getCookie(coos,"pwd");
	      
	      System.out.println("cookieMid : "+cookieMid);
	      System.out.println("cookiePwd : "+cookiePwd);
	      
	      if(cookieMid!=null && !cookieMid.equals("")){
	    	  model.addAttribute("cookieMid", cookieMid);
	      }
	      if(cookiePwd!=null && !cookiePwd.equals("")){
	    	  model.addAttribute("cookiePwd", cookiePwd);
	      }
	      
	      ArrayList<String> rank =  pdao.getRank();
	      ArrayList<Chalgroup> list = new ArrayList<Chalgroup>(); 
	      for (int i = 0; i < 3; i++) {
	    	  list.add(cgdao.getGroupByGid(rank.get(i)));
		}
	      
	      
	      model.addAttribute("list",list);
	      
		if(mid!=null){
	    	  model.addAttribute("mid",mid);
	      }else {
	    	  model.addAttribute("mid",mid);
	      }
		return "index.jsp";
	}
	
	@RequestMapping("/joinus/login.do")
	public String login(){
		
		return "redirect:../index.do";
	}
	
	@RequestMapping(value={"/joinus/join.do"}, method=RequestMethod.GET)
	public String join(){
		System.out.println("가입 하러 고고");
		return "joinus.jsp";
	}
	
	@RequestMapping(value={"/joinus/join.do"}, method=RequestMethod.POST)
	public String join(Member m) throws IOException{


		System.out.println("mid : "+ m.getMid());
		System.out.println("pwd : "+ m.getPwd());
		System.out.println("bod : "+ m.getDob());
		
		
		

		int af = mdao.addMember(m);
		
		if(af==1)
		{
			System.out.println("회원가입성공");
			return "redirect:../index.do";
		}else
		{
			System.out.println("회원가입실패");
			return "redirect:../index.do";
		}
	}
	
	@RequestMapping("/joinus/idcheck.do")
	@ResponseBody
	public String idcheck(String mid){
				
		Member m = mdao.viewMember(mid);
		
		if(m==null){
			return "1";
		}else{
			return "0";
		}
	}
	
	@RequestMapping("/joinus/nickcheck.do")
	@ResponseBody
	public String nickcheck(String nickname){
				
		Member m = mdao.viewMemberbyNick(nickname);
		
		if(m==null){
			return "1";
		}else{
			return "0";
		}
	}
	
   @RequestMapping("/joinus/memberData.do")
   @ResponseBody
   public String memberData(String mid,String pwd){
      
      Member m = mdao.viewMember(mid);
      if(m==null){
         return "midErr";
      }else if(!m.getPwd().equals(pwd)){
         return "pwdErr";
      }else{
         return "";
      }
   }
	   
	   
	   @RequestMapping(value={"/joinus/login.do"},method=RequestMethod.POST)
	   public String login(HttpServletRequest request, HttpServletResponse response,String mid,String pwd,String checkBoxMid){
	      
	      HttpSession session = request.getSession();
	      session.setAttribute("mid", mid);
	      
	         if(checkBoxMid!=null && !checkBoxMid.equals("")){
	            Cookie ck = new Cookie("mid",mid);
	            ck.setMaxAge(60*60*24*30);
	            ck.setPath("/");
	            System.out.println("쿠키생성(mid) : " + mid);
	            response.addCookie(ck);
	            Cookie ck2 = new Cookie("pwd",pwd);
	            ck2.setPath("/");
	            ck2.setMaxAge(60*60*24*30);
	            System.out.println("쿠키생성(pwd) : "+ pwd);
	            response.addCookie(ck2);
	         }else{
	            Cookie ck = new Cookie("mid",null);
	            ck.setMaxAge(0);
	            ck.setPath("/");
	            System.out.println("쿠키삭제(mid)");
	            response.addCookie(ck);
	            Cookie ck2 = new Cookie("pwd",null);
	            ck.setPath("/");
	            ck2.setMaxAge(0);
	            System.out.println("쿠키삭제(pwd)");
	            response.addCookie(ck2);
	         }

	         String returnURL = (String)request.getSession().getAttribute("returnURL");
	         
	         if(returnURL != null && !returnURL.equals("")){
	            return "redirect:"+returnURL;
	         }
	      
	         return "redirect:../index.do";
	   }
	   
	   @RequestMapping(value={"/joinus/logout.do"},method=RequestMethod.GET)
	   public String logout(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
	      HttpSession session = request.getSession();
	         session.invalidate();
	      return "redirect:../index.do";
	   }


}
