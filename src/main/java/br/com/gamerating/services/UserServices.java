package br.com.gamerating.services;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gamerating.bean.User;
import br.com.gamerating.bean.Util;
import br.com.gamerating.dao.UserDAO;
import br.com.gamerating.dao.UserHistoryDAO;
import br.com.gamerating.dao.impl.UserDAOImpl;
import br.com.gamerating.dao.impl.UserHistoryDAOImpl;

@RestController
public class UserServices {
	UserDAO userDao = UserDAOImpl.getInstance();
	UserHistoryDAO userHistoryDAO = UserHistoryDAOImpl.getInstance();
	private int tentativa = 0;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
    public User login(@RequestBody User user) {
		if(tentativa < 2){
			user.setPassword(Util.getInstance().encryptPassword(user.getPassword()));
			user = userDao.login(user);
			if(user.getLogin() == null){
				tentativa = tentativa+1;
			}else{
				tentativa = 0;
			}
		}else{
			user = userDao.getUserByLogin(user.getLogin());
			if(!user.isBlocked()){
				userDao.updateBlockStatus(user);				
			}
			user.setLogin(null);
			tentativa = 0;
		}
		
		return user;
    }
	
	@RequestMapping(value="/listUserByName")
    public ArrayList<User> listUserByName(@RequestParam(value="search") String search) {
		return userDao.searchByName(search);
    }
	
	@RequestMapping(value="/addUser", method=RequestMethod.POST)
    public void addUser(@RequestBody User user) {
		user.setPassword(Util.getInstance().encryptPassword(user.getPassword()));
		userDao.add(user);
    }
	
	@RequestMapping(value="/generateUserPassword", method=RequestMethod.POST, produces = "application/json")
	public void generateUserPassword(@RequestBody User user) {
		String newPassword = Util.getInstance().generatePassword();
		user.setPassword(Util.getInstance().encryptPassword(newPassword));
		Util.sendEmail(newPassword, user);
		userDao.updatePasswordByLogin(user);
	}
	
	@RequestMapping(value="/updateUserPassword", method=RequestMethod.POST, produces = "application/json")
	public Util updateUserPassword(@RequestBody User user) {
		user.setPassword(Util.getInstance().encryptPassword(user.getPassword()));
		userDao.updatePasswordByLogin(user);
		return Util.getInstance();
	}
	
	@RequestMapping(value="/userLoginValidation")
    public User userLoginValidation(@RequestParam(value="login") String login) {
		return userDao.userLoginValidation(login);
	}
	
	@RequestMapping(value="/userDataValidation", method=RequestMethod.POST)
    public User userDataValidation(@RequestBody User user) {
		return userDao.userDataValidation(user);
    }
	
	@RequestMapping(value="/updateDisableStatus", method=RequestMethod.POST)
    public User updateDisableStatus(@RequestBody User user) {
		if(user.isVisible()){
			user.setVisible(false);
			userDao.updateDisableStatus(user);
		}else{
			user.setVisible(true);
			userDao.updateDisableStatus(user);
		}		
		return user;
    }
	
	@RequestMapping(value="/getUserById")
    public User getUserById(@RequestParam(value="id") String id) {
		return userDao.getUserByID(Long.valueOf(id));
	}
	
	@RequestMapping(value="/updateUserProfile", method=RequestMethod.POST)
    public User updateUserProfile(@RequestBody User user) {
		if(user.getProfile() == 1){
			user.setProfile(2);
			userDao.updateUserProfile(user);
		}else{
			user.setProfile(1);
			userDao.updateUserProfile(user);
		}		
		return user;
    }
	
	@RequestMapping(value="/updateUser", method=RequestMethod.POST)
    public void updateUser(@RequestBody User user) {
		userHistoryDAO.addEditInfo(user, userDao.getUserByID(user.getId()));
		userDao.updateUser(user);
    }
	
	@RequestMapping(value="/encryptPassword", method=RequestMethod.POST)
    public Util encryptPassword(@RequestBody Util util) {
		util.setPasswordEncrypted(util.encryptPassword(util.getPasswordEncrypted()));
		return util;
	}
	
	@RequestMapping(value="/resetUserPassword")
    public void resetUserPassword(@RequestParam(value="login") String login) {
		User user = userDao.getUserByLogin(login);
		String password = Util.getInstance().generatePassword();
		user.setPassword(Util.getInstance().encryptPassword(password));
		userDao.updatePasswordByLogin(user);
		Util.sendEmail(password, user);
	}
	
	@RequestMapping(value="/updateBlockStatus", method=RequestMethod.POST)
    public User updateBlockStatus(@RequestBody User user) {
		if(user.isBlocked()){
			user.setBlocked(false);
			userDao.updateBlockStatus(user);
		}else{
			user.setBlocked(true);
			userDao.updateBlockStatus(user);
		}		
		return user;
    }
	
	@RequestMapping(value="/checkReputation")
    public Util checkReputation(@RequestParam(value="profileId") String profileId, @RequestParam(value="userId") String userId) {
		Util.getInstance().setReputation(userDao.checkReputation(profileId, userId));
		return Util.getInstance();
	}
	
	@RequestMapping(value="/giveLike")
    public User giveLike(@RequestParam(value="profileId") String profileId, @RequestParam(value="userId") String userId) {
		String reputation = userDao.checkReputation(profileId, userId);
		User userProfile = userDao.getUserByID(Long.valueOf(profileId));
		userProfile.setLikes(userProfile.getLikes()+1);
		
		if(reputation.equals("1")){
			userProfile.setDislikes(userProfile.getDislikes()-1);
			userDao.updateToLike(profileId,userId);
		}else{
			userDao.addLike(profileId,userId);
		}
		userDao.updateUserReputation(userProfile);
		return userProfile;
	}
	@RequestMapping(value="/giveDislike")
	public User giveDislike(@RequestParam(value="profileId") String profileId, @RequestParam(value="userId") String userId) {
		String reputation = userDao.checkReputation(profileId, userId);
		User userProfile = userDao.getUserByID(Long.valueOf(profileId));
		userProfile.setDislikes(userProfile.getDislikes()+1);
		
		if(reputation.equals("0")){
			userProfile.setLikes(userProfile.getLikes()-1);
			userDao.updateToDislike(profileId,userId);
		}else{
			userDao.addDislike(profileId,userId);
		}
		userDao.updateUserReputation(userProfile);
		return userProfile;
	}
}
