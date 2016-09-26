package br.com.gamerating.bean;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.RandomStringUtils;

import br.com.gamerating.dao.GameDAO;
import br.com.gamerating.dao.impl.ConfigurationDAOImpl;
import br.com.gamerating.dao.impl.GameDAOImpl;

public class Util {
	static Util instance;
	public static final String PASSWORD_KEY = "as45u5hdais23he";
	public static final String SALT_KEY = "feacbc02a3a697b0";

	private String passwordGenerated;
	private String passwordEncrypted;
	private String reputation;

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
		GameDAO gameDao = GameDAOImpl.getInstance();
		ArrayList<Game> gameList = gameDao.getRateInformationByGame(game.getId());
		float audioMedio = media(prepareRate("audio", gameList),gameList.size());
		float diversaoMedio = media(prepareRate("diversao", gameList),gameList.size());
		float imersaoMedio = media(prepareRate("imersao", gameList),gameList.size());
		float jogabilidadeMedio = media(prepareRate("jogabilidade", gameList),gameList.size());
		
		RateConfiguration rateConfiguration = ConfigurationDAOImpl.getInstance().getRateConfiguration();
		
		float participacelAudio = calcularContribuicao(audioMedio, rateConfiguration.getSound());
		float participacelDiversao = calcularContribuicao(diversaoMedio, rateConfiguration.getFun());
		float participacelImersao = calcularContribuicao(imersaoMedio, rateConfiguration.getImmersion());
		float participacelJogabilidade = calcularContribuicao(jogabilidadeMedio, rateConfiguration.getJogability());
		
		float partTotalAudio = calcularContribuicaoTotal(audioMedio,participacelAudio);
		float partTotalDiversao = calcularContribuicaoTotal(diversaoMedio,participacelDiversao);
		float partTotalImersao = calcularContribuicaoTotal(imersaoMedio,participacelImersao);
		float partTotalJogabilidade = calcularContribuicaoTotal(jogabilidadeMedio,participacelJogabilidade);
		
		float total = partTotalAudio + partTotalDiversao + partTotalImersao + partTotalJogabilidade;
		float pesoTotal = rateConfiguration.getSound() + rateConfiguration.getFun() + rateConfiguration.getImmersion() + rateConfiguration.getJogability();
		
		float oneStar = pesoTotal/5;
		float twoStar = oneStar*2;
		float threeStar = oneStar*3;
		float fourStar = oneStar*4;
		
		if(total <= oneStar){
			return 1;
		}else if(total <= twoStar){
			return 2;
		}else if(total <= threeStar){
			return 3;
		}else if(total <= fourStar){
			return 4;
		}else{
			return 5;
		}
	}

	private static float calcularContribuicaoTotal(float audioMedio, float participacelAudio) {
		return audioMedio*participacelAudio;
	}

	private static float calcularContribuicao(float categoria, float peso) {
		return peso/categoria;
	}
	
	private static float media(float somaTotal, float totalItens) {
		return somaTotal/totalItens;
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
	
	public static void sendEmail(String senha, User user) {

		final String username = "gameratingbrasil@gmail.com";
		final String password = "123qwe!@#QWE";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(user.getEmail()));
			message.setSubject("Reset de Senha");
			message.setText(" Caro "+user.getLogin()+" ,"
				+ "\n\n Recebemos a sua solicitação de Alteração de senha. \n \n "+
					"Sua nova senha é: "+ senha +". \n\n No responda a este email por gentileza!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
