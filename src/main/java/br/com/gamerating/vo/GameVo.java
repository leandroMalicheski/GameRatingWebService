package br.com.gamerating.vo;

public class GameVo {
	
	String gameName;
	int numTopics;
	int numComments;
	
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
}
