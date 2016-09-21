package br.com.gamerating.dao;

import java.util.ArrayList;

import br.com.gamerating.bean.Game;
import br.com.gamerating.vo.GameVo;

public interface GameDAO {
	
	public ArrayList<Game> searchByName(String pattern);
	public ArrayList<Game> getRateInformationByGame(long id);
	public ArrayList<Game> lastViewGame();
	public ArrayList<GameVo> listNumTopicsGame();
	public ArrayList<GameVo> listNumCommentsGame();
	public Game getGameById(String id);
	public Game getRateInformation(long userId, long gameId);
	public long getGameIdByName(String name);
	public void updateVisibility(Game game);
	public void update(Game game);
	public void addRate(long l, Game game);
	public void updateRate(Game game);
	public void addGame(Game game);
	public void addRate(Game game);
	public void addVisitedTime(String id, String login);
}
