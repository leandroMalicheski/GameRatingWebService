package br.com.gamerating.dao;

import java.util.ArrayList;

import br.com.gamerating.bean.Comment;
import br.com.gamerating.bean.Topic;

public interface TopicDAO {

	public ArrayList<Topic> getTopicsByGameId(String id);
	public void add(Topic topic);
	public ArrayList<Comment> getCommentsByTopicId(String id);
	public Topic getTopicById(String id);
	public void addComment(Comment comment);
	public ArrayList<Topic> getTopicByUserId(String id);
	public ArrayList<Comment> getCommentsByUserId(String id);
	public void updateCloseStatus(Topic topic);
	public void removeTopic(Topic topic);
	public void updateTopic(Topic topic);
	public ArrayList<Comment> getUserCommentedTopics(String id);
	public ArrayList<Comment> getUserCommentedTopics(String userId, String topicId);
	public Comment getCommentById(String id);

}
