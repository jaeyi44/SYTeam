package com.sist.web.controller;

import java.io.IOException;
import java.sql.SQLException;
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
import com.sist.web.dao.MallDAO;
import com.sist.web.dao.MemberDAO;
import com.sist.web.util.ChangeURL;
import com.sist.web.vo.Items;
import com.sist.web.vo.Member;
import com.sist.web.vo.MyCart;
import com.sist.web.vo.OrderList;
import com.sist.web.vo.Review;

@Controller
@RequestMapping("/mall/*")
public class MallController {
	
	@Autowired
	private MallDAO mlDao;
	@Autowired
	private MemberDAO mdao;
	
	
	@RequestMapping(value= {"LoginCheck.do"},method=RequestMethod.POST)
	@ResponseBody
	public String loginCheck(String mid, String pwd, HttpServletRequest request,Model model) {
		System.out.println("---ID Checking---");
		 Member m = mdao.viewMember(mid);
	      if(m==null){
	         return "ID Error";
	      }else if(!m.getPwd().equals(pwd)){
	         return "PWD Error";
	      }else{
	         return "Welcome!";
	      }
	}
	
	@RequestMapping(value= {"mallLogin.do"},method=RequestMethod.POST)
	public String mallLogin(HttpServletRequest request) {
		System.out.println("---Loging in---");
		HttpSession session = request.getSession();
		String mid = request.getParameter("mid");
		session.setAttribute("mid", mid);
		return "redirect:mallMain.do";
	}
	
	
	@RequestMapping(value= {"mallLogOut.do"}, method=RequestMethod.GET)
	public String mallLogOut(HttpServletRequest request,Model model) {
		System.out.println("----Logging out-----");
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:mallMain.do";
	}
	
