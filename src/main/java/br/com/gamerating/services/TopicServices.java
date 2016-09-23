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
import br.com.gamerating.dao.CommentHistoryDAO;
import br.com.gamerating.dao.TopicDAO;
import br.com.gamerating.dao.TopicHistoryDAO;
import br.com.gamerating.dao.UserDAO;
import br.com.gamerating.dao.impl.CommentHistoryDAOImpl;
import br.com.gamerating.dao.impl.TopicDAOImpl;
import br.com.gamerating.dao.impl.TopicHistoryDAOImpl;
import br.com.gamerating.dao.impl.UserDAOImpl;

@RestController
public class TopicServices {
	TopicDAO topicDAO = TopicDAOImpl.getInstance();	
	UserDAO userDao = UserDAOImpl.getInstance();
	TopicHistoryDAO topicHistoryDAO = TopicHistoryDAOImpl.getInstance();
	CommentHistoryDAO commentHistoryDAO = CommentHistoryDAOImpl.getInstance();
	
	@RequestMapping(value="/listHideTopics")
	public ArrayList<Topic> listHideTopics() {
		return topicDAO.listHideTopics();
    }
	
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
	public ArrayList<Comment> getCommentByTopicUserId(@RequestParam(value="userId") String userId, @RequestParam(value="topicId") String topicId) {
		return topicDAO.getUserCommentedTopics(userId,topicId);
	}
	
	@RequestMapping(value="/getTopicById")
	public Topic getTopicById(@RequestParam(value="topicId") String id, @RequestParam(value="userId") String userId) {
		User user = UserDAOImpl.getInstance().getUserByID(Long.valueOf(userId));
		topicDAO.addVisitedTime(user.getLogin(),id);
		return topicDAO.getTopicById(id);
    }
	
	@RequestMapping(value="/getTopicByIdEdit")
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
	
	@RequestMapping(value="/updateComment", method=RequestMethod.POST)
	public void updateComment(@RequestBody Comment comment) {
		commentHistoryDAO.addEditInfo(comment,topicDAO.getCommentById(String.valueOf(comment.getId())));
		topicDAO.updateComment(comment);
	}
	
	@RequestMapping(value="/updateTopic", method=RequestMethod.POST)
	public void updateTopic(@RequestBody Topic topic) {
		String userResponsible = userDao.getUserByID(Long.valueOf(topic.getUserId())).getLogin();
		topicHistoryDAO.addEditInfo(topic, topicDAO.getTopicById(String.valueOf(topic.getId())),userResponsible);
		topicDAO.updateTopic(topic);
	}
	
	@RequestMapping(value="/removeTopic", method=RequestMethod.POST)
	public void removeTopic(@RequestBody Topic topic) {
		topicDAO.removeTopic(topic);
	}
	@RequestMapping(value="/removeComment", method=RequestMethod.POST)
	public void removeComment(@RequestBody Comment comment) {
		topicDAO.removeComment(comment);
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

	@RequestMapping(value="/updateTopicsVisibleStatus", method=RequestMethod.POST)
    public ArrayList<Topic> updateTopicsVisibleStatus(@RequestBody ArrayList<Topic> topicList) {
		ArrayList<Topic> returnList = new ArrayList<Topic>();
		for (Topic topic : topicList) {
			if(topic.isChecked()){
				topic.setVisible(true);
				topicDAO.updateVisibility(topic);
			}else{
				returnList.add(topic);
			}
		}
		return returnList; 
    }
	@RequestMapping(value="/updateCommetsVisibleStatus", method=RequestMethod.POST)
	public ArrayList<Comment> updateCommetsVisibleStatus(@RequestBody ArrayList<Comment> commentList) {
		ArrayList<Comment> returnList = new ArrayList<Comment>();
		for (Comment comment : commentList) {
			if(comment.isChecked()){
				comment.setVisible(true);
				topicDAO.updateCommentVisibility(comment);
			}else{
				returnList.add(comment);
			}
		}
		return returnList; 
	}
	
	
	@RequestMapping(value="/getHideCommentsTopics")
	public ArrayList<Topic> getHideCommentsTopics() {
		ArrayList<Comment> commentList = topicDAO.getHideComments();
		ArrayList<Topic> topicList = new ArrayList<Topic>();
		for(Comment comment : commentList){
			Topic topicTemp = topicDAO.getTopicById(String.valueOf(comment.getTopicId()));
			topicList.add(topicTemp);			
		}
		return topicList;
    }
	@RequestMapping(value="/getHideCommentsTopicsByTopicId")
	public ArrayList<Comment> getHideCommentsTopicsByTopicId(@RequestParam(value="id") String id) {
		return topicDAO.getHideCommentsTopicsByTopicId(id);
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
	
	@RequestMapping(value="/updateTopicVisibility", method=RequestMethod.POST)
    public Topic updateVisibility(@RequestBody Topic topic) {
		if(topic.isVisible()){
			topic.setVisible(false);
			topicDAO.updateVisibility(topic);
		}else{
			topic.setVisible(true);
			topicDAO.updateVisibility(topic);
		}		
		return topic;
	}
	@RequestMapping(value="/updateCommentVisibility", method=RequestMethod.POST)
	public Comment updateVisibility(@RequestBody Comment comment) {
		if(comment.isVisible()){
			comment.setVisible(false);
			topicDAO.updateCommentVisibility(comment);
		}else{
			comment.setVisible(true);
			topicDAO.updateCommentVisibility(comment);
		}		
		return comment;
	}
}
