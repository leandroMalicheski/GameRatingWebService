package br.com.gamerating.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.gamerating.bean.Comment;
import br.com.gamerating.bean.Topic;
import br.com.gamerating.dao.TopicDAO;
import br.com.gamerating.dao.connection.ConnectionDAO;

public class TopicDAOImpl implements TopicDAO{
	
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
			preparedStatement.setInt(1, Integer.valueOf(id));
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
			Date dataCriacao = new Date(Calendar.getInstance().getTimeInMillis());
			preparedStatement.setDate(5, dataCriacao);			
			preparedStatement.setString(6, topic.getImg());
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
			preparedStatement.setInt(1, Integer.valueOf(id));
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
			preparedStatement.setInt(1, Integer.valueOf(id));
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				Comment commentTemp = new Comment();
				commentTemp.setUserId(result.getInt("USERID"));
				commentTemp.setBody(result.getString("BODY"));
				commentTemp.setUser(result.getString("LOGIN"));
				commentTemp.setImg(result.getString("IMAGE"));
				commentTemp.setId(result.getLong("ID"));
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
			preparedStatement.setInt(1, Integer.valueOf(id));
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				topic.setId(result.getLong("ID"));
				topic.setTitle(result.getString("TITLE"));
				topic.setBody(result.getString("BODY"));
				topic.setVisitedTimes(result.getInt("VISITEDTIMES"));
				topic.setCreatedDate(result.getDate("CREATIONDATE"));
				topic.setImg(result.getString("IMAGE"));
				int close = result.getInt("CLOSED");
				if(close == 1){
					topic.setClosed(true);
				}else{
					topic.setClosed(false);
				}
				int visibility = result.getInt("VISIBLE");
				if(visibility == 1){
					topic.setVisible(false);
				}else{
					topic.setVisible(true);
				}
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
			preparedStatement.setString(4, comment.getImg());
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
			preparedStatement.setInt(1, Integer.valueOf(id));
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

