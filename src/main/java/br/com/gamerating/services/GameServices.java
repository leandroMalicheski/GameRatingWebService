package br.com.gamerating.services;

import java.util.ArrayList;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gamerating.bean.Game;
import br.com.gamerating.bean.User;
import br.com.gamerating.bean.Util;
import br.com.gamerating.dao.GameDAO;
import br.com.gamerating.dao.GameHistoryDAO;
import br.com.gamerating.dao.impl.GameDAOImpl;
import br.com.gamerating.dao.impl.GameHistoryDAOImpl;
import br.com.gamerating.dao.impl.UserDAOImpl;

@RestController
public class GameServices {
	GameDAO gameDAO = GameDAOImpl.getInstance();
	GameHistoryDAO gameHistoryDAO = GameHistoryDAOImpl.getInstance();
	
	@RequestMapping(value="/listGamesByName")
    public ArrayList<Game> listGamesByName(@RequestParam(value="search") String search) {
		search.toLowerCase();
		search = WordUtils.capitalize(search);
		return gameDAO.searchByName(search);
    }

	@RequestMapping(value="/addVisitedTime")
    public void addVisitedTime(@RequestParam(value="gameId") String id, @RequestParam(value="userId") String userLogin) {
		User user = UserDAOImpl.getInstance().getUserByID(Long.valueOf(userLogin));
		gameDAO.addVisitedTime(id,user.getLogin());
	}
	@RequestMapping(value="/getJogoById")
    public Game getJogoById(@RequestParam(value="id") String id) {
		return gameDAO.getGameById(id);		
	}
	
	@RequestMapping(value="/updateVisibility", method=RequestMethod.POST)
    public Game updateVisibility(@RequestBody Game game) {
		if(game.getIsVisible()){
			game.setIsVisible(false);
			gameDAO.updateVisibility(game);
		}else{
			game.setIsVisible(true);
			gameDAO.updateVisibility(game);
		}		
		return game;
	}
	
	@RequestMapping(value="/updateGame", method=RequestMethod.POST)
	public void updateGame(@RequestBody Game game) {
		String userResponsible = UserDAOImpl.getInstance().getUserByID(new Long(game.getUserTempId())).getLogin();
		gameHistoryDAO.addEditInfo(game, gameDAO.getGameById(String.valueOf(game.getId())), userResponsible);
		gameDAO.update(game);
	}
	
	@RequestMapping(value="/getRateInformation")
    public Game getRateInformation(@RequestParam(value="userId") long userId, @RequestParam(value="gameId") long gameId) {
		return gameDAO.getRateInformation(userId,gameId);
    }
	
	@RequestMapping(value="/addRate", method=RequestMethod.POST)
	public void addGameRate(@RequestBody Game game) {
		gameDAO.addRate(game);
	}
	
	@RequestMapping(value="/updateRate", method=RequestMethod.POST)
	public void updateGameRate(@RequestBody Game game) {
		gameDAO.updateRate(game);
	}
	@RequestMapping(value="/addGame", method=RequestMethod.POST)
	public void addGame(@RequestBody Game game) {
		game.setRatingMedio(Util.calcularRateMedio(game));
		gameDAO.addGame(game);
		gameDAO.addRate(gameDAO.getGameIdByName(game.getName()),game);
	}
}
