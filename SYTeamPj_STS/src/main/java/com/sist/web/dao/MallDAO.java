package com.sist.web.dao;

import java.util.ArrayList;
import java.util.List;

import com.sist.web.vo.Items;
import com.sist.web.vo.MyCart;
import com.sist.web.vo.OrderList;
import com.sist.web.vo.Review;

public interface MallDAO {

	public ArrayList<Items> getItems(int ipg, String f, String q);
		
	public ArrayList<Items> getNewItems(int ipg, String f, String q);

	public ArrayList<Items> getMainItems(int ipg, String f, String q);
	
	public int itemNoCount(String f,String q);
	
	public int itemUpload(Items i);
	
	public int itemDel(String itemNo);
	
	public int superDel(String itemNo);
	
	public int superDel2(String itemNo);

	public Items itemDetail(String itemNo);
	
	public int itemPurchase(String mid, String itemNo, String itemName, String address, String itemPrice, String quantity, String total);
	
	public int StockDown(String itemNo, String quantity);
	
	public MyCart itemsFromCart(String itemNo, String mid);
	
	public ArrayList<MyCart> viewCart(String mid);
	
	public int addCart(Items i, String mid, String itemNo, String quantity);
	
	public int addMoretoCart(String mid, String itemNo, String quantity);
	
	public int delCart(String itemNo);
	
	public int addReview(String itemNo, String mid, Review r);
	
	public List<Review> getReviews(String itemNo);
	
	public int DelReview(String reviewNo);

	public ArrayList<OrderList> getMyOrders(String mid);
	
	public ArrayList<OrderList> adminOrderList();
	
	public ArrayList<Items> searchItems(String f1, String f2, String column, String f3);
	
	
	
	
	
}
