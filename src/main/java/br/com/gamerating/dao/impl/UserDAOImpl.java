package br.com.gamerating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.gamerating.bean.User;
import br.com.gamerating.dao.UserDAO;
import br.com.gamerating.dao.connection.ConnectionDAO;

public class UserDAOImpl implements UserDAO {
	
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
			preparedStatement.setInt(10, 0);
			preparedStatement.setInt(11, 0);
			preparedStatement.setInt(12, 0);
			preparedStatement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updatePasswordByLogin(User user){
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
				usuarioRetorno.setPasswordTip(result.getString("PASSWORDTIP"));
				usuarioRetorno.setProfile(result.getInt("PROFILE"));
				usuarioRetorno.setLikes(result.getInt("LIKES"));
				usuarioRetorno.setDislikes(result.getInt("DISLIKES"));
				usuarioRetorno.setComments(result.getInt("COMMENTS"));
				usuarioRetorno.setTopics(result.getInt("TOPICS"));
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
				usuarioRetorno.setComments(result.getInt("COMMENTS"));
				usuarioRetorno.setTopics(result.getInt("TOPICS"));
				int blocked = result.getInt("BLOCKED");
				if(blocked == 0){
					usuarioRetorno.setBlocked(false);				
				}else{
					usuarioRetorno.setBlocked(true);					
				}
				int visible = result.getInt("VISIBLE");
				if(visible == 0){
					usuarioRetorno.setVisible(false);				
				}else{
					usuarioRetorno.setVisible(true);					
				}
			}
			return usuarioRetorno;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return usuarioRetorno;
		}
	}

	public void updateDisableStatus(User user) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		int visibility = 1;
		if(user.isVisible()){
			visibility = 0;
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(DISABLE_USER);
			preparedStatement.setInt(1,visibility);
			preparedStatement.setLong(2,user.getId());
			preparedStatement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateTopics(User user) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(UPDATE_USER_TOPICS);
			preparedStatement.setInt(1,user.getTopics());
			preparedStatement.setLong(2,user.getId());
			preparedStatement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateComments(User user) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(UPDATE_USER_COMMENTS);
			preparedStatement.setInt(1,user.getTopics());
			preparedStatement.setLong(2,user.getId());
			preparedStatement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateUserProfile(User user) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(UPDATE_USER_PROFILE);
			preparedStatement.setInt(1,user.getProfile());
			preparedStatement.setLong(2,user.getId());
			preparedStatement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void updateBlockStatus(User user) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		int blocked = 0;
		if(user.isBlocked()){
			blocked = 1;
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(BLOCK_USER);
			preparedStatement.setInt(1,blocked);
			preparedStatement.setLong(2,user.getId());
			preparedStatement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public String checkReputation(String profileId, String userId) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		String reputation = "";
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(SELECT_USER_REPUTARION);
			preparedStatement.setInt(1, Integer.valueOf(profileId));
			preparedStatement.setInt(2, Integer.valueOf(userId));
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()){
				reputation = String.valueOf(result.getInt("ISLIKE"));
			}
			return reputation;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return reputation;
		}
	}

	@Override
	public void updateToLike(String profileId, String userId) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(UPDATE_REPUTATION);
			preparedStatement.setInt(1,0);
			preparedStatement.setInt(2,Integer.valueOf(profileId));
			preparedStatement.setInt(3,Integer.valueOf(userId));
			preparedStatement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	@Override
	public void updateToDislike(String profileId, String userId) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(UPDATE_REPUTATION);
			preparedStatement.setInt(1,1);
			preparedStatement.setInt(2,Integer.valueOf(profileId));
			preparedStatement.setInt(3,Integer.valueOf(userId));
			preparedStatement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	public void addLike(String profileId, String userId) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(INSERT_REPUTATION);
			preparedStatement.setInt(1, Integer.valueOf(profileId));
			preparedStatement.setInt(2, Integer.valueOf(userId));
			preparedStatement.setInt(3, 0);
			preparedStatement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void addDislike(String profileId, String userId) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(INSERT_REPUTATION);
			preparedStatement.setInt(1, Integer.valueOf(profileId));
			preparedStatement.setInt(2, Integer.valueOf(userId));
			preparedStatement.setInt(3, 1);
			preparedStatement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void updateUserReputation(User userProfile) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(UPDATE_USER_FULL_REPUTATION);
			preparedStatement.setInt(1,userProfile.getLikes());
			preparedStatement.setInt(2,userProfile.getDislikes());
			preparedStatement.setLong(3,userProfile.getId());
			preparedStatement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}

	@Override
	public void updateUser(User user) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(UPDATE_USER);
			preparedStatement.setString(1,user.getName());
			preparedStatement.setString(2,user.getEmail());
			preparedStatement.setString(3,user.getPasswordTip());
			preparedStatement.setLong(4,user.getId());
			preparedStatement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static final String LOGIN = "SELECT * FROM USER WHERE LOGIN=? AND PASSWORD=?";
	private static final String LOGIN_VALIDATION = "SELECT * FROM USER WHERE LOGIN=?";
	private static final String UPDATE_PASSWORD = "UPDATE USER SET PASSWORD=? WHERE LOGIN=?";
	private static final String UPDATE_REPUTATION = "UPDATE REPUTATION SET ISLIKE=? WHERE PROFILEID=? AND USERID=?";
	private static final String UPDATE_USER_TOPICS = "UPDATE USER SET TOPICS=? WHERE ID=?";
	private static final String UPDATE_USER_COMMENTS = "UPDATE USER SET COMMENTS=? WHERE ID=?";
	private static final String UPDATE_USER_PROFILE = "UPDATE USER SET PROFILE=? WHERE ID=?";
	private static final String UPDATE_USER = "UPDATE USER SET NAME=?,EMAIL=?,PASSWORDTIP=? WHERE ID=?";
	private static final String UPDATE_USER_FULL_REPUTATION = "UPDATE USER SET LIKES=?,DISLIKES=? WHERE ID=?";
	private static final String DISABLE_USER = "UPDATE USER SET VISIBLE=? WHERE ID=?";
	private static final String BLOCK_USER = "UPDATE USER SET BLOCKED=? WHERE ID=?";
	private static final String USER_DATA_VALIDATION = "SELECT * FROM USER WHERE LOGIN=? AND EMAIL=? AND PASSWORDTIP=?";
	private static final String INSERT_USER = "INSERT INTO USER(NAME,EMAIL,LOGIN,PASSWORD,PASSWORDTIP,PROFILE,LIKES,DISLIKES,BLOCKED,VISIBLE,COMMENTS,TOPICS) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String INSERT_REPUTATION = "INSERT INTO REPUTATION(PROFILEID,USERID,ISLIKE) VALUES (?,?,?)";
	private static final String SELECT_USER = "SELECT * FROM USER WHERE ID=?";
	private static final String SELECT_USER_REPUTARION = "SELECT * FROM REPUTATION WHERE PROFILEID=? AND USERID=?";

}