	@RequestMapping(value={"mallMain.do"},method=RequestMethod.GET)
	public String mallMain(HttpServletRequest request,Model model, String pg, String q, String f){
		System.out.println("-------Welcome to the mall-------");
		HttpSession session = request.getSession();
		String mid = (String)session.getAttribute("mid");
		System.out.println("mid: "+mid);
		
		Member m = null;
		if(mid!=null){
 		m = mdao.viewMember(mid);
 			String nickname = m.getNickname();
			System.out.println("nickname: "+nickname);
			model.addAttribute("nickname", nickname);
 			String myLevel = m.getMyLevel();
			System.out.println("myLevel: "+myLevel);
			model.addAttribute("myLevel", myLevel);
			List<MyCart> cList = mlDao.viewCart(mid);
			model.addAttribute("cList", cList);
		}else{
 			String nickname = "guest";
 			System.out.println("nickname: "+nickname);
 			model.addAttribute("nickname", nickname);
 			String myLevel = "guest";
 			model.addAttribute("myLevel", myLevel);
 		}
		
	      int ipg=0;
	      
	      if(pg!=null && !pg.equals("")){
	         ipg = Integer.parseInt(pg);
	      }else{
	         ipg = 1;
	      }

	      if(f==null || f.equals("")){
	         f = "ITEMNAME";  
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
	      
	      int sPage = ipg - (ipg-1)%3;
	      int seqCount = mlDao.itemNoCount(f, urlQuery);
	       int finalpage = seqCount/5 + (seqCount%5==0?0:1);
	      
	      
	      ArrayList<Items> mainList = mlDao.getMainItems(ipg, f, urlQuery);
	      for(Items i : mainList) {
	         System.out.println(i.getItemName());
	      }
	      
	      System.out.println("mainList size :" + mainList.size() );
	      
	      model.addAttribute("mainList", mainList);
	      model.addAttribute("pg", ipg);
	      model.addAttribute("f", f);
	      model.addAttribute("q", q);
	      model.addAttribute("urlQuery", urlQuery);
	      model.addAttribute("sPage", sPage);
	      model.addAttribute("finalpage", finalpage);
	      
		
		
		return "mallMain.jsp";
	}

	
	@RequestMapping(value={"allProducts.do"},method=RequestMethod.GET)
	public String allProducts(HttpServletRequest request,Model model, String pg, String f, String kind, String q) throws SQLException{
		System.out.println("------all products-----");
		HttpSession session = request.getSession();
		String mid = (String)session.getAttribute("mid");
		System.out.println("mid: "+mid);
		
		int ipg=0;
		if(pg!=null && !pg.equals("")){
			ipg = Integer.parseInt(pg);
		}else{
			ipg = 1;
		}

		if(f==null || f.equals("")){
			f = "ITEMNAME";  
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
		int seqCount = mlDao.itemNoCount(f, q);
		System.out.println("seqCount:" + seqCount);
		int finalPage = (seqCount/5)+(seqCount%5==0? 0:1);
		if(finalPage==0){
			finalPage=1;
		}
		System.out.println("finalPage: "+finalPage);
		
		ArrayList<Items> iList = mlDao.getItems(ipg, f, q);

		model.addAttribute("iList", iList);
		model.addAttribute("pg", ipg);
		model.addAttribute("f", f);
		model.addAttribute("q", q);
		model.addAttribute("urlQuery", urlQuery);
		model.addAttribute("sPage", sPage);
		model.addAttribute("finalPage", finalPage);
		
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
					List<MyCart> cList = mlDao.viewCart(mid);
					model.addAttribute("cList", cList);
				}else{
					mid = "guest";
		 			String nickname = "guest";
		 			String myLevel = "guest";
		 			System.out.println("nickname: "+nickname);
		 			model.addAttribute("nickname", "guest");
		 			model.addAttribute("myLevel", myLevel);
		 		}
		return "allProducts.jsp";
	}
	
	
	@RequestMapping(value={"newArrivals.do"},method=RequestMethod.GET)
	public String newArrivals(HttpServletRequest request,Model model, String pg, String f, String kind, String q) throws SQLException{
		System.out.println("-----new arrivals----");
		HttpSession session = request.getSession();
		String mid = (String)session.getAttribute("mid");
		System.out.println("mid: "+mid);
		
		int ipg=0;
		if(pg!=null && !pg.equals("")){
			ipg = Integer.parseInt(pg);
		}else{
			ipg = 1;
		}

		if(f==null || f.equals("")){
			f = "ITEMNAME";  
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
		
		int sPage = ipg - (ipg-1)%6;
		
		mlDao.getNewItems(ipg, f, q);
		
		ArrayList<Items> iList = mlDao.getNewItems(ipg, f, urlQuery);

		model.addAttribute("iList", iList);
		model.addAttribute("ipg", ipg);
		model.addAttribute("f", f);
		model.addAttribute("q", q);
		model.addAttribute("urlQuery", urlQuery);
		model.addAttribute("sPage", sPage);
//		model.addAttribute("finalPage", finalPage);
		
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
					List<MyCart> cList = mlDao.viewCart(mid);
					model.addAttribute("cList", cList);
				}else{
		 			String nickname = "guest";
		 			System.out.println("nickname: "+nickname);
		 			model.addAttribute("nickname", nickname);
		 			String myLevel = "guest";
		 			model.addAttribute("myLevel", myLevel);
		 		}
		return "newArrivals.jsp";
	}
	
	
	@RequestMapping(value={"mallAdmin.do"},method=RequestMethod.GET)
	public String mallAdmin(Model model){
		System.out.println("to AdminMode");
		
		List<OrderList> aList = mlDao.adminOrderList();
		model.addAttribute("aList", aList);

		return "mallAdmin.jsp";
	}

	@RequestMapping(value={"itemUp.do"},method=RequestMethod.POST)
	public String itemUp(HttpServletRequest request) throws IOException{
		System.out.println("itemUp Controller");
		
		String path = "/mall/upload";
		String realPath = request.getServletContext().getRealPath(path);
		
		MultipartRequest mulReq = new MultipartRequest(request, realPath, 10*1024*1024, "UTF-8", new DefaultFileRenamePolicy());
			String itemName = mulReq.getParameter("itemName");
			String itemPrice = mulReq.getParameter("itemPrice");
			String itemType = mulReq.getParameter("itemType");
			String itemUse = mulReq.getParameter("itemUse");
			String itemImg = mulReq.getFilesystemName("itemImg");
			String itemDesc = mulReq.getParameter("itemDesc");
			String itemStock = mulReq.getParameter("itemStock");
			
			Items i = new Items();
			i.setItemName(itemName);
			i.setItemPrice(itemPrice);
			i.setItemType(itemType);
			i.setItemUse(itemUse);
			i.setItemImg(itemImg);
			i.setItemDesc(itemDesc);
			i.setItemStock(itemStock);
		int af = mlDao.itemUpload(i);
		System.out.println("item Upload Success");
		
		return "redirect:mallAdmin.do";
	}
	
	@RequestMapping(value={"itemDel.do"},method=RequestMethod.GET)
	public String itemDel(String itemNo) throws SQLException {
		mlDao.superDel(itemNo);
		mlDao.superDel2(itemNo);
		mlDao.itemDel(itemNo);
		System.out.println("item gone");
		return "redirect:allProducts.do";
	}
	
	@RequestMapping(value={"itemDetail.do"},method=RequestMethod.GET)
	public String itemDetail(HttpServletRequest request, Model model, String itemNo){
		System.out.println("-----itemDetail-----");
		System.out.println("itemNo: "+itemNo);
		HttpSession session = request.getSession();
		String mid = (String)session.getAttribute("mid");
		System.out.println("mid: "+mid);
		
		Items i = null;
		i = mlDao.itemDetail(itemNo);
		model.addAttribute("i",i);
		model.addAttribute("itemNo",itemNo);
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
			List<MyCart> cList = mlDao.viewCart(mid);
			model.addAttribute("cList", cList);
		}else{
 			String nickname = "guest";
 			System.out.println("nickname: "+nickname);
 			model.addAttribute("nickname", nickname);
 			String myLevel = "guest";
 			model.addAttribute("myLevel", myLevel);
 		}
		
		List<Review> rList = mlDao.getReviews(itemNo);
		model.addAttribute("rList",rList);
		
		
		return "itemDetail.jsp";
	}

