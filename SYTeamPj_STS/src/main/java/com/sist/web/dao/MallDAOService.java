package com.sist.web.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.sist.web.vo.Items;
import com.sist.web.vo.MyCart;
import com.sist.web.vo.OrderList;
import com.sist.web.vo.Review;

public class MallDAOService implements MallDAO{

	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public ArrayList<Items> getItems(int ipg, String f, String q) {
		MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
		return mlDao.getItems(ipg, f, q);
	}

	@Override
	public ArrayList<Items> getNewItems(int ipg, String f, String q) {
		MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
		return mlDao.getNewItems(ipg, f, q);
	}

	@Override
	public ArrayList<Items> getMainItems(int ipg, String f, String q) {
	    MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
	    return mlDao.getMainItems(ipg, f, q);
	   }
	
	
	@Override
	public int itemNoCount(String f, String q) {
		MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
		return mlDao.itemNoCount(f, q);
	}

	@Override
	public int itemUpload(Items i) {
		MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
		return mlDao.itemUpload(i);
	}

	@Override
	public int itemDel(String itemNo) {
		MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
		return mlDao.itemDel(itemNo);
	}

	@Override
	public Items itemDetail(String itemNo) {
		MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
		return mlDao.itemDetail(itemNo);
	}

	@Override
	public int itemPurchase(String mid, String itemNo, String itemName, String address, String quantity,String itemPrice, String total) {
		MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
		return mlDao.itemPurchase(mid, itemNo, itemName, address, quantity, itemPrice, total);
	}

	@Override
	public MyCart itemsFromCart(String itemNo, String mid) {
		MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
		return mlDao.itemsFromCart(itemNo, mid);
	}

	@Override
	public ArrayList<MyCart> viewCart(String mid) {
		MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
		return mlDao.viewCart(mid);
	}

	@Override
	public int addCart(Items i, String mid, String itemNo, String quantity) {
		MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
		return mlDao.addCart(i, mid, itemNo, quantity);
	}
	
	@Override
	public int addMoretoCart(String mid, String itemNo, String quantity) {
		MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
		return mlDao.addMoretoCart(mid, itemNo, quantity);
	}
	
	@Override
	public int delCart(String itemNo) {
		MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
		return mlDao.delCart(itemNo);
	}
	
	@Override
	public int addReview(String itemNo, String mid, Review r) {
		MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
		return mlDao.addReview(itemNo, mid, r);
	}

	@Override
	public List<Review> getReviews(String itemNo) {
		MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
		return mlDao.getReviews(itemNo);
	}

	@Override
	public int DelReview(String reviewNo) {
		MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
		return mlDao.DelReview(reviewNo);
	}

	@Override
	public ArrayList<OrderList> getMyOrders(String mid) {
		MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
		return mlDao.getMyOrders(mid);
	}

	@Override
	public ArrayList<OrderList> adminOrderList() {
		MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
		return mlDao.adminOrderList();
	}

	@Override
	public int StockDown(String itemNo, String quantity) {
		MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
		return mlDao.StockDown(itemNo, quantity);
	}

	@Override
	public int superDel(String itemNo) {
		MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
		return mlDao.superDel(itemNo);
	}

	@Override
	public int superDel2(String itemNo) {
		MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
		return mlDao.superDel2(itemNo);
	}

	@Override
	public ArrayList<Items> searchItems(String f1, String f2, String column, String f3) {
	    MallDAO mlDao = sqlSession.getMapper(MallDAO.class);
	    return mlDao.searchItems(f1,f2,column,f3);
	}

	
	
	
	
	
}
