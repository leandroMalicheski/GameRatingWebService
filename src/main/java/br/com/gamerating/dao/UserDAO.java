package br.com.gamerating.dao;

import br.com.gamerating.bean.User;

public interface UserDAO {

	public User login(User user);
	public void add(User user);
	public void updatePassword(User user);
	public User userLoginValidation(String login);
	public User userDataValidation(User user);
}
