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
import br.com.gamerating.vo.GameVo;

public class GameDAOImpl implements GameDAO {
	
	public static GameDAOImpl instance;
	private Connection conn;
	
	public static GameDAOImpl getInstance(){
		if(instance == null){
			instance = new GameDAOImpl();
		}
		return instance;
	}
	
	@Override
	public ArrayList<Game> searchByNameAdm(String pattern) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		String patternPrepered = "%" + pattern + "%";
		ArrayList<Game> gamesList = new ArrayList<Game>();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_GAME_BY_NAME_SEARCH_ADM);
			preparedStatement.setString(1, patternPrepered);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				Game gameTemp = new Game();
				gameTemp.setId(result.getLong("ID"));
				gameTemp.setName(result.getString("NAME"));
				gameTemp.setImg(result.getString("IMAGE"));
				gamesList.add(gameTemp);
			}
			return gamesList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return gamesList;
		}
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
				game.setVisitedTimes(result.getInt("VISITEDTIMES"));
				game.setImg(result.getString("IMAGE"));
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
				game.setImg(result.getString("IMAGE"));
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
	
	@Override
	public void addVisitedTime(String id, String userLogin) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		Game game = getGameById(id);
		int visitedTimes = game.getVisitedTimes()+1;
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
	
	@Override
	public ArrayList<GameVo> listNumTopicsGame() {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		ArrayList<GameVo> gameList = new ArrayList<GameVo>();
		
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_GAME_TOPICS);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				GameVo gameVo = new GameVo();
				gameVo.setGameName(result.getString("NAME"));
				gameVo.setNumTopics(result.getInt("TOPICS"));
				gameList.add(gameVo);
			}
			return gameList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return gameList;
		}
	}
	
	@Override
	public ArrayList<GameVo> listNumCommentsGame() {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		ArrayList<GameVo> gameHistoryVoList = new ArrayList<GameVo>();
		
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_GAME_COMMENTS);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				GameVo gameHistoryVo = new GameVo();
				gameHistoryVo.setGameName(result.getString("NAME"));
				gameHistoryVo.setNumComments(result.getInt("COMMENTS"));
				gameHistoryVoList.add(gameHistoryVo);
			}
			return gameHistoryVoList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return gameHistoryVoList;
		}
	}
	
	@Override
	public ArrayList<Game> lastViewGame() {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		ArrayList<Game> gameList = new ArrayList<Game>();
		
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_GAME_LAST_VISITED_DATE);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				Game game = new Game();
				game.setName(result.getString("NAME"));
				game.setVisitedDate(result.getString("VISITEDDATE"));
				game.setUserLogin(result.getString("USERVISITED"));
				gameList.add(game);
			}
			return gameList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return gameList;
		}
	}
	
	private static final String SELECT_GAME_BY_NAME_SEARCH = "SELECT ID,NAME FROM GAME WHERE VISIBLE=0 AND REMOVED=0 AND NAME LIKE ?";
	private static final String SELECT_GAME_BY_NAME_SEARCH_ADM = "SELECT ID,NAME,IMAGE FROM GAME WHERE REMOVED=0 AND NAME LIKE ?";
	private static final String SELECT_GAME_BY_NAME = "SELECT * FROM GAME WHERE VISIBLE=0 AND REMOVED=0 AND NAME=?";
	private static final String SELECT_GAME_BY_ID = "SELECT * FROM GAME WHERE REMOVED=0 AND ID=?";	
	
	private static final String SELECT_GAME_RATE = "SELECT * FROM GAME AS G,RATE AS R WHERE G.ID = R.GAMEID AND R.USERID = ? AND R.GAMEID = ?";	
	private static final String SELECT_GAME_RATE_BY_GAME = "SELECT * FROM RATE WHERE GAMEID = ?";	
	private static final String SELECT_GAME_TOPICS = "SELECT G.NAME , COUNT(T.ID) AS TOPICS FROM GAME AS G,TOPIC AS T WHERE G.ID=T.GAMEID GROUP BY G.ID";
	private static final String SELECT_GAME_COMMENTS = "SELECT G.NAME , COUNT(C.ID) AS COMMENTS FROM GAME AS G,TOPIC AS T, COMMENT AS C WHERE G.ID=T.GAMEID AND T.ID=C.TOPICID GROUP BY G.ID";
	private static final String SELECT_GAME_LAST_VISITED_DATE = "SELECT NAME, VISITEDDATE, USERVISITED FROM GAME WHERE VISIBLE=0 AND REMOVED=0 ORDER BY VISITEDDATE DESC";
	
	private static final String UPDATE_GAME_HIDE_FLAG = "UPDATE GAME SET VISIBLE=? WHERE ID=?";
	private static final String UPDATE_GAME = "UPDATE GAME SET NAME=?,DESCRIPTION=?,LAUNCHDATE=?,PLATAFORMS=?,DEVELOPERS=?,RATINGMEDIO=? WHERE ID=?";
	private static final String UPDATE_GAME_RATE = "UPDATE RATE SET JOGABILITY=?,FUN=?,SOUND=?,IMMERSION=? WHERE USERID=? AND GAMEID=?";
	private static final String UPDATE_VISITEDTIMES = "UPDATE GAME SET VISITEDTIMES=?,VISITEDDATE=?,USERVISITED=? WHERE ID=?";
	
	private static final String INSERT_GAME_RATE = "INSERT INTO RATE(USERID,GAMEID,JOGABILITY,FUN,SOUND,IMMERSION) VALUES(?,?,?,?,?,?)";
	private static final String INSERT_GAME = "INSERT INTO GAME(NAME,DESCRIPTION,LAUNCHDATE,PLATAFORMS,DEVELOPERS,RATINGMEDIO,VISIBLE,REMOVED,CREATEDATE,VISITEDTIMES) VALUES(?,?,?,?,?,?,0,0,?,0)";
}
