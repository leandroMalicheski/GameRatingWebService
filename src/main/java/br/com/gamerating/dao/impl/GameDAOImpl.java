package br.com.gamerating.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.gamerating.bean.Game;
import br.com.gamerating.dao.GameDAO;
import br.com.gamerating.dao.connection.ConnectionDAO;

public class GameDAOImpl implements GameDAO {
	private static final String SELECT_GAME_BY_NAME_SEARCH = "SELECT ID,NAME FROM GAME WHERE NAME LIKE ?";
	private static final String SELECT_GAME_BY_NAME = "SELECT * FROM GAME WHERE NAME=?";
	private static final String SELECT_GAME_BY_ID = "SELECT * FROM GAME WHERE ID=? AND REMOVED=0";	
	private static final String SELECT_GAME_RATE = "SELECT * FROM GAME AS G,RATE AS R WHERE G.ID = R.GAMEID AND R.USERID = ? AND R.GAMEID = ?";	
	private static final String SELECT_GAME_RATE_BY_GAME = "SELECT * FROM GAME WHERE GAMEID = ?";	
	private static final String UPDATE_GAME_HIDE_FLAG = "UPDATE GAME SET VISIBLE=? WHERE ID=?";
	private static final String UPDATE_GAME = "UPDATE GAME SET NAME=?,DESCRIPTION=?,LAUNCHDATE=?,PLATAFORMS=?,DEVELOPERS=?,RATINGMEDIO=? WHERE ID=?";
	private static final String UPDATE_GAME_RATE = "UPDATE RATE SET JOGABILITY=?,FUN=?,SOUND=?,IMMERSION=? WHERE USERID=? AND GAMEID=?";
	private static final String INSERT_GAME_RATE = "INSERT INTO RATE(USERID,GAMEID,JOGABILITY,FUN,SOUND,IMMERSION) VALUES(?,?,?,?,?,?)";
	private static final String INSERT_GAME = "INSERT INTO GAME(NAME,DESCRIPTION,LAUNCHDATE,PLATAFORMS,DEVELOPERS,RATINGMEDIO,VISIBLE,REMOVED,CREATEDATE) VALUES(?,?,?,?,?,?,0,0,?)";
	public static GameDAOImpl instance;
	private Connection conn;
	
	public static GameDAOImpl getInstance(){
		if(instance == null){
			instance = new GameDAOImpl();
		}
		return instance;
	}
	
