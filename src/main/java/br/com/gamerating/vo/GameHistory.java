package br.com.gamerating.vo;

public class GameHistory {
	long gameId;
	int visitedTimes;
	String gameName;
	int numTopics;
	int numComments;
	String visitedDate;
	String userLogin;
	
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
	public int getNumTopics() {
		return numTopics;
	}
	public void setNumTopics(int numTopics) {
		this.numTopics = numTopics;
	}
	public int getNumComments() {
		return numComments;
	}
	public void setNumComments(int numComments) {
		this.numComments = numComments;
	}
	public String getVisitedDate() {
		return visitedDate;
	}
	public void setVisitedDate(String visitedDate) {
		this.visitedDate = visitedDate;
	}
	public String getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
}