	//Picking multiple items from cart...
	@RequestMapping(value= {"cartPurchase.do"},method=RequestMethod.GET)
	public String cartPurchase(HttpServletRequest request, Model model, String itemNo) {
		//mid에 딸린 cart 중 선택한 아이템만 가져오기
		System.out.println("----cartPurchase----");
		HttpSession session = request.getSession();
		String mid = (String)session.getAttribute("mid");
		System.out.println("mid: "+mid);
		
		MyCart cartItem = null;
		List<MyCart> buyList = new ArrayList<MyCart>(); //구매확정 리스트
		String[] cpList =request.getParameterValues("cartItems");
		//받아온 아이템 번호들을 담은 리스트
		int onePrice = 0;
		int totalPrice = 0;
		
		for(int i=0; i<cpList.length;i++) { //아이템 갯수 만큼 for
			System.out.println(i+": "+cpList[i]); //아이템 번호들만 출력
			cartItem = mlDao.itemsFromCart(cpList[i], mid); //각 번호로 아이템 뽑음
			
			String quantity = cartItem.getQuantity();
			int itemQ = Integer.parseInt(quantity);
			String price = cartItem.getPrice();
			System.out.println("price:"+price);
			System.out.println("itemQ:"+itemQ);
			onePrice = (Integer.parseInt(price)*Integer.parseInt(quantity));
			System.out.println("onePrice:"+onePrice);
			totalPrice =(totalPrice+onePrice);
			buyList.add(cartItem); //뽑은 아이템들을 구매 리스트에 넣음
		}
		System.out.println("totalPrice:" +totalPrice);
		
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cpList", cpList);
		model.addAttribute("buyList", buyList);
		
		return "cartPurchase.jsp";
	}
	
