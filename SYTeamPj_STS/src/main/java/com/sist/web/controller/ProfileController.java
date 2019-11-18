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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sist.web.dao.MemberDAO;
import com.sist.web.dao.MyRouteDAO;
import com.sist.web.vo.Bookmark;
import com.sist.web.vo.Member;
import com.sist.web.vo.MyRoute;

@Controller
@RequestMapping("/profile/*")
public class ProfileController {

	@Autowired
	public MemberDAO mdao;
	
	
	@RequestMapping(value={"myProfile.do"}, method=RequestMethod.GET)
	public String myProfile(HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
		String mid = (String)session.getAttribute("mid");
		if(mid == null){
			return "redirect:../joinus/login.do";
		}
		
		else{
			Member m = mdao.viewMember(mid);
			model.addAttribute("m",m);
			
			return "myProfile.jsp";

		}
	}
	
	@RequestMapping(value={"profileEdit.do"}, method=RequestMethod.GET)
	public String profileEdit(HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
		String mid = (String)session.getAttribute("mid");
		System.out.println(mid);
		System.out.println("확인");
		
		if(mid == null){
			return "redirect:../joinus/login.do";
		}
		else{
			Member m = mdao.viewMember(mid);
			model.addAttribute("m",m);
			System.out.println(m.getPurpose());
			
			return "profileEdit.jsp";
		}
	}
	
	
	@RequestMapping(value={"profileEdit.do"}, method=RequestMethod.POST)
	public String profileEdit(HttpServletRequest request) {
		
		String path = "profile/images";
		String realPath = request.getServletContext().getRealPath(path);
		System.out.println("realPath : "+realPath);
		
		int af=0;
		MultipartRequest mulReq;
		
		
		try {
			mulReq = new MultipartRequest(request, realPath, 10*1024*1024, "UTF-8", new DefaultFileRenamePolicy());
			String profilePic = mulReq.getFilesystemName("profilePic");
			String mid = mulReq.getParameter("mid");
			String pwd = mulReq.getParameter("pwd");
			String nickname = mulReq.getParameter("nickname");
			String contact = mulReq.getParameter("contact");
			String height = mulReq.getParameter("height");
			String weight = mulReq.getParameter("weight");
			String[] purposes = mulReq.getParameterValues("purpose");
			String purpose="";
			System.out.println("purpose.length:"+purposes.length);
			for (int i = 0; i < purposes.length; i++) {
				System.out.println("purpose:"+purposes[i]);
				purpose += purposes[i];
				if(i<purposes.length-1) {
					purpose += ",";
				}
			}
			
			System.out.println("--------purpose:"+purpose);
			
			String orifileSrcprofilePicSrc = mulReq.getOriginalFileName("profilePic");
			System.out.println("profilePic : "+profilePic);
			System.out.println("orifileSrcprofilePicSrc : "+orifileSrcprofilePicSrc);
			Member m = new Member();
			m.setMid(mid);
			m.setPwd(pwd);
			m.setNickname(nickname);
			m.setContact(contact);
			m.setHeight(height);
			m.setWeight(weight);
			m.setPurpose(purpose);
			m.setProfilePic(profilePic);
			
			
			
			
			
			af = mdao.editMember(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("af="+af);
		if(af >= 1){ 
			System.out.println("회원정보수정 성공");
			return "redirect:../index.do";
		} else {
			System.out.println("회원정보수정 실패");
			return "redirect:../index.do";
		} 
		
	}
	
	
	@RequestMapping(value={"profileDel.do"}, method=RequestMethod.GET)
	public String profileDelProc(HttpServletRequest request){
		HttpSession session = request.getSession();
		String mid = (String)session.getAttribute("mid");
		
		int af = mdao.delMember(mid);
		if (af == 1) {
			System.out.println("회원탈퇴성공");
			return "redirect:../joinus/logout.do";  
		} else {
			System.out.println("회원탈퇴실패");
			return "../index.jsp";
		}
	}
	
	@RequestMapping(value={"nicknameCheck.do"}, method=RequestMethod.POST)
	@ResponseBody
	public String nicknameCheck(String nickname){
		
		Member m = mdao.viewMemberbyNick(nickname);
		
		
		if(m==null){
			System.out.println("사용가능 닉네임");
			return "yes";
		}else{
			System.out.println("사용불가 닉네임");
			return "no";
		}
		
		
	}
	
	
	
	
	
	
	
	
	
}
