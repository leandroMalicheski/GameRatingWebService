package br.com.gamerating.bean;

public class User {

	long id;
	String name;
	String email;
	String login;
	String password;
	String passwordConfirm;
	String passwordTip;
	int profile;
	int likes;
	int dislikes;
	boolean isBlocked;
	boolean isVisible;
	
	public static User login(String login, String senha){
		if(login.equalsIgnoreCase("leandro") && senha.equalsIgnoreCase("123")){
			User usuario = new User();
			usuario.setId(1);
			usuario.setLogin(login);
			usuario.setPassword(senha);
			usuario.setName("LeadroMCDS");
			return usuario;
		}else{
			return null;			
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordTip() {
		return passwordTip;
	}

	public void setPasswordTip(String passwordTip) {
		this.passwordTip = passwordTip;
	}

	public int getProfile() {
		return profile;
	}

	public void setProfile(int profile) {
		this.profile = profile;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	
}
