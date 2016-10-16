package br.com.gamerating.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import br.com.gamerating.bean.Topic;
import br.com.gamerating.dao.TopicHistoryDAO;
import br.com.gamerating.dao.connection.ConnectionDAO;

public class TopicHistoryDAOImpl implements TopicHistoryDAO {

	static TopicHistoryDAOImpl instance;
	private Connection conn;
	
	public static TopicHistoryDAOImpl getInstance() {
		if(instance == null){
			instance = new TopicHistoryDAOImpl();
		}
		return instance;
	}
	
	@Override
	public void addEditInfo(Topic newTopicInfo, Topic oldTopicInfo, String userResponsible) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		String camposAtualizados = checkCamposAtualizados(newTopicInfo,oldTopicInfo);
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(INSERT_TOPIC_HISTORY);
			preparedStatement.setInt(1, (int)newTopicInfo.getId());
			preparedStatement.setString(2, camposAtualizados);
			preparedStatement.setDate(3, new Date(Calendar.getInstance().getTimeInMillis()));
			preparedStatement.setString(4, userResponsible);
			preparedStatement.setString(5, oldTopicInfo.getTitle());
			preparedStatement.setString(6, newTopicInfo.getTitle());
			preparedStatement.setString(7, oldTopicInfo.getBody());
			preparedStatement.setString(8, newTopicInfo.getBody());
			preparedStatement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String checkCamposAtualizados(Topic newTopicInfo, Topic oldTopicInfo) {
		StringBuffer camposAtualizados = new StringBuffer();

		if(!newTopicInfo.getTitle().equalsIgnoreCase(oldTopicInfo.getTitle())){
			camposAtualizados.append(" Title ");
		}
		if(!newTopicInfo.getBody().equalsIgnoreCase(oldTopicInfo.getBody())){
			camposAtualizados.append(" Body ");
		}
		return camposAtualizados.toString();
	}

	private static final String INSERT_TOPIC_HISTORY = "INSERT INTO TOPICHISTORY(TOPICID,CAMPOSATUALIZADOS,CHANGEDDATE,BYUSER,TITLEANTES,TITLEDEPOIS,BODYANTES,BODYDEPOIS) VALUES(?,?,?,?,?,?,?,?)";
}
