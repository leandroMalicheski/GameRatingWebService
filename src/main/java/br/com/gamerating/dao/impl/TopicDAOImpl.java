package br.com.gamerating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.gamerating.bean.Comment;
import br.com.gamerating.bean.Topic;
import br.com.gamerating.dao.TopicDAO;
import br.com.gamerating.dao.connection.ConnectionDAO;

public class TopicDAOImpl implements TopicDAO{
	
	private static final String SELECT_TOPIC_BY_GAME = "SELECT ID,TITLE FROM TOPIC WHERE GAMEID=?";
	private static final String SELECT_TOPIC_BY_ID = "SELECT * FROM TOPIC WHERE ID=?";
	private static final String SELECT_TOPIC_BY_USER = "SELECT * FROM TOPIC WHERE USERID=?";
	private static final String SELECT_COMMENT_BY_USER = "SELECT * FROM COMMENT WHERE USERID=?";
	private static final String SELECT_COMMENT_BY_TOPIC = "SELECT C.BODY,U.LOGIN,C.USERID FROM COMMENT AS C,USER AS U WHERE C.USERID = U.ID AND TOPICID=? AND VISIBLE=0";
	private static final String INSERT_TOPIC = "INSERT INTO TOPIC(TITLE,BODY,CLOSED,VISIBLE,BLOCKED,USERID,GAMEID) VALUES(?,?,0,0,0,?,?)";
	private static final String INSERT_COMMENT = "INSERT INTO COMMENT(BODY,VISIBLE,USERID,TOPICID) VALUES(?,0,?,?)";
	public static TopicDAOImpl instance;
	private Connection conn;
	
	public static TopicDAOImpl getInstance(){
		if(instance == null){
			instance = new TopicDAOImpl();
		}
		return instance;
	}

	public ArrayList<Topic> getTopicsByGameId(String id) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		ArrayList<Topic> topicList = new ArrayList<Topic>();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_TOPIC_BY_GAME);
			preparedStatement.setString(1, id);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				Topic topicTemp = new Topic();
				topicTemp.setId(result.getLong("ID"));
				topicTemp.setTitle(result.getString("TITLE"));
				topicList.add(topicTemp);
			}
			return topicList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return topicList;
		}
	}

	public void add(Topic topic) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(INSERT_TOPIC);
			preparedStatement.setString(1, topic.getTitle());
			preparedStatement.setString(2, topic.getBody());
			preparedStatement.setLong(3, topic.getUserId());
			preparedStatement.setLong(4, topic.getGameId());
			preparedStatement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Topic> getTopicByUserId(String id) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		ArrayList<Topic> topicList = new ArrayList<Topic>();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_TOPIC_BY_USER);
			preparedStatement.setString(1, id);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				Topic topicTemp = new Topic();
				topicTemp.setId(result.getLong("ID"));
				topicTemp.setGameId(result.getLong("GAMEID"));
				topicTemp.setUserId(result.getInt("USERID"));
				topicTemp.setBody(result.getString("BODY"));
				topicTemp.setTitle(result.getString("TITLE"));
				topicList.add(topicTemp);
			}
			return topicList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return topicList;
		}
	}
	
	public ArrayList<Comment> getCommentsByTopicId(String id) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_COMMENT_BY_TOPIC);
			preparedStatement.setString(1, id);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				Comment commentTemp = new Comment();
				commentTemp.setUserId(result.getInt("USERID"));
				commentTemp.setBody(result.getString("BODY"));
				commentTemp.setUser(result.getString("LOGIN"));
				commentList.add(commentTemp);
			}
			return commentList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return commentList;
		}
	}

	public Topic getTopicById(String id) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		Topic topic = new Topic();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_TOPIC_BY_ID);
			preparedStatement.setString(1, id);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				topic.setId(result.getLong("ID"));
				topic.setTitle(result.getString("TITLE"));
				topic.setBody(result.getString("BODY"));
			}
			return topic;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return topic;
		}
	}

	@Override
	public void addComment(Comment comment) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(INSERT_COMMENT);
			preparedStatement.setString(1, comment.getBody());
			preparedStatement.setLong(2, comment.getUserId());
			preparedStatement.setLong(3, comment.getTopicId());
			preparedStatement.execute();						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Comment> getCommentsByUserId(String id) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_COMMENT_BY_USER);
			preparedStatement.setString(1, id);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				Comment commentTemp = new Comment();
				commentTemp.setTopicId(result.getInt("TOPICID"));
				commentTemp.setBody(result.getString("BODY"));
				commentList.add(commentTemp);
			}
			return commentList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return commentList;
		}
	}

}
