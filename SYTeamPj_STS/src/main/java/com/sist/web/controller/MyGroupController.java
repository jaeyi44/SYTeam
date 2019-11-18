package com.sist.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sist.web.dao.ChalgroupDAO;
import com.sist.web.dao.GroupMemberDAO;
import com.sist.web.dao.MemberDAO;
import com.sist.web.dao.PostDAO;
import com.sist.web.util.ChangeURL;
import com.sist.web.vo.Chalgroup;
import com.sist.web.vo.GroupMember;
import com.sist.web.vo.Member;

import com.sist.web.vo.Pcomments;
import com.sist.web.vo.MyRoute;
import com.sist.web.vo.Post;
import com.sist.web.vo.WorkoutGson;

@Controller
@RequestMapping("/myGroup/*")
public class MyGroupController {
	
	@Autowired
	private ChalgroupDAO gdao;
	@Autowired
	private PostDAO pdao;
	@Autowired
	private MemberDAO mdao;
	@Autowired
	private GroupMemberDAO gmdao;
	
	
	@RequestMapping("myGroup.do")
	public String myGroup(HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
	    String mid =(String)session.getAttribute("mid");
		
		List<String> gids= gmdao.viewMygroup(mid);
		List<Chalgroup> cList = new ArrayList<Chalgroup>();
		Chalgroup cGroup = null;
		List<Chalgroup> adList = gdao.getGroupByAdmin(mid);
		int aSize=adList.size();
		if(gids!=null || aSize!=0) {
			String group="yes";
			model.addAttribute("group",group);
			int cSize = gids.size();
			for (int i = 0; i < gids.size(); i++) {
				
				cGroup = new Chalgroup();
				cGroup = gdao.getGroupByGid(gids.get(i).toString());
				
				System.out.println("gid :"+gids.get(i).toString());
				System.out.println("cGroup.getAdmin() :"+cGroup.getAdmin());
				
				cList.add(cGroup);
			}
			for (int i = 0; i < adList.size(); i++) {
				System.out.println("adList.get(i).getTitle()"+adList.get(i).getTitle());
			}
			
			System.out.println("cSize"+cSize);
			System.out.println("aSize"+aSize);
			model.addAttribute("adList",adList);
			model.addAttribute("cSize",cSize);
			model.addAttribute("aSize",aSize);
			model.addAttribute("cList",cList);
			model.addAttribute("gids",gids);
			
			return "myGroup.jsp";
			
		}else {
			String group="no";
			model.addAttribute("cGroup",group);
			
			return "myGroup.jsp";
		}

	}
	
	@RequestMapping("groupDetail.do")
	public String groupDetail(HttpServletRequest request, Model model, String gid){
		HttpSession session = request.getSession();
	    String mid =(String)session.getAttribute("mid");
	    
	    Member activeM = mdao.viewMember(mid);
	    
	    String nickname = activeM.getNickname();

		List<Post> pList = pdao.getPosts(gid);
		List<String> mIdList = gmdao.searchMem(gid);
		List<Member> groupMem = new ArrayList<Member>();
		
		Chalgroup cg = gdao.getGroupByGid(gid);
		
		String adminId = cg.getAdmin();
		System.out.println("adminID : "+adminId);
		
		
		Member m = null;
		for (int i = 0; i < mIdList.size(); i++) {
			m = new Member();
			m = mdao.viewMember(mIdList.get(i).toString());
			
			groupMem.add(m);
		}
		
		Member adminM = new Member();
		adminM = mdao.viewMember(adminId);
		
		System.out.println("pList.size() :  "+pList.size());

		System.out.println(gid+"踰� 諛⑹쓽 post 紐⑤몢 遺덈윭�샂");
		
		model.addAttribute("gid",gid);
		model.addAttribute("pList",pList);
		model.addAttribute("groupMem",groupMem);
		model.addAttribute("cg",cg);
		model.addAttribute("nickname",nickname);
		model.addAttribute("adminM",adminM);

		return "groupDetail.jsp";
	}
	
	
	
