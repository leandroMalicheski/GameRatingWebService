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
		user.setPassword(Util.getInstance().encryptPassword(user.getPassword()));
		return userDao.login(user);
    }
	
	@RequestMapping(value="/addUser", method=RequestMethod.POST)
    public void addUser(@RequestBody User user) {
		user.setPassword(Util.getInstance().encryptPassword(user.getPassword()));
		userDao.add(user);
    }
	
	@RequestMapping(value="/generateUserPassword", method=RequestMethod.POST, produces = "application/json")
	public Util generateUserPassword(@RequestBody User user) {
		String newPassword = Util.getInstance().generatePassword();
		user.setPassword(Util.getInstance().encryptPassword(newPassword));
		userDao.updatePasswordByLogin(user);
		return Util.getInstance();
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
	
	@RequestMapping(value="/updateUser", method=RequestMethod.POST)
    public void updateUser(@RequestBody User user) {
		userDao.updateUser(user);
    }
	
	@RequestMapping(value="/encryptPassword", method=RequestMethod.POST)
    public Util encryptPassword(@RequestBody Util util) {
		util.setPasswordEncrypted(util.encryptPassword(util.getPasswordEncrypted()));
		return util;
	}
	
	@RequestMapping(value="/resetUserPassword")
    public void resetUserPassword(@RequestParam(value="login") String login) {
		User user = new User();
		user.setLogin(login);
		String password = Util.getInstance().generatePassword();
		user.setPassword(Util.getInstance().encryptPassword(password));
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
