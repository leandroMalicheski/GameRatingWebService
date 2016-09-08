package br.com.gamerating.bean;

import org.apache.commons.lang3.RandomStringUtils;

public class Util {
	String passwordGenerated;
	static Util instance;

	public static Util getInstance(){
		if(instance == null){
			instance = new Util();
		}
		return instance;
	}
	
	public String generatePassword() {
		this.passwordGenerated = RandomStringUtils.randomAlphanumeric(8);
		return this.passwordGenerated;
	}

	public String getPasswordGenerated() {
		return passwordGenerated;
	}

	public void setPasswordGenerated(String passwordGenerated) {
		this.passwordGenerated = passwordGenerated;
	}
}
