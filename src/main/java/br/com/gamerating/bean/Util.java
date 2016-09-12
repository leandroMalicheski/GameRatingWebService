package br.com.gamerating.bean;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.RandomStringUtils;

public class Util {
	String passwordGenerated;
	String reputation;
	static Util instance;
	public static final String PASSWORD_KEY = "as45u5hdais23he";
	public static final String SALT_KEY = "feacbc02a3a697b0";

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
	
	public String encriptyPassword(String password){
		MessageDigest algorithm;
		try {
			algorithm = MessageDigest.getInstance("MD5");
			byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));
			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getReputation() {
		return reputation;
	}

	public void setReputation(String reputation) {
		this.reputation = reputation;
	}
}
