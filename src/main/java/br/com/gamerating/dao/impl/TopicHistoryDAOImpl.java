package br.com.gamerating.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.gamerating.dao.TopicHistoryDAO;
import br.com.gamerating.dao.connection.ConnectionDAO;
import br.com.gamerating.vo.TopicHistory;

public class TopicHistoryDAOImpl implements TopicHistoryDAO {

	private static final String INSERT_TOPIC_HISTORY = "INSERT INTO TOPICHISTORY(VISITEDTIMES,TOPICID) VALUES(0,?)";
	private static final String SELECT_TOPIC_HISTORY = "SELECT * FROM TOPICHISTORY WHERE TOPICID=?";
	private static final String SELECT_TOPIC_HISTORY_VIEWS = "SELECT T.TITLE,TH.VISITEDTIMES FROM TOPICHISTORY AS TH, TOPIC T WHERE TH.TOPICID=T.ID AND T.DELETED=0";
	private static final String SELECT_TOPIC_LAST_VISITED_DATE = "SELECT T.TITLE, TH.VISITEDDATE, TH.USERVISITED FROM TOPICHISTORY AS TH, TOPIC AS T WHERE T.ID=TH.TOPICID ORDER BY TH.VISITEDDATE DESC";
	private static final String UPDATE_VISITEDTIMES = "UPDATE TOPICHISTORY SET VISITEDTIMES=?,VISITEDDATE=?,USERVISITED=? WHERE TOPICID=?";
	
	public static TopicHistoryDAOImpl instance = null;
	private Connection conn;
	
	public static TopicHistoryDAOImpl getInstance(){
		if(instance == null){
			instance = new TopicHistoryDAOImpl();
		}
		return instance;
	}
	
	@Override
	public void addHistory(long id) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(INSERT_TOPIC_HISTORY);
			preparedStatement.setLong(1, id);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addVisitedTime(String userLogin, String id) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		TopicHistory topicHistory = selectGameHistoryById(id);
		int visitedTimes = topicHistory.getVisitedTimes()+1;
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(UPDATE_VISITEDTIMES);
			preparedStatement.setInt(1, visitedTimes);
			preparedStatement.setDate(2, new Date(Calendar.getInstance().getTimeInMillis()));
			preparedStatement.setString(3, userLogin);
			preparedStatement.setString(4, id);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private TopicHistory selectGameHistoryById(String id) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		TopicHistory topicHistoryVo = new TopicHistory();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_TOPIC_HISTORY);
			preparedStatement.setString(1, id);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				topicHistoryVo.setTopicId(result.getLong("TOPICID"));
				topicHistoryVo.setVisitedTimes(result.getInt("VISITEDTIMES"));
			}
			return topicHistoryVo;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return topicHistoryVo;
		}
	}

	@Override
	public ArrayList<TopicHistory> listNumViewsTopic() {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		ArrayList<TopicHistory> topicHistoryVoList = new ArrayList<TopicHistory>();
		
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_TOPIC_HISTORY_VIEWS);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				TopicHistory topicHistoryVo = new TopicHistory();
				topicHistoryVo.setTopicTitle(result.getString("TITLE"));
				topicHistoryVo.setVisitedTimes(result.getInt("VISITEDTIMES"));
				topicHistoryVoList.add(topicHistoryVo);
			}
			return topicHistoryVoList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return topicHistoryVoList;
		}
	}

	@Override
	public ArrayList<TopicHistory> lastViewTopic() {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		ArrayList<TopicHistory> topicHistoryVoList = new ArrayList<TopicHistory>();
		
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_TOPIC_LAST_VISITED_DATE);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				TopicHistory topicHistoryVo = new TopicHistory();
				topicHistoryVo.setTopicTitle(result.getString("TITLE"));
				topicHistoryVo.setVisitedDate(result.getString("VISITEDDATE"));
				topicHistoryVo.setUserLogin(result.getString("USERVISITED"));
				topicHistoryVoList.add(topicHistoryVo);
			}
			return topicHistoryVoList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return topicHistoryVoList;
		}
	}

}
