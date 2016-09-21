package br.com.gamerating.bean;

public class Game {
	long id;

	String name;
	String description;
	String launchDate;
	String platforms;
	String devs;
	String visitedDate;
	String userLogin;
	
	boolean isVisible;
	int ratingMedio;
	int visitedTimes;
	
	int userTempId;
	int ratingJogabilidade;
	int ratingDiversao;
	int ratingAudio;
	int ratingImersao;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLaunchDate() {
		return launchDate;
	}
	public void setLaunchDate(String launchDate) {
		this.launchDate = launchDate;
	}
	public String getPlatforms() {
		return platforms;
	}
	public void setPlatforms(String platforms) {
		this.platforms = platforms;
	}
	public String getDevs() {
		return devs;
	}
	public void setDevs(String devs) {
		this.devs = devs;
	}
	public boolean getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	public int getRatingMedio() {
		return ratingMedio;
	}
	public void setRatingMedio(int ratingMedio) {
		this.ratingMedio = ratingMedio;
	}
	public int getRatingJogabilidade() {
		return ratingJogabilidade;
	}
	public void setRatingJogabilidade(int ratingJogabilidade) {
		this.ratingJogabilidade = ratingJogabilidade;
	}
	public int getRatingDiversao() {
		return ratingDiversao;
	}
	public void setRatingDiversao(int ratingDiversao) {
		this.ratingDiversao = ratingDiversao;
	}
	public int getRatingAudio() {
		return ratingAudio;
	}
	public void setRatingAudio(int ratingAudio) {
		this.ratingAudio = ratingAudio;
	}
	public int getRatingImersao() {
		return ratingImersao;
	}
	public void setRatingImersao(int ratingImersao) {
		this.ratingImersao = ratingImersao;
	}
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	public int getUserTempId() {
		return userTempId;
	}
	public void setUserTempId(int userTempId) {
		this.userTempId = userTempId;
	}
	public int getVisitedTimes() {
		return visitedTimes;
	}
	public void setVisitedTimes(int visitedTimes) {
		this.visitedTimes = visitedTimes;
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
