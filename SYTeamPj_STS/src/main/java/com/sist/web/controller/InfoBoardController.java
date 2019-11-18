package com.sist.web.controller;

import java.io.IOException;
import java.util.ArrayList;

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
import com.sist.web.dao.InfoBoardDAO;
import com.sist.web.dao.MemberDAO;
import com.sist.web.util.ChangeURL;
import com.sist.web.vo.Comments;
import com.sist.web.vo.InfoBoard;
import com.sist.web.vo.Member;

@Controller
@RequestMapping("/InfoBoard/*")
public class InfoBoardController {
	@Autowired
	private InfoBoardDAO idao;
	@Autowired
	private MemberDAO mdao;
	
	@RequestMapping(value={"infoBoard.do"},method=RequestMethod.GET)
	public String infoBoard(HttpServletRequest request,Model model, String pg, String f, String kind, String q) {
		System.out.println("infoBoardController Runs");
		int ipg=0;
		if(pg!=null && !pg.equals("")){
			ipg = Integer.parseInt(pg);
		}else{
			ipg = 1;
		}

		if(f==null || f.equals("")){
			f = "TITLE";  
		}
		String urlQuery="";
		if(q==null || q.equals("null")){
			q = "";  
		}else{
			urlQuery = ChangeURL.getURLformat(q);  
		}

		System.out.println("pg: " +ipg);
		System.out.println("field: " +f);
		System.out.println("query: " +q);

		int sPage = ipg - (ipg-1)%5;
		int seqCount = idao.infoSeqCount(f, q);
		HttpSession session = request.getSession();

		String mid = (String)session.getAttribute("mid");
		System.out.println("mid: "+mid);

		//Login & authorization
		Member m = null;
		if(mid!=null){
 		m = mdao.viewMember(mid);
 			String nickname = m.getNickname();
			System.out.println("nickname: "+nickname);
			model.addAttribute("nickname", nickname);
 			String myLevel = m.getMyLevel();
			System.out.println("myLevel: "+myLevel);
			model.addAttribute("myLevel", myLevel);
		}else{
 			String nickname = "guest";
 			System.out.println("nickname: "+nickname);
 			model.addAttribute("nickname", nickname);
 			String myLevel = "guest";
 			model.addAttribute("myLevel", myLevel);
 		}
		
		System.out.println("seqCount:" + seqCount);
		int finalPage = (seqCount/5)+(seqCount%5==0? 0:1);
		if(finalPage==0){
			finalPage=1;
		}
		System.out.println("finalPage: "+finalPage);

		ArrayList<InfoBoard> bList = idao.getInfos(ipg,f,q);

		model.addAttribute("bList", bList);
		model.addAttribute("ipg", ipg);
		model.addAttribute("f", f);
		model.addAttribute("q", q);
		model.addAttribute("urlQuery", urlQuery);
		model.addAttribute("sPage", sPage);
		model.addAttribute("finalPage", finalPage);

		return "infoBoard.jsp";
	}
	
	@RequestMapping(value={"infoBoardDetail.do"},method=RequestMethod.GET)
	public String infoBoardDetail(String mid, String cseq, String myLevel, String nickname, HttpServletRequest request, Model model,String hitUp,String iseq, String pg, String f, String q, String hit, String filesrc){
		System.out.println("infoBoardDetailController Runs");
		System.out.println("iseq: "+iseq);
		HttpSession session = request.getSession();
		InfoBoard i = null;
			mid = (String)session.getAttribute("mid");
			
			Member m = null;
			if(mid!=null){
	 		m = mdao.viewMember(mid);
	 			nickname = m.getNickname();
				System.out.println("nickname: "+nickname);
				model.addAttribute("nickname", nickname);
	 			myLevel = m.getMyLevel();
				System.out.println("myLevel: "+myLevel);
				model.addAttribute("myLevel", myLevel);
			}else{
	 			nickname = "guest";
	 			System.out.println("nickname: "+nickname);
	 			model.addAttribute("nickname", nickname);
	 			myLevel = "guest";
	 			model.addAttribute("myLevel", myLevel);
	 		}
			System.out.println("mid: "+mid);
			System.out.println("nickname: " +nickname);
			System.out.println("myLevel: " +myLevel);
     		
			if(hitUp!=null && !hitUp.equals("")){
			idao.hitUp(iseq);
		}
		
		i=idao.getInfo(iseq);
		System.out.println("title: "+idao.getInfo(iseq).getTitle());
		model.addAttribute("i", i);
		model.addAttribute("mid", mid);
		model.addAttribute("iseq", iseq);
		model.addAttribute("pg", pg);
		model.addAttribute("field", f);
		model.addAttribute("query", q);
		model.addAttribute("filesrc", filesrc);
		
		
		//댓글 표시용
		ArrayList<Comments> cList =idao.getComments(iseq);
		
		model.addAttribute("cList", cList);
		
		return "../InfoBoard/infoBoardDetail.jsp";
	}
	
