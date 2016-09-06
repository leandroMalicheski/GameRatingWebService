package br.com.gamerating.services;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.gamerating.bean.User;

@RestController
public class UserServices {

	@RequestMapping(value="/login", method=RequestMethod.POST)
    public User login(@RequestBody User user) {
		return User.login(user.getLogin(),user.getPassword());
    }
}
