package br.com.gamerating.services;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gamerating.bean.Game;
import br.com.gamerating.bean.Topic;
import br.com.gamerating.dao.GameDAO;
import br.com.gamerating.dao.TopicDAO;
import br.com.gamerating.dao.impl.GameDAOImpl;
import br.com.gamerating.dao.impl.TopicDAOImpl;
import br.com.gamerating.vo.GameVo;

@RestController
public class RelatorioServices {
	GameDAO gameDAO = GameDAOImpl.getInstance();
	TopicDAO topicDAO = TopicDAOImpl.getInstance();
	
	@RequestMapping(value="/listNumTopicsGame")
    public ArrayList<GameVo> listNumTopicsGame() {
		return gameDAO.listNumTopicsGame();
    }
	
	@RequestMapping(value="/listNumCommentsGame")
    public ArrayList<GameVo> listNumCommentsGame() {
		return gameDAO.listNumCommentsGame();
    }
	
	@RequestMapping(value="/lastViewGame")
	public ArrayList<Game> lastViewGame() {
		return gameDAO.lastViewGame();
	}
	
	@RequestMapping(value="/listNumViewsTopic")
    public ArrayList<Topic> listNumViewsTopic() {
		return topicDAO.listNumViewsTopic();
    }
	@RequestMapping(value="/lastViewTopic")
	public ArrayList<Topic> lastViewTopic() {
		return topicDAO.lastViewTopic();
	}
	
}