	@Override
	public void updateCloseStatus(Topic topic) {
		int visibility = 0;
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(UPDATE_TOPIC_CLOSED_STATUS);
			if (topic.isClosed()){
				visibility = 1;
			}
			preparedStatement.setInt(1,visibility);
			preparedStatement.setInt(2,(int)topic.getId());
			preparedStatement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeTopic(Topic topic) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(UPDATE_TOPIC_REMOVE_STATUS);
			preparedStatement.setInt(1,(int)topic.getId());
			preparedStatement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateTopic(Topic topic) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(UPDATE_TOPIC);
			preparedStatement.setString(1,topic.getTitle());
			preparedStatement.setString(2,topic.getBody());
			preparedStatement.setString(3,topic.getImg());
			preparedStatement.setInt(4,(int)topic.getId());
			preparedStatement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Comment> getUserCommentedTopics(String id) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_COMMENT_UNCLOSED_TOPICS_BY_USER);
			preparedStatement.setInt(1, Integer.valueOf(id));
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				Comment commentTemp = new Comment();
				commentTemp.setTopicId(result.getLong("TOPICID"));
				commentList.add(commentTemp);
			}
			return commentList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return commentList;
		}
	}

	@Override
	public ArrayList<Comment> getUserCommentedTopics(String userId, String topicId) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_COMMENT_UNCLOSED_TOPICS_BY_USER_TOPIC);
			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, topicId);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				Comment commentTemp = new Comment();
				commentTemp.setId(result.getLong("ID"));
				commentTemp.setBody(result.getString("BODY"));
				commentTemp.setImg(result.getString("IMAGE"));
				commentList.add(commentTemp);
			}
			return commentList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return commentList;
		}
	}

	@Override
	public Comment getCommentById(String id) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		Comment comment = new Comment();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_COMMENT_BY_ID);
			preparedStatement.setInt(1, Integer.valueOf(id));
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				comment.setId(result.getInt("ID"));
				comment.setBody(result.getString("BODY"));
				comment.setImg(result.getString("IMAGE"));
				comment.setUserImg(result.getString("USERIMAGE"));
				int visibility = result.getInt("VISIBLE");
				if(visibility == 0){
					comment.setVisible(true);
				}else{
					comment.setVisible(false);
				}
			}
			return comment;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return comment	;
		}
	}

	@Override
	public void removeComment(Comment comment) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(UPDATE_COMMENT_REMOVE_STATUS);
			preparedStatement.setInt(1,(int)comment.getId());
			preparedStatement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateComment(Comment comment) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement;
			if(comment.getBody() != null){
				preparedStatement = this.conn.prepareStatement(UPDATE_COMMENT);
				preparedStatement.setString(1,comment.getBody());
			}else{
				preparedStatement = this.conn.prepareStatement(UPDATE_COMMENT_IMG);
				preparedStatement.setString(1,comment.getImg());
			}
			
			preparedStatement.setInt(2,(int)comment.getId());				
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Topic> listHideTopics() {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		ArrayList<Topic> topicList = new ArrayList<Topic>();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_HIDE_TOPICS);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				Topic topicTemp = new Topic();
				topicTemp.setId(result.getLong("ID"));
				topicTemp.setTitle(result.getString("TITLE"));
				int visibility = result.getInt("VISIBLE");
				if(visibility == 0){
					topicTemp.setVisible(true);					
				}else{
					topicTemp.setVisible(false);
				}
				topicList.add(topicTemp);
			}
			return topicList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return topicList;
		}
	}

	@Override
	public ArrayList<Comment> getHideComments() {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_HIDE_COMMENTS);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				Comment commentTemp = new Comment();
				commentTemp.setId(result.getLong("ID"));
				commentTemp.setTopicId(result.getInt("TOPICID"));
				commentList.add(commentTemp);
			}
			return commentList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return commentList;
		}
	}

	@Override
	public void updateVisibility(Topic topic) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(UPDATE_TOPIC_HIDE_FLAG);
			int visibility = 0;
			if(!topic.isVisible()){
				visibility = 1;
			}
			preparedStatement.setInt(1, visibility);
			preparedStatement.setInt(2,(int)topic.getId());
			preparedStatement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateCommentVisibility(Comment comment) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(UPDATE_COMMENT_HIDE_FLAG);
			int visibility = 0;
			if(!comment.isVisible()){
				visibility = 1;
			}
			preparedStatement.setInt(1, visibility);
			preparedStatement.setInt(2,(int)comment.getId());
			preparedStatement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Comment> getHideCommentsTopicsByTopicId(String id) {
		
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_HIDE_COMMENTS_BY_TOPIC);
			preparedStatement.setInt(1, Integer.valueOf(id));
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				Comment commentTemp = new Comment();
				commentTemp.setId(result.getLong("ID"));
				commentTemp.setBody(result.getString("BODY"));
				commentTemp.setImg(result.getString("IMAGE"));
				int visibility = result.getInt("VISIBLE");
				if(visibility == 0){
					commentTemp.setVisible(true);					
				}else{
					commentTemp.setVisible(false);
				}
				commentList.add(commentTemp);
			}
			return commentList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return commentList;
		}
	}

	@Override
	public void addVisitedTime(String login, String id) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		Topic topic = getTopicById(id);
		int visitedTimes = topic.getVisitedTimes()+1;
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(UPDATE_VISITEDTIMES);
			preparedStatement.setInt(1, visitedTimes);
			preparedStatement.setDate(2, new Date(Calendar.getInstance().getTimeInMillis()));
			preparedStatement.setString(3, login);
			preparedStatement.setInt(4, Integer.valueOf(id));
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public ArrayList<Topic> listNumViewsTopic() {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		ArrayList<Topic> topicList = new ArrayList<Topic>();
		
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_TOPIC);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				Topic topic = new Topic();
				topic.setTitle(result.getString("TITLE"));
				topic.setVisitedTimes(result.getInt("VISITEDTIMES"));
				topicList.add(topic);
			}
			return topicList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return topicList;
		}
	}

	@Override
	public ArrayList<Topic> lastViewTopic() {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		ArrayList<Topic> topicList = new ArrayList<Topic>();
		
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_TOPIC);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				Topic topic = new Topic();
				topic.setTitle(result.getString("TITLE"));
				topic.setVisitedDate(result.getString("VISITEDDATE"));
				topic.setUserLogin(result.getString("USERVISITED"));
				topicList.add(topic);
			}
			return topicList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return topicList;
		}
	}

	private static final String SELECT_TOPIC_BY_GAME = "SELECT ID,TITLE FROM TOPIC WHERE GAMEID=? AND VISIBLE=0 AND DELETED=0 ORDER BY CREATIONDATE DESC";
	private static final String SELECT_TOPIC_BY_ID = "SELECT * FROM TOPIC WHERE ID=? AND VISIBLE=0 AND DELETED=0 ORDER BY CREATIONDATE DESC";
	private static final String SELECT_TOPIC_BY_USER = "SELECT * FROM TOPIC WHERE USERID=? AND VISIBLE=0 AND DELETED=0 ORDER BY CREATIONDATE DESC";
	private static final String SELECT_TOPIC = "SELECT TITLE,VISITEDTIMES,VISITEDDATE,USERVISITED FROM TOPIC WHERE VISIBLE=0 AND DELETED=0 ORDER BY VISITEDDATE DESC";
	
	private static final String SELECT_HIDE_TOPICS = "SELECT * FROM TOPIC WHERE VISIBLE=1 AND DELETED=0 ORDER BY CREATIONDATE DESC";
	private static final String SELECT_HIDE_COMMENTS = "SELECT * FROM TCOMMENT WHERE VISIBLE=1 AND DELETED=0";
	private static final String SELECT_HIDE_COMMENTS_BY_TOPIC = "SELECT * FROM TCOMMENT WHERE VISIBLE=1 AND DELETED=0 AND TOPICID=?";
	
	private static final String SELECT_COMMENT_BY_USER = "SELECT * FROM TCOMMENT WHERE USERID=? AND DELETED=0";
	private static final String SELECT_COMMENT_BY_ID = "SELECT C.ID,C.BODY,C.VISIBLE,C.IMAGE,U.IMAGE AS USERIMAGE FROM TCOMMENT AS C, TUSER AS U WHERE C.ID=? AND C.DELETED=0 AND C.USERID=U.ID";
	private static final String SELECT_COMMENT_UNCLOSED_TOPICS_BY_USER = "SELECT C.TOPICID FROM TCOMMENT AS C,TOPIC AS T WHERE C.TOPICID = T.ID AND USERID=? AND T.VISIBLE=0 AND C.DELETED=0 AND T.DELETED=0";
	private static final String SELECT_COMMENT_UNCLOSED_TOPICS_BY_USER_TOPIC = "SELECT C.ID,C.BODY,C.IMAGE FROM TCOMMENT AS C,TOPIC AS T WHERE C.TOPICID = T.ID AND USERID=? AND TOPICID=? AND T.VISIBLE=0 AND C.DELETED=0 AND T.DELETED=0";
	private static final String SELECT_COMMENT_BY_TOPIC = "SELECT C.BODY,U.LOGIN,C.USERID,C.ID, C.IMAGE FROM TCOMMENT AS C,TUSER AS U WHERE C.USERID = U.ID AND TOPICID=? AND C.VISIBLE=0 AND C.DELETED=0";
	
	private static final String INSERT_TOPIC = "INSERT INTO TOPIC(TITLE,BODY,CLOSED,VISIBLE,VISITEDTIMES,USERID,GAMEID,DELETED,CREATIONDATE,IMAGE) VALUES(?,?,0,0,0,?,?,0,?,?)";
	private static final String INSERT_COMMENT = "INSERT INTO TCOMMENT(BODY,VISIBLE,USERID,TOPICID,DELETED,IMAGE) VALUES(?,0,?,?,0,?)";
	
	private static final String UPDATE_VISITEDTIMES = "UPDATE TOPIC SET VISITEDTIMES=?,VISITEDDATE=?,USERVISITED=? WHERE ID=?";
	private static final String UPDATE_TOPIC_CLOSED_STATUS = "UPDATE TOPIC SET CLOSED=? WHERE ID=?";
	private static final String UPDATE_TOPIC_REMOVE_STATUS = "UPDATE TOPIC SET DELETED=1 WHERE ID=?";
	private static final String UPDATE_COMMENT_REMOVE_STATUS = "UPDATE TCOMMENT SET DELETED=1 WHERE ID=?";
	private static final String UPDATE_COMMENT = "UPDATE TCOMMENT SET BODY=? WHERE ID=?";
	private static final String UPDATE_COMMENT_IMG = "UPDATE TCOMMENT SET IMAGE=? WHERE ID=?";
	private static final String UPDATE_COMMENT_HIDE_FLAG = "UPDATE TCOMMENT SET VISIBLE=? WHERE ID=?";
	private static final String UPDATE_TOPIC = "UPDATE TOPIC SET TITLE=?, BODY=?, IMAGE=? WHERE ID=?";
	private static final String UPDATE_TOPIC_HIDE_FLAG = "UPDATE TOPIC SET VISIBLE=? WHERE ID=?";
}
