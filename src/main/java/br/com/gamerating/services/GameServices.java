package br.com.gamerating.services;

import java.util.ArrayList;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gamerating.bean.Game;
import br.com.gamerating.dao.GameDAO;
import br.com.gamerating.dao.impl.GameDAOImpl;

@RestController
public class GameServices {
	GameDAO gameDAO = GameDAOImpl.getInstance();
	
	@RequestMapping(value="/listGamesByName")
    public ArrayList<Game> listGamesByName(@RequestParam(value="search") String search) {
		search.toLowerCase();
		search = WordUtils.capitalize(search);
		return gameDAO.searchByName(search);
    }

	@RequestMapping(value="/getJogoById")
    public Game getJogoById(@RequestParam(value="id") String id) {
		return gameDAO.getGameById(id);		
	}
	
	@RequestMapping(value="/hideGame", method=RequestMethod.POST)
    public Game hideGame(@RequestBody Game game) {
		if(game.getIsVisible()){
			game.setIsVisible(false);
			gameDAO.hideGame(game);
		}else{
			game.setIsVisible(true);
			gameDAO.hideGame(game);
		}		
		return game;
	}
	
	@RequestMapping(value="/updateGame", method=RequestMethod.POST)
	public void updateGame(@RequestBody Game game) {
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
	
	
}
