package br.com.gamerating.dao;

import java.util.ArrayList;

import br.com.gamerating.vo.TopicHistory;

public interface TopicHistoryDAO {

	void addHistory(long id);
	public void addVisitedTime(String userLogin, String id);
	ArrayList<TopicHistory> listNumViewsTopic();
	ArrayList<TopicHistory> lastViewTopic();

}
