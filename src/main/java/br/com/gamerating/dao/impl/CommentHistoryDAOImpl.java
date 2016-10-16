package br.com.gamerating.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import br.com.gamerating.bean.Comment;
import br.com.gamerating.dao.CommentHistoryDAO;
import br.com.gamerating.dao.connection.ConnectionDAO;

public class CommentHistoryDAOImpl implements CommentHistoryDAO {

	static CommentHistoryDAOImpl instance;
	private Connection conn;
	

	public static CommentHistoryDAOImpl getInstance() {
		if(instance == null){
			instance = new CommentHistoryDAOImpl();
		}
		return instance;
	}

	@Override
	public void addEditInfo(Comment newCommentInfo, Comment oldCommentInfo) {
		if(this.conn == null){
			this.conn = ConnectionDAO.getInstance().getConnection();
		}
		String camposAtualizados = checkCamposAtualizados(newCommentInfo,oldCommentInfo);
		try {
			PreparedStatement preparedStatement = this.conn.prepareStatement(INSERT_COMMENT_HISTORY);
			preparedStatement.setInt(1, (int) newCommentInfo.getId());
			preparedStatement.setString(2, camposAtualizados);
			preparedStatement.setDate(3, new Date(Calendar.getInstance().getTimeInMillis()));
			preparedStatement.setString(4, newCommentInfo.getUser());
			preparedStatement.setString(5, oldCommentInfo.getBody());
			preparedStatement.setString(6, newCommentInfo.getBody());
			preparedStatement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String checkCamposAtualizados(Comment newCommentInfo, Comment oldCommentInfo) {
		StringBuffer camposAtualizados = new StringBuffer();

		if(!newCommentInfo.getBody().equalsIgnoreCase(oldCommentInfo.getBody())){
			camposAtualizados.append(" Body ");
		}
		return camposAtualizados.toString();
	}

	private static final String INSERT_COMMENT_HISTORY = "INSERT INTO COMMENTHISTORY(COMMENTID,CAMPOSATUALIZADOS,CHANGEDDATE,BYUSER,BODYANTES,BODYDEPOIS) VALUES(?,?,?,?,?,?)";
}
