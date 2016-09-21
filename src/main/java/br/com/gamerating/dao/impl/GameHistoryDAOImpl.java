package br.com.gamerating.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.gamerating.dao.GameHistoryDAO;
import br.com.gamerating.dao.connection.ConnectionDAO;
import br.com.gamerating.vo.GameHistory;

public class GameHistoryDAOImpl implements GameHistoryDAO {
	private static final String INSERT_GAME_HISTORY = "INSERT INTO GAMEHISTORY(VISITEDTIMES,GAMEID) VALUES(0,?)";
	private static final String SELECT_GAME_HISTORY = "SELECT * FROM GAMEHISTORY WHERE GAMEID=?";
	private static final String UPDATE_VISITEDTIMES = "UPDATE GAMEHISTORY SET VISITEDTIMES=?,VISITEDDATE=?,USERVISITED=? WHERE GAMEID=?";
	private static final String SELECT_GAME_TOPICS = "SELECT G.NAME , COUNT(T.ID) AS TOPICS FROM GAME AS G,TOPIC AS T WHERE G.ID=T.GAMEID GROUP BY G.ID";
	private static final String SELECT_GAME_COMMENTS = "SELECT G.NAME , COUNT(C.ID) AS COMMENTS FROM GAME AS G,TOPIC AS T, COMMENT AS C WHERE G.ID=T.GAMEID AND T.ID=C.TOPICID GROUP BY G.ID";
	private static final String SELECT_GAME_LAST_VISITED_DATE = "SELECT G.NAME, GH.VISITEDDATE, GH.USERVISITED FROM GAMEHISTORY AS GH, GAME AS G WHERE G.ID=GH.GAMEID ORDER BY GH.VISITEDDATE DESC";
	
	public static GameHistoryDAOImpl instance = null;
	private Connection conn;
	
	public static GameHistoryDAOImpl getInstance(){
		if(instance == null){
			instance = new GameHistoryDAOImpl();
		}
		return instance;
	}

	@Override
	public void addHistory(long id) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(INSERT_GAME_HISTORY);
			preparedStatement.setLong(1, id);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void addVisitedTime(String id, String userLogin) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		GameHistory gameHistory = selectGameHistoryById(id);
		int visitedTimes = gameHistory.getVisitedTimes()+1;
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

	private GameHistory selectGameHistoryById(String id) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		GameHistory gameHistoryVo = new GameHistory();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_GAME_HISTORY);
			preparedStatement.setString(1, id);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				gameHistoryVo.setGameId(result.getLong("GAMEID"));
				gameHistoryVo.setVisitedTimes(result.getInt("VISITEDTIMES"));
			}
			return gameHistoryVo;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return gameHistoryVo;
		}
	}

	@Override
	public ArrayList<GameHistory> listNumTopicsGame() {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		ArrayList<GameHistory> gameHistoryVoList = new ArrayList<GameHistory>();
		
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_GAME_TOPICS);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				GameHistory gameHistoryVo = new GameHistory();
				gameHistoryVo.setGameName(result.getString("NAME"));
				gameHistoryVo.setNumTopics(result.getInt("TOPICS"));
				gameHistoryVoList.add(gameHistoryVo);
			}
			return gameHistoryVoList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return gameHistoryVoList;
		}
	}
	
	@Override
	public ArrayList<GameHistory> listNumCommentsGame() {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		ArrayList<GameHistory> gameHistoryVoList = new ArrayList<GameHistory>();
		
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_GAME_COMMENTS);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				GameHistory gameHistoryVo = new GameHistory();
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
	public ArrayList<GameHistory> lastViewGame() {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		ArrayList<GameHistory> gameHistoryVoList = new ArrayList<GameHistory>();
		
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_GAME_LAST_VISITED_DATE);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				GameHistory gameHistoryVo = new GameHistory();
				gameHistoryVo.setGameName(result.getString("NAME"));
				gameHistoryVo.setVisitedDate(result.getString("VISITEDDATE"));
				gameHistoryVo.setUserLogin(result.getString("USERVISITED"));
				gameHistoryVoList.add(gameHistoryVo);
			}
			return gameHistoryVoList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return gameHistoryVoList;
		}
	}
}
