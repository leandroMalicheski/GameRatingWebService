package br.com.gamerating.services;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gamerating.dao.GameDAO;
import br.com.gamerating.dao.GameHistoryDAO;
import br.com.gamerating.dao.impl.GameDAOImpl;
import br.com.gamerating.dao.impl.GameHistoryDAOImpl;
import br.com.gamerating.vo.GameHistory;

@RestController
public class RelatorioServices {
	GameDAO gameDAO = GameDAOImpl.getInstance();
	GameHistoryDAO gameHistoryDAO = GameHistoryDAOImpl.getInstance();
	
	@RequestMapping(value="/listRelatorioTopicosJogo")
    public ArrayList<GameHistory> getRelatorioTopicosJogo() {
		return gameHistoryDAO.getRelatorioTopicosJogo();
    }
}
