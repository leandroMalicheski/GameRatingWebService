package br.com.gamerating.vo;

public class TopicHistory {
	long topicId;
	int visitedTimes;
	String topicTitle;
	String visitedDate;
	String userLogin;
	
	public long getTopicId() {
		return topicId;
	}
	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}
	public int getVisitedTimes() {
		return visitedTimes;
	}
	public void setVisitedTimes(int visitedTimes) {
		this.visitedTimes = visitedTimes;
	}
	public String getTopicTitle() {
		return topicTitle;
	}
	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
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
