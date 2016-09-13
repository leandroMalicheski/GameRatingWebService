package br.com.gamerating.dao;

import java.util.ArrayList;

import br.com.gamerating.bean.Comment;
import br.com.gamerating.bean.Topic;

public interface TopicDAO {

	ArrayList<Topic> getTopicsByGameId(String id);
	void add(Topic topic);
	ArrayList<Comment> getCommentsByTopicId(String id);
	Topic getTopicById(String id);
	void addComment(Comment comment);
	ArrayList<Topic> getTopicByUserId(String id);
	ArrayList<Comment> getCommentsByUserId(String id);

}
