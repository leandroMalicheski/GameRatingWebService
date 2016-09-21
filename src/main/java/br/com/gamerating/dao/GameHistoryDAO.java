package br.com.gamerating.dao;

import java.util.ArrayList;

import br.com.gamerating.vo.GameHistory;

public interface GameHistoryDAO {

	void addHistory(long id);
	void addVisitedTime(String id, String userLogin);
	ArrayList<GameHistory> listNumTopicsGame();
	ArrayList<GameHistory> listNumCommentsGame();
	ArrayList<GameHistory> lastViewGame();

}
