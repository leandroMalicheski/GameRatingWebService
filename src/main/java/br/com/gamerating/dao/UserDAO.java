package br.com.gamerating.dao;

import br.com.gamerating.bean.User;

public interface UserDAO {

	public User login(User user);
	public void add(User user);
	public void updatePasswordByLogin(User user);
	public User userLoginValidation(String login);
	public User userDataValidation(User user);
	public void disableUser(User user);
	public User getUserByID(Long id);
	public void updateTopics(User user);
	public void updateComments(User user);
	public void updateUserProfile(User user);
	public void blockUser(User user);
}
