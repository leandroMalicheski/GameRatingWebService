package br.com.gamerating.dao;

import java.util.ArrayList;

import br.com.gamerating.bean.Comment;
import br.com.gamerating.bean.Topic;

public interface TopicDAO {

	public ArrayList<Topic> getTopicsByGameId(String id);
	public ArrayList<Topic> getTopicByUserId(String id);
	public ArrayList<Topic> listHideTopics();
	public ArrayList<Topic> listNumViewsTopic();
	public ArrayList<Topic> lastViewTopic();
	public void add(Topic topic);
	public void addComment(Comment comment);
	public ArrayList<Comment> getCommentsByTopicId(String id);
	public ArrayList<Comment> getCommentsByUserId(String id);
	public ArrayList<Comment> getUserCommentedTopics(String id);
	public ArrayList<Comment> getUserCommentedTopics(String userId, String topicId);
	public ArrayList<Comment> getHideComments();
	public ArrayList<Comment> getHideCommentsTopicsByTopicId(String id);
	public Comment getCommentById(String id);
	public Topic getTopicById(String id);
	public void addVisitedTime(String login, String id);
	public void updateCloseStatus(Topic topic);
	public void removeTopic(Topic topic);
	public void updateTopic(Topic topic);
	public void updateVisibility(Topic topic);
	public void removeComment(Comment comment);
	public void updateComment(Comment comment);
	public void updateCommentVisibility(Comment comment);

}