	@RequestMapping("meetUp.do")
	public String meetUp(HttpServletRequest request,String pg,String f,String q,Model model) {
		HttpSession session = request.getSession();
		String mid = (String) session.getAttribute("mid");
		
		if (mid == null) {
			System.out.println("세션 X : 로그인 페이지로 이동");
			session.setAttribute("returnURL", "/myGroup/meetUp.do");
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
				f="title";
			}
			if(q==null || q.equals("")){
				q="";
			}else{
				urlStext=ChangeURL.getURLformat(q);
			}
			
			int sPage = ipg -(ipg-1)%5;
			
			int seqCount  = gdao.cgSeqCount(f, q);
			List<Chalgroup> cList = gdao.getGroups(ipg, f, q);
			
			int pgCount=0;
			if(seqCount%10!=0){
				pgCount=(seqCount/10)+1;
			}else{
				pgCount=(seqCount/10);
			}
			
			model.addAttribute("cList",cList);
			model.addAttribute("pg", ipg);
			model.addAttribute("f", f);
			model.addAttribute("q", q);
			model.addAttribute("urlStext", urlStext);
			model.addAttribute("sPage", sPage);
			model.addAttribute("pgCount", pgCount);
			model.addAttribute("seqCount", seqCount);
			
			
			
			return "meetUp.jsp";
		}
		
		
	}

	@RequestMapping("groupContent.do")
	public String groupContent(HttpServletRequest request,Model model, String gid) {
		
		HttpSession session = request.getSession();
	    String mid =(String)session.getAttribute("mid");
		Chalgroup cg = gdao.getGroupByGid(gid);
		cg.setStartd(cg.getStartd().split(" ")[0]);
		cg.setEndd(cg.getEndd().split(" ")[0]);
		int memNum = gmdao.countMem(gid);
		List<String> mList = gmdao.searchMem(gid);

		
		int check=0;
		for (int i = 0; i < mList.size(); i++) {
			if(mList.get(i).equals(mid)) {
				check=1;
			}
		}
		System.out.println("---------------------------check값 확인: "+check);
		model.addAttribute("cg",cg);
		model.addAttribute("memNum",memNum);
		model.addAttribute("mid",mid);
		model.addAttribute("mList",mList);
		model.addAttribute("check",check);
		
		return "groupContent.jsp";
	}
	
	@RequestMapping("likeUp.do")
	@ResponseBody
	public String likeUp(Model model, String pseq) {

		System.out.println("pseq : "+pseq);
		
		int af = pdao.likeUp(pseq);
		
		if(af==1)
		{
			System.out.println("좋아요 성공");
			return "yes";
		}else
		{
			System.out.println("좋아요 실패");
			return null;
		}

	}
	
	@RequestMapping(value={"createGroup.do"},method=RequestMethod.GET)
	public String createGroup() {
		
		return "createGroup.jsp";
	}
	
	@RequestMapping(value={"createGroup.do"},method=RequestMethod.POST)
	public String createGroup(HttpServletRequest request,Model model) {
		HttpSession session = request.getSession();
	    String mid =(String)session.getAttribute("mid");
	    
		String path = "myGroup/gImages";
		String realPath = request.getServletContext().getRealPath(path);
		System.out.println("realPath : "+realPath);
		
		
		int af=0;
		MultipartRequest mulReq;
		
		try {
			mulReq = new MultipartRequest(request, realPath, 10*1024*1024, "UTF-8", new DefaultFileRenamePolicy());
			String filesrc=mulReq.getFilesystemName("filesrc");
			String title =mulReq.getParameter("title");
			String content =mulReq.getParameter("content");
			String kind =mulReq.getParameter("kind");
			String startd =mulReq.getParameter("startd");
			String endd =mulReq.getParameter("endd");
			
			System.out.println(title);
			System.out.println(content);
			System.out.println(kind);
			System.out.println(startd);
			System.out.println(endd);
			
			
			Chalgroup cg = new Chalgroup();
			cg.setAdmin(mid);
			cg.setContent(content);
			cg.setEndd(endd);
			cg.setFilesrc(filesrc);
			cg.setStartd(startd);
			cg.setTitle(title);
			cg.setKind(kind);
			
			af = gdao.addGroup(cg);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		if(af == 1){ 
			System.out.println("그룹 생성 성공");
			String groupAdd="yes";
			model.addAttribute("groupAdd",groupAdd);
			return "createGroup.jsp";
		} else {
			System.out.println("그룹 생성 실패");
			String groupAdd="no";
			model.addAttribute("groupAdd",groupAdd);
			return "createGroup.jsp";
		} 
	}
	
	@RequestMapping("showCom.do")
	@ResponseBody
	public String showCom(Model model, String pseq) {
		
		System.out.println("pseq : "+pseq);
		List<Pcomments> pcList = pdao.getComments(pseq);
		
	    Gson gson = new Gson();
	    String pcListData = null;
	    pcListData = gson.toJson(pcList);
		
		System.out.println(pcListData);
		
		return pcListData;
		
	}

	@RequestMapping(value={"enterGroup.do"},method=RequestMethod.GET)
	public String enterGroup(HttpServletRequest request, String gid) {
		HttpSession session = request.getSession();
	    String mid =(String)session.getAttribute("mid");
		List<GroupMember> gmlist = gmdao.getGroupMems();
	    
	    int check = 0;
		for(int i=0; i<gmlist.size(); i++) {
			int igmseq = Integer.parseInt(gmlist.get(i).getGmseq());
			if(igmseq>check) {
				check=igmseq;
			}
		}
		String gmseq = Integer.toString(check+1);
		System.out.println("---------------------------gmseq: "+gmseq);
		System.out.println("---------------------------gid: "+gid);
		System.out.println("---------------------------mid: "+mid);
		int af = gmdao.addGroupMem(gid, mid, gmseq);
		System.out.println("--------------------그룹가입확인: "+af);
		return "meetUp.jsp";
	}

	@RequestMapping(value = "regPost.do", headers = "content-type=multipart/*", method = RequestMethod.POST)
	public String regPost(HttpServletRequest request) throws IOException {
		
	try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	
		String path = "/myGroup/pImages";
		String realPath = request.getServletContext().getRealPath(path);
		System.out.println("upload realpath: " + realPath);
	
		MultipartRequest mulReq = new MultipartRequest(request, realPath, 10*1024*1024, "UTF-8", new DefaultFileRenamePolicy());
		String nickname = mulReq.getParameter("nickname");
		String content = mulReq.getParameter("content");
		String gid = mulReq.getParameter("gid");
		String filesrc = mulReq.getFilesystemName("filesrc");
		
		//저장 경로 설정
	/*	String root = multi.getSession().getServletContext().getRealPath("/");
        String path = root+"myGroup/upload";
        System.out.println("path : " +path);

        //업로드 되는 파일명
        String newFileName = ""; 
        
        File dir = new File(path);
        if(!dir.isDirectory()){
            dir.mkdir();
        }
        
        Iterator<String> files = multi.get
        String fileName = null;
        
        while(files.hasNext()){
            String uploadFile = files.next();
                         
            MultipartFile mFile = multi.getFile(uploadFile);
            fileName = mFile.getOriginalFilename();
            System.out.println("실제 파일 이름 : " +fileName);
            newFileName = System.currentTimeMillis()+"."
                    +fileName.substring(fileName.lastIndexOf(".")+1);
            System.out.println("newFileName : " +newFileName);

            try {
                mFile.transferTo(new File(path+newFileName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		
		String nickname = multi.getParameter("nickname");
		String content = multi.getParameter("content");
		String gid = multi.getParameter("gid");*/
		int af;
		
		
		System.out.println("nickname : " +nickname);
		System.out.println("content : "+content);
		System.out.println("gid : " +gid);
		System.out.println("filesrc : " +filesrc);

		
		Post p = new Post(null, gid, content, null, filesrc, nickname, null);

		af = pdao.addPost(p);
		
		if(af==1)
		{
			System.out.println("글쓰기 성공");
			return "redirect:groupDetail.do?gid="+gid;
		}else
		{
			System.out.println("글쓰기 실패");
			return null;
		}
	}

	@RequestMapping("myPosts.do")
	public String myPosts(HttpServletRequest request, Model model, String gid) {
		
		HttpSession session = request.getSession();
	    String mid =(String)session.getAttribute("mid");
	    
	    Member activeM = mdao.viewMember(mid);
	    
	    String nickname = activeM.getNickname();
	    
		List<Post> mypList = pdao.getMyPosts(nickname);
		List<String> mIdList = gmdao.searchMem(gid);
		List<Member> groupMem = new ArrayList<Member>();
		
		Member m = null;
		for (int i = 0; i < mIdList.size(); i++) {
			m = new Member();
			m = mdao.viewMember(mIdList.get(i).toString());
			
			groupMem.add(m);
		}
		
		Post post = null;
		
		Chalgroup cg = gdao.getGroupByGid(gid);
		System.out.println("mypList.size() :  "+mypList.size());

		System.out.println(gid+"번 방의 myPosts 모두 불러옴");
		
		model.addAttribute("mid",mid);
		model.addAttribute("mypList",mypList);
		model.addAttribute("groupMem",groupMem);
		model.addAttribute("cg",cg);
		model.addAttribute("nickname",nickname);
		
		return "myPost.jsp";
	}
	
}
