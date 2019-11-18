package com.sist.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sist.web.dao.EventDAO;
import com.sist.web.dao.MemberDAO;
import com.sist.web.vo.Event;
import com.sist.web.vo.Member;

@Controller
public class EventController {

	@Autowired
	public EventDAO edao;
	@Autowired
	public MemberDAO mdao;
	
	@RequestMapping(value={"event/eventCalendar.do"}, method=RequestMethod.GET)
	public String eventCalendar(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
		String mid = (String)session.getAttribute("mid");
		if(mid == null){
			return "redirect:../joinus/login.do";
		}
		else {
			Member m = mdao.viewMember(mid);
			model.addAttribute("m",m);
			List<Event> list = edao.getDates();
			model.addAttribute("list",list);
			return "eventCalendar.jsp";
		}
		
		
	}





}
