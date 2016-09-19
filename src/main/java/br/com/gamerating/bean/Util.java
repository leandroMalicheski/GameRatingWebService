package br.com.gamerating.bean;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.commons.lang3.RandomStringUtils;

import br.com.gamerating.dao.GameDAO;
import br.com.gamerating.dao.impl.GameDAOImpl;

public class Util {
	private String passwordGenerated;
	private String passwordEncrypted;
	private String reputation;
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
	
	public String encryptPassword(String password){
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

	public String getPasswordEncrypted() {
		return passwordEncrypted;
	}

	public void setPasswordEncrypted(String passwordEncrypted) {
		this.passwordEncrypted = passwordEncrypted;
	}

	public static int calcularRateMedio(Game game) {
		int audio = game.getRatingAudio();
		int diversao = game.getRatingDiversao();
		int imersao = game.getRatingImersao();
		int jogabilidade = game.getRatingJogabilidade();
		int total = 0;
		int retorno = 0;
		if(game.getRatingMedio() == 0){
			total = audio + diversao + imersao + jogabilidade;
			retorno = total/4;
		}else{
			GameDAO gameDao = GameDAOImpl.getInstance();
			ArrayList<Game> gameList = gameDao.getRateInformationByGame(game.getId());
			audio = prepareRate("audio", gameList);
			diversao = prepareRate("diversao", gameList);
			imersao = prepareRate("imersao", gameList);
			jogabilidade = prepareRate("jogabilidade", gameList);
			total = audio + diversao + imersao + jogabilidade;
			retorno = total/4;
		}
		return retorno;
	}

	private static int prepareRate(String string, ArrayList<Game> gameList) {
		int total = 0;
		for(Game game : gameList){
			switch (string) {
			case "audio":
				total = total + game.getRatingAudio();
				break;
			case "diversao":
				total = total + game.getRatingDiversao();
				break;
			case "imersao":
				total = total + game.getRatingImersao();
				break;
			case "jogabilidade":
				total = total + game.getRatingJogabilidade();
				break;
			}
		}
		return total/gameList.size();
	}
}