	//Buying multiple items thru cart...
	@RequestMapping(value= {"cartPurchase.do"},method=RequestMethod.POST)
	public String cartPurchaseProc(HttpServletRequest request, Model model) {
		System.out.println("----Purchase CheckOut----");
		HttpSession session = request.getSession();
		String mid = (String)session.getAttribute("mid");
		String address = request.getParameter("address");
		System.out.println("mid: "+mid);
		List<MyCart> checkoutItems = new ArrayList<MyCart>();
		int onePrice = 0;
		int totalPrice = 0;
		
		String[] itemNumbers = request.getParameterValues("itemNo");
		for(int i=0;i<itemNumbers.length;i++) {
			
			checkoutItems.add(mlDao.itemsFromCart(itemNumbers[i],mid));
			String quantity = checkoutItems.get(i).getQuantity();
			
			int itemQ = Integer.parseInt(quantity);
			
			String price = checkoutItems.get(i).getPrice();
			System.out.println("price:"+price);
			System.out.println("itemQ:"+itemQ);
			onePrice = (Integer.parseInt(price)*itemQ);
			System.out.println("onePrice:"+onePrice);
			totalPrice = totalPrice + onePrice;
			System.out.println("total checkout price:"+totalPrice);
			String total = Integer.toString(totalPrice);
			
			mlDao.itemPurchase(mid, checkoutItems.get(i).getItemNo(),checkoutItems.get(i).getItemName() , address, checkoutItems.get(i).getPrice(), quantity, total);
			System.out.println("Item "+(i+1) +"purchased");
			mlDao.StockDown(checkoutItems.get(i).getItemNo(), quantity);
			System.out.println("Stock down by: "+quantity);
			mlDao.delCart(checkoutItems.get(i).getItemNo());
			System.out.println("item " +(i+1) +"removed from cart");
		}
		
		return "redirect:allProducts.do";
	}
	
	
	//Buying one item directly from itemDetail
	@RequestMapping(value={"itemPurchase.do"},method=RequestMethod.POST)
	public String itemPurchase(HttpServletRequest request, Model model, String itemNo){
		System.out.println("--------item Purchase---------");

		HttpSession session = request.getSession();
		String mid = (String)session.getAttribute("mid");
		System.out.println("mid: "+mid);
		
		Items i = mlDao.itemDetail(itemNo);
		
		String itemName = i.getItemName();
		String itemPrice = i.getItemPrice();
		String address = request.getParameter("address");
		String cardNo = request.getParameter("cardNo");
		String quantity = request.getParameter("mQuantity");
		
		int price = Integer.parseInt(itemPrice);
		int qty = Integer.parseInt(quantity);
		int itotal = price * qty;
		String total = Integer.toString(itotal); 
		
		System.out.println("itemNo: "+itemNo);
		System.out.println("itemName: "+itemName);
		System.out.println("itemPrice: "+itemPrice);
		System.out.println("cardNo:" + cardNo);
		System.out.println("qty: " + quantity);
		System.out.println("address: "+address);
		System.out.println("total price: " +total);
		
		int af = mlDao.itemPurchase(mid, itemNo, itemName, address, quantity, itemPrice, total);
	
		mlDao.StockDown(itemNo, quantity);
		System.out.println("Stock down by: "+quantity);
	
		return "redirect:allProducts.do";
	}

	
	@RequestMapping(value={"addReview.do"},method=RequestMethod.POST)
//	@ResponseBody
	public String addReview(HttpServletRequest request, Model model){
		System.out.println("--------AddReviewCon-----");
		
		HttpSession session = request.getSession();
		String mid = (String)session.getAttribute("mid");
		System.out.println("mid: "+mid);
		
		Member m = null;
		
		if(mid!=null){
 		m = mdao.viewMember(mid);
 			String nickname = m.getNickname();
			System.out.println("nickname: "+nickname);
			model.addAttribute("nickname", nickname);
		}else{
 			String nickname = "guest";
 			mid = "guest";
 			System.out.println("nickname: "+nickname);
 			model.addAttribute("mid",mid);
 			model.addAttribute("nickname", nickname);
 		}
		
		
		Review r = new Review();
		String itemNo = request.getParameter("itemNo");
//		String itemNo = (String) request.getAttribute("itemNo");
		System.out.println("itemNo: " +itemNo);
		String itemName = mlDao.itemDetail(itemNo).getItemName();
		String content = request.getParameter("reviewContent");
		String stars = request.getParameter("stars");
		System.out.println("stars: "+stars);
				
		r.setRid(mid);
		r.setContent(content);
		r.setItemName(itemName);
		r.setStar(stars);
		System.out.println("rid: " +mid);
		System.out.println("content: "+content);
		System.out.println("Rcontent: " +r.getContent());
		mlDao.addReview(itemNo, mid, r);  
		return "redirect:itemDetail.do?itemNo="+itemNo;
	}
	
	
	@RequestMapping(value={"myShopping.do"},method=RequestMethod.GET)
	public String myShopping(HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
		String mid = (String)session.getAttribute("mid");
		ArrayList<OrderList> oList =mlDao.getMyOrders(mid);
		model.addAttribute("oList", oList);
		return "myShopping.jsp";
	}

