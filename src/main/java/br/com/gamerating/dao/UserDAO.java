package br.com.gamerating.dao;

import java.util.ArrayList;

import br.com.gamerating.bean.User;

public interface UserDAO {

	public ArrayList<User> searchByName(String search);
	public User login(User user);
	public User userLoginValidation(String login);
	public User userDataValidation(User user);
	public User getUserByID(Long id);
	public String checkReputation(String profileId, String userId);
	public void add(User user);
	public void updatePasswordByLogin(User user);
	public void updateDisableStatus(User user);
	public void updateTopics(User user);
	public void updateComments(User user);
	public void updateUserProfile(User user);
	public void updateBlockStatus(User user);
	public void updateToLike(String profileId, String userId);
	public void updateToDislike(String profileId, String userId);
	public void addLike(String profileId, String userId);
	public void addDislike(String profileId, String userId);
	public void updateUserReputation(User userProfile);
	public void updateUser(User user);
}