	@RequestMapping(value={"infoBoardWrite.do"},method=RequestMethod.GET)
	public String infoBoardWrite(Model model, String pg, String f, String q){
		System.out.println("writecon runs");
		model.addAttribute("pg", pg);
		model.addAttribute("field", f);
		model.addAttribute("query", q);
		return "infoBoardWrite.jsp";
	}
	
	@RequestMapping(value={"infoBoardWrite.do"},method=RequestMethod.POST)
	public String infoBoardWrite(HttpServletRequest request) throws IOException{
		System.out.println("write ProcCon Runs");

		String path = "/InfoBoard/upload";
		String realPath = request.getServletContext().getRealPath(path);
		System.out.println("upload realpath: " + realPath);

		MultipartRequest mulReq = new MultipartRequest(request, realPath, 10*1024*1024, "UTF-8", new DefaultFileRenamePolicy());
		String title = mulReq.getParameter("title");
		String content = mulReq.getParameter("content");
		String kind = mulReq.getParameter("kind");
		String filesrc = mulReq.getFilesystemName("filesrc");

		System.out.println("title: " +title);
		System.out.println("fileSrc: " + filesrc);

//		String mid = (String)request.getSession().getAttribute("mid");

		InfoBoard i = new InfoBoard();
		i.setTitle(title);
		i.setContent(content);
		i.setFilesrc(filesrc);
		i.setKind(kind);
		int af = idao.addInfo(i);
		
		return "redirect:infoBoard.do";
	}	

	@RequestMapping(value={"vote.do"},method=RequestMethod.GET)
	public String vote(String iseq){
		System.out.println("vote run");
		idao.voteUp(iseq);
		InfoBoard i = idao.getInfo(iseq);
		System.out.println("vote:"+ i.getVote());
		return "redirect:infoBoard.do";

	}
	
	@RequestMapping(value={"infoBoardDelete.do"},method=RequestMethod.GET)
	public String infoBoardDelete(String iseq){
		System.out.println("DelCon Runs");
		int af = idao.delInfo(iseq);
		return "redirect:infoBoard.do";
	}
	

	@RequestMapping(value={"infoBoardEdit.do"}, method=RequestMethod.GET)
	public String infoBoardEdit(HttpServletRequest request,Model model,String iseq, String pg, String f, String q){
		System.out.println("EditCon Runs");
		InfoBoard i = idao.getInfo(iseq); 		

		model.addAttribute("i", i);
		model.addAttribute("iseq", iseq);
		model.addAttribute("pg", pg);
		model.addAttribute("field", f);
		model.addAttribute("query", q);
//		model.addAttribute("urlQuery", urlQuery);
		return "infoBoardEdit.jsp";
	}
	
	@RequestMapping(value={"infoBoardEdit.do"}, method=RequestMethod.POST)
	public String infoBoardEdit(String iseq,String content, String title){
		System.out.println("EditProcCon Runs");
		InfoBoard i = idao.getInfo(iseq);
		System.out.println("title: "+title);
		i.setTitle(title);
		i.setContent(content);
		int af = idao.editInfo(iseq, content, title);
		return "redirect:../InfoBoard/infoBoardDetail.do?iseq="+iseq;
	}
	

	@RequestMapping(value={"commentWrite.do"},method=RequestMethod.POST)
	@ResponseBody
	public String commentWrite(String iseq, String writer, String content){
		System.out.println("-----commentWController Runs----");
		System.out.println("iseq: " + iseq); //값 있음
		System.out.println("writer: "+writer);
		System.out.println("content: "+content);

		idao.addComment(iseq,writer,content);
		idao.commentUp(iseq);
		System.out.println("-------컨트롤러 끝----------");
		return "yes";
	}	
	
	@RequestMapping(value={"commentDelete.do"},method=RequestMethod.GET)
	public String commentDelete(String cseq, String iseq){
		System.out.println("CommentDelCon Runs");
		idao.delComment(cseq);
		System.out.println("1");
		idao.commentDown(iseq);
		System.out.println("2");
		return "redirect:../InfoBoard/infoBoardDetail.do?iseq="+iseq;
	}
	
	
	
}