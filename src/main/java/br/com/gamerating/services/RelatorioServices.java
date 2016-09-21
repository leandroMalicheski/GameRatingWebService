package br.com.gamerating.services;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gamerating.dao.GameDAO;
import br.com.gamerating.dao.GameHistoryDAO;
import br.com.gamerating.dao.TopicHistoryDAO;
import br.com.gamerating.dao.impl.GameDAOImpl;
import br.com.gamerating.dao.impl.GameHistoryDAOImpl;
import br.com.gamerating.dao.impl.TopicHistoryDAOImpl;
import br.com.gamerating.vo.GameHistory;
import br.com.gamerating.vo.TopicHistory;

@RestController
public class RelatorioServices {
	GameDAO gameDAO = GameDAOImpl.getInstance();
	GameHistoryDAO gameHistoryDAO = GameHistoryDAOImpl.getInstance();
	TopicHistoryDAO topicHistoryDAO = TopicHistoryDAOImpl.getInstance();
	
	@RequestMapping(value="/listNumTopicsGame")
    public ArrayList<GameHistory> listNumTopicsGame() {
		return gameHistoryDAO.listNumTopicsGame();
    }
	
	@RequestMapping(value="/listNumCommentsGame")
    public ArrayList<GameHistory> listNumCommentsGame() {
		return gameHistoryDAO.listNumCommentsGame();
    }
	
	@RequestMapping(value="/lastViewGame")
	public ArrayList<GameHistory> lastViewGame() {
		return gameHistoryDAO.lastViewGame();
	}
	
	@RequestMapping(value="/listNumViewsTopic")
    public ArrayList<TopicHistory> listNumViewsTopic() {
		return topicHistoryDAO.listNumViewsTopic();
    }
	@RequestMapping(value="/lastViewTopic")
	public ArrayList<TopicHistory> lastViewTopic() {
		return topicHistoryDAO.lastViewTopic();
	}
	
}
