package br.com.gamerating.services;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gamerating.bean.User;

@RestController
public class UserServices {

	@RequestMapping("/login")
    public User login(@RequestParam(value="user") String user) {
		//http://localhost:8080/login?user={login:leandro,password=123}		
		return User.login(user, user);
    }
}
