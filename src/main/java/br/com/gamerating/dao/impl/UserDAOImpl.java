package br.com.gamerating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.gamerating.bean.User;
import br.com.gamerating.dao.UserDAO;
import br.com.gamerating.dao.connection.ConnectionDAO;

public class UserDAOImpl implements UserDAO {
	
	private static final String LOGIN = "SELECT * FROM USER WHERE LOGIN=? AND PASSWORD=?";
	private static final String LOGIN_VALIDATION = "SELECT * FROM USER WHERE LOGIN=?";
	private static final String UPDATE_PASSWORD = "UPDATE USER SET PASSWORD=? WHERE LOGIN=?";
	private static final String USER_DATA_VALIDATION = "SELECT * FROM USER WHERE LOGIN=? AND EMAIL=? AND PASSWORDTIP=?";
	private static final String INSERT_USER = "INSERT INTO USER(NAME,EMAIL,LOGIN,PASSWORD,PASSWORDTIP,PROFILE,LIKES,DISLIKES,BLOCKED) VALUES (?,?,?,?,?,?,?,?,?)";
	private static final String SELECT_USER = "SELECT * FROM USER WHERE ID=?";
	
	public static UserDAOImpl instance;
	private Connection conn;
	
	public static UserDAOImpl getInstance(){
		if(instance == null){
			instance = new UserDAOImpl();
		}
		return instance;
	}
	
	public void add(User usuario){
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(INSERT_USER);
			preparedStatement.setString(1, usuario.getName());
			preparedStatement.setString(2, usuario.getEmail());
			preparedStatement.setString(3, usuario.getLogin());
			preparedStatement.setString(4, usuario.getPassword());
			preparedStatement.setString(5, usuario.getPasswordTip());
			preparedStatement.setInt(6, 2);
			preparedStatement.setInt(7, 0);
			preparedStatement.setInt(8, 0);
			preparedStatement.setInt(9, 0);
			preparedStatement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updatePassword(User user){
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(UPDATE_PASSWORD);
			preparedStatement.setString(1,user.getPassword());
			preparedStatement.setString(2,user.getLogin());
			preparedStatement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public User userDataValidation(User usuario){
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		User usuarioRetorno = new User();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(USER_DATA_VALIDATION);
			preparedStatement.setString(1, usuario.getLogin());
			preparedStatement.setString(2, usuario.getEmail());
			preparedStatement.setString(3, usuario.getPasswordTip());
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				usuarioRetorno.setLogin(result.getString("LOGIN"));
			}
			return usuarioRetorno;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return usuarioRetorno;
		}
	}
	
	public User userLoginValidation(String login){
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		User usuarioRetorno = new User();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(LOGIN_VALIDATION);
			preparedStatement.setString(1, login);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				usuarioRetorno.setLogin(result.getString("LOGIN"));
			}
			return usuarioRetorno;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return usuarioRetorno;
		}
	}
	
	public User login(User usuario){
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		User usuarioRetorno = new User();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(LOGIN);
			preparedStatement.setString(1, usuario.getLogin());
			preparedStatement.setString(2, usuario.getPassword());
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				usuarioRetorno.setId(result.getInt("ID"));
				usuarioRetorno.setName(result.getString("NAME"));
				usuarioRetorno.setEmail(result.getString("EMAIL"));
				usuarioRetorno.setLogin(result.getString("LOGIN"));
				usuarioRetorno.setPassword(result.getString("PASSWORD"));
				usuarioRetorno.setProfile(result.getInt("PROFILE"));
				usuarioRetorno.setLikes(result.getInt("LIKES"));
				usuarioRetorno.setDislikes(result.getInt("DISLIKES"));
				int blocked = result.getInt("BLOCKED");
				if(blocked == 0){
					usuarioRetorno.setBlocked(false);				
				}else{
					usuarioRetorno.setBlocked(true);					
				}
			}
			return usuarioRetorno;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return usuarioRetorno;
		}
	}

	public User getUserByID(Long id) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		User usuarioRetorno = new User();
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_USER);
			preparedStatement.setLong(1, id);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				usuarioRetorno.setId(result.getInt("ID"));
				usuarioRetorno.setName(result.getString("NAME"));
				usuarioRetorno.setEmail(result.getString("EMAIL"));
				usuarioRetorno.setLogin(result.getString("LOGIN"));
				usuarioRetorno.setPassword(result.getString("PASSWORD"));
				usuarioRetorno.setProfile(result.getInt("PROFILE"));
				usuarioRetorno.setLikes(result.getInt("LIKES"));
				usuarioRetorno.setDislikes(result.getInt("DISLIKES"));
				int blocked = result.getInt("BLOCKED");
				if(blocked == 0){
					usuarioRetorno.setBlocked(false);				
				}else{
					usuarioRetorno.setBlocked(true);					
				}
			}
			return usuarioRetorno;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return usuarioRetorno;
		}
	}

}
