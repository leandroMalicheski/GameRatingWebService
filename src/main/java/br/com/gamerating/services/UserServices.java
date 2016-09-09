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
		userDao.updatePassword(user);
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
}
