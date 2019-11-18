package com.sist.web.dao;

import java.util.ArrayList;

import com.sist.web.vo.Event;

public interface EventDAO {
	
	public ArrayList<Event> getDates();
	
	public int addEvent(Event event);
	
	public int delEvent(String seq);
}
