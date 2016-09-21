package br.com.gamerating.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import br.com.gamerating.bean.User;
import br.com.gamerating.dao.UserHistoryDAO;
import br.com.gamerating.dao.connection.ConnectionDAO;

public class UserHistoryDAOImpl implements UserHistoryDAO {
	static UserHistoryDAOImpl instance;
	private Connection conn;
	
	public static UserHistoryDAOImpl getInstance() {
		if(instance == null){
			instance = new UserHistoryDAOImpl();
		}
		return instance;
	}

	@Override
	public void addEditInfo(User newUserInfo, User oldUserInfo) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		String camposAtualizados = checkCamposAtualizados(newUserInfo,oldUserInfo);
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(INSERT_USER_HISTORY);
			preparedStatement.setLong(1, newUserInfo.getId());
			preparedStatement.setString(2, camposAtualizados);
			preparedStatement.setDate(3, new Date(Calendar.getInstance().getTimeInMillis()));
			preparedStatement.setString(4, newUserInfo.getLogin());
			preparedStatement.setString(5, oldUserInfo.getName());
			preparedStatement.setString(6, newUserInfo.getName());
			preparedStatement.setString(7, oldUserInfo.getEmail());
			preparedStatement.setString(8, newUserInfo.getEmail());
			preparedStatement.setString(9, oldUserInfo.getPasswordTip());
			preparedStatement.setString(10, newUserInfo.getPasswordTip());
			preparedStatement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String checkCamposAtualizados(User newUserInfo, User oldUserInfo) {
		StringBuffer camposAtualizados = new StringBuffer();

		if(!newUserInfo.getName().equalsIgnoreCase(oldUserInfo.getName())){
			camposAtualizados.append(" Name ");
		}
		if(!newUserInfo.getEmail().equalsIgnoreCase(oldUserInfo.getEmail())){
			camposAtualizados.append(" Email ");
		}
		if(!newUserInfo.getPasswordTip().equalsIgnoreCase(oldUserInfo.getPasswordTip())){
			camposAtualizados.append(" PasswordTip ");
		}
		return camposAtualizados.toString();
	}

	private static final String INSERT_USER_HISTORY = "INSERT INTO USERHISTORY(USERID,CAMPOSATUALIZADOS,CHANGEDDATE,BYUSER,NAMEANTES,NAMEDEPOIS,EMAILANTES,EMAILDEPOIS,PASSWORDTIPANTES,PASSWORDTIPDEPOIS) VALUES(?,?,?,?,?,?,?,?,?,?)";
	
}
