package br.com.gamerating.services;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gamerating.bean.User;
import br.com.gamerating.bean.Util;
import br.com.gamerating.dao.UserDAO;
import br.com.gamerating.dao.impl.UserDAOImpl;

@RestController
public class UserServices {
	UserDAO userDao = UserDAOImpl.getInstance();
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
    public User login(@RequestBody User user) {
		User usuario = userDao.login(user);
		return usuario;
    }
	
	@RequestMapping(value="/addUser", method=RequestMethod.POST)
    public void addUser(@RequestBody User user) {
		userDao.add(user);
    }
	
	@RequestMapping(value="/updateUserPassword", method=RequestMethod.POST, produces = "application/json")
	public Util updateUserPassword(@RequestBody User user) {
		String newPassword = Util.getInstance().generatePassword();
		user.setPassword(newPassword);
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
	
	@RequestMapping(value="/disableUser", method=RequestMethod.POST)
    public User disableUser(@RequestBody User user) {
		if(user.isVisible()){
			user.setVisible(false);
			userDao.disableUser(user);
		}else{
			user.setVisible(true);
			userDao.disableUser(user);
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
	
	@RequestMapping(value="/resetUserPassword")
    public void resetUserPassword(@RequestParam(value="login") String login) {
		User user = new User();
		user.setLogin(login);
		user.setPassword(Util.getInstance().generatePassword());
		userDao.updatePasswordByLogin(user);
		
		//TODO: EnviarEmail
	}
	
	@RequestMapping(value="/blockUser", method=RequestMethod.POST)
    public User blockUser(@RequestBody User user) {
		if(user.isBlocked()){
			user.setBlocked(false);
			userDao.blockUser(user);
		}else{
			user.setBlocked(true);
			userDao.blockUser(user);
		}		
		return user;
    }
}
