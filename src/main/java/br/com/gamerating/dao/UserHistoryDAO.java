package br.com.gamerating.dao;

import br.com.gamerating.bean.User;

public interface UserHistoryDAO {

	void addEditInfo(User newUserInfo, User oldUserInfo);

}
