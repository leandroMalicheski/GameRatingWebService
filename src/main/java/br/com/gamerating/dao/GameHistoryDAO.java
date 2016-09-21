package br.com.gamerating.dao;

import br.com.gamerating.bean.Game;

public interface GameHistoryDAO {

	void addEditInfo(Game newGameInfo, Game oldGameInfo, String userResponsible);

}
