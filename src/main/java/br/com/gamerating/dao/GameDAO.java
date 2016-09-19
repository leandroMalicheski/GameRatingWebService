package br.com.gamerating.dao;

import java.util.ArrayList;

import br.com.gamerating.bean.Game;

public interface GameDAO {
	
	public ArrayList<Game> searchByName(String pattern);
	public Game getGameById(String id);
	public void updateVisibility(Game game);
	public void update(Game game);
	public Game getRateInformation(long userId, long gameId);
	public void addRate(long l, Game game);
	public void updateRate(Game game);
	public void addGame(Game game);
	public ArrayList<Game> getRateInformationByGame(long id);
	public long getGameIdByName(String name);
	public void addRate(Game game);
}
