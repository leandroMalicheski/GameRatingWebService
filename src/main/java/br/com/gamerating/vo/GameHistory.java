package br.com.gamerating.vo;

public class GameHistory {
	long gameId;
	int visitedTimes;
	String gameName;
	
	public long getGameId() {
		return gameId;
	}
	public void setGameId(long gameId) {
		this.gameId = gameId;
	}
	public int getVisitedTimes() {
		return visitedTimes;
	}
	public void setVisitedTimes(int visitedTimes) {
		this.visitedTimes = visitedTimes;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
}
