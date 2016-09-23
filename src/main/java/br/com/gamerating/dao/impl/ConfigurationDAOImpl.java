package br.com.gamerating.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import br.com.gamerating.bean.RateConfiguration;
import br.com.gamerating.dao.ConfigurationDAO;
import br.com.gamerating.dao.connection.ConnectionDAO;

public class ConfigurationDAOImpl implements ConfigurationDAO {

	public static ConfigurationDAOImpl instance;
	private Connection conn;

	@Override
	public int getDateConfiguration() {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		int days = 0;
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_DATE_CONFIGURATION);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				days = result.getInt("DAYSTOCLOSE");				
			}
			return days;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return days;
		}
	}

	@Override
	public void updateDateConfiguration(String days, String user) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatementUpdate = this.conn.prepareStatement(UPDATE_DATE_CONFIGURATION_STATUS);
			preparedStatementUpdate.setDate(1, new Date(Calendar.getInstance().getTimeInMillis()));
			preparedStatementUpdate.setString(2, user);
			preparedStatementUpdate.execute();
			
			PreparedStatement preparedStatementInsert = this.conn.prepareStatement(INSERT_DATE_CONFIGURATION);
			preparedStatementInsert.setString(1, days);
			preparedStatementInsert.setDate(2, new Date(Calendar.getInstance().getTimeInMillis()));
			preparedStatementInsert.setString(3, user);
			preparedStatementInsert.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public RateConfiguration getRateConfiguration() {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		RateConfiguration configuration = new RateConfiguration();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_RATE_CONFIGURATION);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				configuration.setJogability(result.getInt("JOGABILITY"));				
				configuration.setFun(result.getInt("FUN"));				
				configuration.setSound(result.getInt("SOUND"));				
				configuration.setImmersion((result.getInt("IMMERSION")));				
			}
			return configuration;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return configuration;
		}
	}
	
	@Override
	public void updateRateConfiguration(RateConfiguration rateConfig) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatementUpdate = this.conn.prepareStatement(UPDATE_RATE_CONFIGURATION_STATUS);
			preparedStatementUpdate.setDate(1, new Date(Calendar.getInstance().getTimeInMillis()));
			preparedStatementUpdate.setString(2, rateConfig.getUserLogin());
			preparedStatementUpdate.execute();
			
			PreparedStatement preparedStatementInsert = this.conn.prepareStatement(INSERT_RATE_CONFIGURATION);
			preparedStatementInsert.setInt(1, rateConfig.getJogability());
			preparedStatementInsert.setInt(2, rateConfig.getFun());
			preparedStatementInsert.setInt(3, rateConfig.getSound());
			preparedStatementInsert.setInt(4, rateConfig.getImmersion());
			preparedStatementInsert.setDate(5, new Date(Calendar.getInstance().getTimeInMillis()));
			preparedStatementInsert.setString(6, rateConfig.getUserLogin());
			preparedStatementInsert.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ConfigurationDAO getInstance() {
		if(instance == null){
			instance = new ConfigurationDAOImpl();
		}
		return instance;
	}

	private static final String SELECT_DATE_CONFIGURATION = "SELECT DAYSTOCLOSE FROM DATETOCLOSE WHERE STATUS=0";
	private static final String UPDATE_DATE_CONFIGURATION_STATUS = "UPDATE DATETOCLOSE SET STATUS=1,CLOSEDDATE=?,BYUSER=? WHERE STATUS=0";
	private static final String INSERT_DATE_CONFIGURATION = "INSERT INTO DATETOCLOSE(DAYSTOCLOSE,STATUS,CREATIONDATE,BYUSER) VALUES(?,0,?,?)";
	
	private static final String SELECT_RATE_CONFIGURATION = "SELECT JOGABILITY,FUN,SOUND,IMMERSION FROM RATINGCONFIGURATION WHERE STATUS=0";
	private static final String UPDATE_RATE_CONFIGURATION_STATUS = "UPDATE RATINGCONFIGURATION SET STATUS=1,DELETEDDATE=?,BYUSER=? WHERE STATUS=0";
	private static final String INSERT_RATE_CONFIGURATION = "INSERT INTO RATINGCONFIGURATION(JOGABILITY,FUN,SOUND,IMMERSION,STATUS,CREATIONDATE,BYUSER) VALUES(?,?,?,?,0,?,?)";
}