	@RequestMapping(value={"DelR.do"},method=RequestMethod.GET)
	public String DelReview(String reviewNo,String itemNo){
		mlDao.DelReview(reviewNo);
		return "redirect:itemDetail.do?itemNo="+itemNo;
	}
	
	
	@RequestMapping(value={"toCart.do"}, method=RequestMethod.GET)
	public String toCart(HttpServletRequest request,String itemNo){
		System.out.println("-------cartCont-----");
		HttpSession session = request.getSession();
		String mid = (String)session.getAttribute("mid");
		System.out.println("mid: "+mid);
		System.out.println("itemNo: "+itemNo);
		String quantity = request.getParameter("qtyhere");
		if(quantity.equals("")) {
			quantity = "1";
		}
			
		System.out.println("qty: "+ quantity);
		Items i = null;
		i = mlDao.itemDetail(itemNo);
		ArrayList<MyCart> mycurrentcart = mlDao.viewCart(mid);
		for(int index =0;index<mycurrentcart.size();index++) {
			String itemNumbers = mycurrentcart.get(index).getItemNo();
			System.out.println("currentItems: "+itemNumbers);
			
			if(itemNo.equals(itemNumbers)) {
				System.out.println("!! Duplicate item: "+ itemNo +"- qtt up by: "+quantity);
				mlDao.addMoretoCart(mid, itemNo, quantity);
				return "redirect:itemDetail.do?itemNo="+itemNo;
			}
		}
		
		System.out.println("[item added to cart]");
		mlDao.addCart(i, mid, itemNo, quantity);
		return "redirect:itemDetail.do?itemNo="+itemNo;
	}
	
	
	@RequestMapping(value={"delCart.do"},method=RequestMethod.GET)
	public String DelCart(String itemNo){
		mlDao.delCart(itemNo);
		return "mallMain.do";
	}
	
	
	@RequestMapping(value={"giftGuide.do"},method=RequestMethod.GET)
	   public String giftGuide(HttpServletRequest request,Model model, String pg, String kind){
	      System.out.println("------giftGuide-----");
	      HttpSession session = request.getSession();
	      String mid = (String)session.getAttribute("mid");
	      System.out.println("mid: "+mid);

	      int ipg=0;
	      if(pg!=null && !pg.equals("")){
	         ipg = Integer.parseInt(pg);
	      }else{
	         ipg = 1;
	      }

	      String f1 = request.getParameter("f1");
	      String f2 = request.getParameter("f2");
	      String f3 = request.getParameter("f3");
	      
	      String f = null;
	      if(f==null || f.equals("")) {
	         f="itemType";
	      }
	      String q = request.getParameter("q");
	      
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
	      //      int seqCount = mlDao.itemNoCount(f, urlQuery);

	      ArrayList<Items> iList =null;
	      
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
	         List<MyCart> cList = mlDao.viewCart(mid);
	         model.addAttribute("cList", cList);
	      }else{
	         mid = "guest";
	         String nickname = "guest";
	         String myLevel = "guest";
	         System.out.println("nickname: "+nickname);
	         model.addAttribute("nickname", "guest");
	         model.addAttribute("myLevel", myLevel);
	      }
	      
	      ArrayList<Items> sList = null;
	      
	      if(f3 == null) {
	         sList = mlDao.searchItems(f1, f2, "ITEMNO", "DESC");
	      }else if(f3 !=null) {
	         sList = mlDao.searchItems(f1, f2, "ITEMPRICE", f3);
	      }

	      System.out.println("f1= " +f1);
	      System.out.println("f2= "+f2);
	      System.out.println("f3= "+f3);
	      
	      model.addAttribute("iList", iList);
	      model.addAttribute("sList", sList);
	      model.addAttribute("ipg", ipg);
	      model.addAttribute("f", f);
	      model.addAttribute("f1",f1);
	      model.addAttribute("f2",f2);
	      model.addAttribute("f3",f3);
	      model.addAttribute("q", q);
	      model.addAttribute("urlQuery", urlQuery);
	      model.addAttribute("sPage", sPage);
	      
	      return "giftGuide.jsp";
	   }
	   
	
	
}