	@Override
	public ArrayList<Game> searchByName(String pattern) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		String patternPrepered = "%" + pattern + "%";
		ArrayList<Game> gamesList = new ArrayList<Game>();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_GAME_BY_NAME_SEARCH);
			preparedStatement.setString(1, patternPrepered);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				Game gameTemp = new Game();
				gameTemp.setId(result.getLong("ID"));
				gameTemp.setName(result.getString("NAME"));
				gamesList.add(gameTemp);
			}
			return gamesList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return gamesList;
		}
	}

	@Override
	public Game getGameById(String id) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		Game game = new Game();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_GAME_BY_ID);
			preparedStatement.setString(1, id);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				game.setId(result.getLong("ID"));
				game.setName(result.getString("NAME"));
				game.setDescription(result.getString("DESCRIPTION"));
				game.setLaunchDate(result.getString("LAUNCHDATE"));
				game.setPlatforms(result.getString("PLATAFORMS"));
				game.setDevs(result.getString("DEVELOPERS"));
				game.setRatingMedio(result.getInt("RATINGMEDIO"));
				int visibility = result.getInt("VISIBLE");
				if(visibility == 0) {
					game.setIsVisible(true);
				}else{
					game.setIsVisible(false);
				}
			}
			return game;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return game;
		}
	}

	@Override
	public void updateVisibility(Game game) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(UPDATE_GAME_HIDE_FLAG);
			int visibility = 0;
			if(!game.getIsVisible()){
				visibility = 1;
			}
			preparedStatement.setInt(1, visibility);
			preparedStatement.setLong(2,game.getId());
			preparedStatement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Game game) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(UPDATE_GAME);
			preparedStatement.setString(1, game.getName());
			preparedStatement.setString(2, game.getDescription());
			preparedStatement.setString(3, game.getLaunchDate());
			preparedStatement.setString(4, game.getPlatforms());
			preparedStatement.setString(5, game.getDevs());
			preparedStatement.setInt(6, game.getRatingMedio());
			preparedStatement.setLong(7,game.getId());
			preparedStatement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public Game getRateInformation(long userId, long gameId) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		Game game = new Game();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_GAME_RATE);
			preparedStatement.setLong(1, userId);
			preparedStatement.setLong(2, gameId);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				game.setId(result.getLong("ID"));
				game.setName(result.getString("NAME"));
				game.setRatingJogabilidade(result.getInt("JOGABILITY"));
				game.setRatingMedio(result.getInt("RATINGMEDIO"));
				game.setRatingDiversao(result.getInt("FUN"));
				game.setRatingAudio(result.getInt("SOUND"));
				game.setRatingImersao(result.getInt("IMMERSION"));
				int visibility = result.getInt("VISIBLE");
				if(visibility == 0) {
					game.setIsVisible(true);
				}else{
					game.setIsVisible(false);
				}
			}
			return game;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return game;
		}
	}

	@Override
	public void addRate(long id, Game game) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(INSERT_GAME_RATE);
			preparedStatement.setInt(1, game.getUserTempId());
			preparedStatement.setLong(2, id);
			preparedStatement.setInt(3, game.getRatingJogabilidade());
			preparedStatement.setInt(4, game.getRatingDiversao());
			preparedStatement.setInt(5, game.getRatingAudio());
			preparedStatement.setInt(6, game.getRatingImersao());
			
			preparedStatement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void addRate(Game game) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(INSERT_GAME_RATE);
			preparedStatement.setInt(1, game.getUserTempId());
			preparedStatement.setLong(2, game.getId());
			preparedStatement.setInt(3, game.getRatingJogabilidade());
			preparedStatement.setInt(4, game.getRatingDiversao());
			preparedStatement.setInt(5, game.getRatingAudio());
			preparedStatement.setInt(6, game.getRatingImersao());
			
			preparedStatement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateRate(Game game) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(UPDATE_GAME_RATE);
			preparedStatement.setInt(1, game.getRatingJogabilidade());
			preparedStatement.setInt(2, game.getRatingDiversao());
			preparedStatement.setInt(3, game.getRatingAudio());
			preparedStatement.setInt(4, game.getRatingImersao());
			preparedStatement.setInt(5, game.getUserTempId());
			preparedStatement.setLong(6, game.getId());
			preparedStatement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addGame(Game game) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(INSERT_GAME);
			preparedStatement.setString(1, game.getName());
			preparedStatement.setString(2, game.getDescription());
			preparedStatement.setString(3, game.getLaunchDate());
			preparedStatement.setString(4, game.getPlatforms());
			preparedStatement.setString(5, game.getDevs());
			preparedStatement.setInt(6, game.getRatingMedio());
			preparedStatement.setDate(7, new Date(Calendar.getInstance().getTimeInMillis())); 
			preparedStatement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Game> getRateInformationByGame(long id) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		ArrayList<Game> gameList = new ArrayList<Game>();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_GAME_RATE_BY_GAME);
			preparedStatement.setLong(1, id);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				Game gameTemp = new Game();
				gameTemp.setId(result.getLong("GAMEID"));
				gameTemp.setRatingJogabilidade(result.getInt("JOGABILITY"));
				gameTemp.setRatingDiversao(result.getInt("FUN"));
				gameTemp.setRatingAudio(result.getInt("SOUND"));
				gameTemp.setRatingImersao(result.getInt("IMMERSION"));
				gameList.add(gameTemp);
			}
			return gameList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return gameList;
		}
	}

	@Override
	public long getGameIdByName(String name) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		long id = 0;
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_GAME_BY_NAME);
			preparedStatement.setString(1, name);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				id = result.getLong("ID");
			}
			return id;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return id;
		}
	}
}
