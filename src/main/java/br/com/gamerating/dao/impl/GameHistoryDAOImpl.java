package br.com.gamerating.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import br.com.gamerating.bean.Game;
import br.com.gamerating.dao.GameHistoryDAO;
import br.com.gamerating.dao.connection.ConnectionDAO;

public class GameHistoryDAOImpl implements GameHistoryDAO {
	
	static GameHistoryDAOImpl instance;
	private Connection conn;
	
	public static GameHistoryDAOImpl getInstance() {
		if(instance == null){
			instance = new GameHistoryDAOImpl();
		}
		return instance;
	}
	
	@Override
	public void addEditInfo(Game newGameInfo, Game oldGameInfo, String userResponsible) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		String camposAtualizados = checkCamposAtualizados(newGameInfo,oldGameInfo);
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(INSERT_GAME_HISTORY);
			preparedStatement.setLong(1, newGameInfo.getId());
			preparedStatement.setString(2, camposAtualizados);
			preparedStatement.setDate(3, new Date(Calendar.getInstance().getTimeInMillis()));
			preparedStatement.setString(4, userResponsible);
			preparedStatement.setString(5, oldGameInfo.getName());
			preparedStatement.setString(6, newGameInfo.getName());
			preparedStatement.setString(7, oldGameInfo.getDescription());
			preparedStatement.setString(8, newGameInfo.getDescription());
			preparedStatement.setString(9, oldGameInfo.getLaunchDate());
			preparedStatement.setString(10, newGameInfo.getLaunchDate());
			preparedStatement.setString(11, oldGameInfo.getPlatforms());
			preparedStatement.setString(12, newGameInfo.getPlatforms());
			preparedStatement.setString(13, oldGameInfo.getDevs());
			preparedStatement.setString(14, newGameInfo.getDevs());
			preparedStatement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String checkCamposAtualizados(Game newGameInfo, Game oldGameInfo) {
		StringBuffer camposAtualizados = new StringBuffer();

		if(!newGameInfo.getName().equalsIgnoreCase(oldGameInfo.getName())){
			camposAtualizados.append(" Name ");
		}
		if(!newGameInfo.getDescription().equalsIgnoreCase(oldGameInfo.getDescription())){
			camposAtualizados.append(" Description ");
		}
		if(!newGameInfo.getLaunchDate().equalsIgnoreCase(oldGameInfo.getLaunchDate())){
			camposAtualizados.append(" LaunchDate ");
		}
		if(!newGameInfo.getPlatforms().equalsIgnoreCase(oldGameInfo.getPlatforms())){
			camposAtualizados.append(" Plataforms ");
		}
		if(!newGameInfo.getDevs().equalsIgnoreCase(oldGameInfo.getDevs())){
			camposAtualizados.append(" Devs ");
		}
		
		return camposAtualizados.toString();
	}

	private static final String INSERT_GAME_HISTORY = "INSERT INTO GAMEHISTORY(GAMEID,CAMPOSATUALIZADOS,CHANGEDDATE,BYUSER,GAMENAMEANTES,GAMENAMEDEPOIS,DESCRIPTIONANTES,DESCRIPTIONDEPOIS,LAUNCHDATEANTES,LAUNCHDATEDEPOIS,PLATAFORMSANTES,PLATAFORMSDEPOIS,DEVELOPERSANTES,DEVELOPERSDEPOIS) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
}
