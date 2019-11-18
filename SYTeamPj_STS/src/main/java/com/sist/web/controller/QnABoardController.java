package com.sist.web.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sist.web.dao.MemberDAO;
import com.sist.web.dao.QnABoardDAO;
import com.sist.web.util.ChangeURL;
import com.sist.web.vo.Member;
import com.sist.web.vo.Qcomment;
import com.sist.web.vo.QnABoard;

@Controller
@RequestMapping("/qnaBoard/*")

public class QnABoardController {
   
   @Autowired
   private QnABoardDAO qdao;
   
   @Autowired
   private MemberDAO mdao;
   
   //글, 댓글 전체 보기
   @RequestMapping(value={"qnaBoard.do"}, method = RequestMethod.GET)
   public String qnaBoard(HttpServletRequest request, String mid, String pg, String f, String q, Model model){
      System.out.println("qna board controller");
      
      int ipg = 0;
      if(pg!=null && !pg.equals("")){
         ipg = Integer.parseInt(pg);
      }else{
         ipg=1;
      }
      
      if(f==null || f.equals("")){
         f="TITLE";
      }
      String urlQuery="";
      if(q==null || q.equals("null")){
         q="";
      }else{
         urlQuery = ChangeURL.getURLformat(q);
      }
      
      int sPage = ipg-(ipg-1)%5;
      int seqCount = qdao.qnASeqCount(f,q);
      int finalpage = seqCount/5 + (seqCount%5==0?0:1);
      
      ArrayList<Qcomment> cList = qdao.getQnAComments(ipg, f, q);
      ArrayList<Member> mList = mdao.viewMembers();
      ArrayList<QnABoard> qList = qdao.getQnABoards(ipg, f, q);
      Member m = new Member();
      mList = mdao.viewMembers();
      
     System.out.println("qList size:" + qList.size());
      model.addAttribute("qList", qList);
      model.addAttribute("cList",cList);
      model.addAttribute("pg",ipg);
      model.addAttribute("field",f);
      model.addAttribute("query", q);
      model.addAttribute("sPage", sPage);
      model.addAttribute("urlQuery", urlQuery);
      model.addAttribute("finalpage", finalpage);
      model.addAttribute("mid",mid);
      
      for (int i = 0; i < qList.size(); i++) {
      System.out.println("qlist profile: " +qList.get(i).getProfilePic());
      System.out.println("qlist seq: " +qList.get(i).getqSeq());
   }
      
      System.out.println("pg"+ipg);
      System.out.println("sPage"+sPage);
      System.out.println("finalpage"+finalpage);
            
      return "qnaBoard.jsp";
   }
   
   //글지우기
   @RequestMapping(value={"qnaBoardDel.do"}, method=RequestMethod.GET)
   public String qnaBoardDel(HttpServletRequest request, String qSeq, String pg,String query, String field, String urlQuery,Model model){
      System.out.println("qnaBoardDel 실행");
      System.out.println(qSeq);
      
      Qcomment c = qdao.getQnAComment(qSeq);
      int af=0;
      
      if(c != null) {
         String cSeq = c.getcSeq();
         qdao.delQnAComment(cSeq);
         qdao.delQnABoard(qSeq);
      }else {
         af = qdao.delQnABoard(qSeq);
      }
      
      
      if(af==1){
         System.out.println("게시글 삭제됐습니다.");
         return "redirect:qnaBoard.do";
      }else{
         System.out.println("게시글 삭제 실패ㅠㅠ");
         return "redirect:qnaBoard.do";
      } 
      
      
   }
   
   //글쓰기 get
   @RequestMapping(value={"qnaBoardWrite.do"}, method=RequestMethod.GET)
   public String qnaBoardWrite(HttpServletRequest request, String pg, String f, String q, String urlQuery, Model model){

       model.addAttribute("pg", pg);
       model.addAttribute("field", f);
       model.addAttribute("urlQuery", urlQuery);
       
       return "qnaBoardWrite.jsp";
   }
   
