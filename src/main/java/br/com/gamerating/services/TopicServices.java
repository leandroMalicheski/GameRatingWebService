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
	@RequestMapping(value="/getTopicByUserId")
	public ArrayList<Topic> getTopicByUserId(@RequestParam(value="id") String id) {
		return topicDAO.getTopicByUserId(id);
	}
	
	@RequestMapping(value="/getCommentsByTopicId")
	public ArrayList<Comment> getCommentsByTopicId(@RequestParam(value="id") String id) {
		return topicDAO.getCommentsByTopicId(id);
	}
	
	@RequestMapping(value="/getCommentById")
	public Comment getCommentById(@RequestParam(value="id") String id) {
		return topicDAO.getCommentById(id);
	}
	
	@RequestMapping(value="/getUserCommentedTopics")
	public ArrayList<Topic> getUserCommentedTopics(@RequestParam(value="id") String id) {
		ArrayList<Comment> commentsList = topicDAO.getUserCommentedTopics(id);
		ArrayList<Topic> topicsList = new ArrayList<Topic>();
		for (Comment comment : commentsList) {
			topicsList.add(topicDAO.getTopicById(String.valueOf(comment.getTopicId())));
		}
		return filterCommentListProcess(topicsList);
	}
	
	@RequestMapping(value="/getCommentByTopicUserId")
	public ArrayList<Topic> getCommentByTopicUserId(@RequestParam(value="userId") String userId, @RequestParam(value="topicId") String topicId) {
		ArrayList<Comment> commentsList = topicDAO.getUserCommentedTopics(userId,topicId);
		ArrayList<Topic> topicsList = new ArrayList<Topic>();
		for (Comment comment : commentsList) {
			topicsList.add(topicDAO.getTopicById(String.valueOf(comment.getTopicId())));
		}
		return filterCommentListProcess(topicsList);
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
	
	@RequestMapping(value="/updateTopic", method=RequestMethod.POST)
	public void updateTopic(@RequestBody Topic topic) {
		topicDAO.updateTopic(topic);
	}
	
	@RequestMapping(value="/removeTopic", method=RequestMethod.POST)
	public void removeTopic(@RequestBody Topic topic) {
		topicDAO.removeTopic(topic);
	}
	
	@RequestMapping(value="/updateCloseStatus", method=RequestMethod.POST)
    public Topic updateCloseStatus(@RequestBody Topic topic) {
		if(topic.isClosed()){
			topic.setClosed(false);
			topicDAO.updateCloseStatus(topic);
		}else{
			topic.setClosed(true);
			topicDAO.updateCloseStatus(topic);
		}
		return topic;
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
	
	private ArrayList<Topic> filterCommentListProcess(ArrayList<Topic> topicList) {
		ArrayList<Topic> filteredList = new ArrayList<Topic>();
		filteredList.add(topicList.get(0));
		for (int i = 1; i < topicList.size(); i++) {
			Topic lastTopic = topicList.get(i-1); 
			if(lastTopic.getId() != topicList.get(i).getId()){
				filteredList.add(topicList.get(i));
			}
		}
		return filteredList;
	}
}
