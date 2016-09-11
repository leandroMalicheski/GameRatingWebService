package br.com.gamerating.services;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gamerating.bean.Comment;
import br.com.gamerating.bean.Topic;
import br.com.gamerating.bean.User;
import br.com.gamerating.dao.TopicDAO;
import br.com.gamerating.dao.UserDAO;
import br.com.gamerating.dao.impl.TopicDAOImpl;
import br.com.gamerating.dao.impl.UserDAOImpl;

@RestController
public class TopicServices {
	TopicDAO topicDAO = TopicDAOImpl.getInstance();	
	UserDAO userDao = UserDAOImpl.getInstance();

	@RequestMapping(value="/getTopicsByGameId")
	public ArrayList<Topic> getTopicsByGameId(@RequestParam(value="id") String id) {
		return topicDAO.getTopicsByGameId(id);
    }
	
	@RequestMapping(value="/getCommentsByTopicId")
	public ArrayList<Comment> getCommentsByTopicId(@RequestParam(value="id") String id) {
		return topicDAO.getCommentsByTopicId(id);
	}
	
	@RequestMapping(value="/getTopicById")
	public Topic getTopicById(@RequestParam(value="id") String id) {
		return topicDAO.getTopicById(id);
    }
	
	@RequestMapping(value="/addTopic", method=RequestMethod.POST)
    public void addTopic(@RequestBody Topic topic) {
		User user = userDao.getUserByID(Long.valueOf(topic.getUserId()));
		user.setTopics(user.getTopics()+1);
		userDao.updateTopics(user);
		topicDAO.add(topic);
    }
	
	@RequestMapping(value="/addComment", method=RequestMethod.POST)
    public Comment addComment(@RequestBody Comment comment) {
		User user = userDao.getUserByID(Long.valueOf(comment.getUserId()));
		user.setComments(user.getComments()+1);
		userDao.updateComments(user);
		topicDAO.addComment(comment);
		comment.setUser(user.getLogin());
		return comment; 
    }
}