   //글쓰기 post
   @RequestMapping(value={"qnaBoardWrite.do"}, method=RequestMethod.POST)
   public String qnaBoardWrite(HttpServletRequest request) throws IOException{
      request.setCharacterEncoding("UTF-8");
      
      String path="/qnaBoard/upload";
      String realPath=request.getServletContext().getRealPath(path);
      
      System.out.println("realPath : "+realPath);

      MultipartRequest mulReq = new MultipartRequest(request, realPath, 10*1024*1024,"UTF-8",new DefaultFileRenamePolicy());
      String fileSrc = mulReq.getFilesystemName("file");
      System.out.println("fileSrc : "+fileSrc);
      HttpSession session = request.getSession();
      String mid = (String)session.getAttribute("mid");
      String title = mulReq.getParameter("title");
      String content = mulReq.getParameter("content");
      
      System.out.println("mid" +mid);
      System.out.println("title"+title);
      System.out.println("content"+content);
      
      QnABoard q = new QnABoard();
      
      q.setContent(content);
      q.setTitle(title);
      q.setWriter(mid);
      q.setFileSrc(fileSrc);
      
       int af = qdao.addQnABoard(q);
          if(af == 1){
            System.out.println("글작성 성공!");
            return "redirect:qnaBoard.do";
            
          }else{
            System.out.println("글작성 실패 ㅠㅠ");
            return "qnaBoard.do";
          }
   
   }
   
   //글 수정 get
   @RequestMapping(value={"qnaBoardEdit.do"}, method=RequestMethod.GET)
   public String qnaBoardEdit(String fileSrc, String qSeq, String pg, String f, String q, Model model){
      
      QnABoard qBoard = qdao.getQnABoard(qSeq);
      
      model.addAttribute("fileSrc",fileSrc);
      model.addAttribute("qBoard", qBoard);
      model.addAttribute("qSeq", qSeq);
      model.addAttribute("pg", pg);
      model.addAttribute("field", f);
      model.addAttribute("query", q);
   
      return "qnaBoardEdit.jsp";
   }
   
   //글수정 post
   @RequestMapping(value={"qnaBoardEdit.do"}, method=RequestMethod.POST)
   public String qnaBoardEdit(HttpServletRequest request,String pg, String f, String q, QnABoard qBoard, Model model){
      
      String title = request.getParameter("title");
      String content = request.getParameter("content");
      String fileSrc = request.getParameter("fileSrc");
      String qSeq = request.getParameter("qSeq");
      
      qBoard.setTitle(title);
      qBoard.setContent(content);
      qBoard.setFileSrc(fileSrc);
      
      int af = qdao.editQnABoard(title, content, fileSrc, qSeq);
      model.addAttribute("title",title);
      model.addAttribute("content",content);
      model.addAttribute("qBoard",qBoard);
      
      if(af == 1){
         System.out.println("글수정성공!");
         return "redirect:qnaBoard.do";
      }else{
         System.out.println("글수정 실패 ㅠㅠ");
         return "redirect:qnaBoard.do";
      }
      
   }
   
   //코멘트 삭제
   @RequestMapping(value={"qnaCommnetDel.do"}, method=RequestMethod.GET)
   public String qnaCommnetDel(QnABoard qBoard, String cSeq, String pg, String field, String urlQuery,Model model){
      System.out.println("cseq : "+cSeq);
      int af = qdao.delQnAComment(cSeq);
      System.out.println("af : "+af);
      
      model.addAttribute("cSeq",cSeq);
      model.addAttribute("qBoard",qBoard);
      
       if(af==1){
         System.out.println("댓글 삭제됐습니다.");
         return "redirect:qnaBoard.do";
      }else{
         System.out.println("댓글 삭제 실패ㅠㅠ");
         return "redirect:qnaBoard.do";
      } 
   }

   //댓글 올리기
   @RequestMapping(value={"replyCreate.do"}, method=RequestMethod.GET)
   public String replyCreate(HttpServletRequest request){
      System.out.println("replycreate");
      
      String qSeq = request.getParameter("qSeq");
      String cContent = request.getParameter("cContent");
      System.out.println("qSeq: "+qSeq);
      System.out.println("cContent: "+cContent);
      int af = qdao.addReply(qSeq,cContent);
      if(af==1) {
         Qcomment c = qdao.getQnAComment(qSeq);
         int af2 = qdao.changeStatus(qSeq);
         System.out.println("댓글입력 완료");
         return "redirect:qnaBoard.do";
      }else {
         System.out.println("댓글 입력 실패");
      }
      
      return "replyCreate.jsp";

   }
}
