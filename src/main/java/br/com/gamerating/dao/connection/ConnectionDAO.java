package br.com.gamerating.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDAO {
	
	private static ConnectionDAO instance;
//	private static final String URL = "jdbc:hsqldb:file:C:\\Users\\lmacedo\\git\\GameRatingWebService\\resources\\database\\gameRating";
	private static final String URL = "jdbc:postgresql://localhost:5432/GameRate";
//jdbc:hsqldb:file:C:\workspace\GameRaingWebService\resources\database\gameRating
//jdbc:hsqldb:file:C:\Users\lmacedo\git\GameRatingWebService\resources\database\gameRating
	
	private Connection conn;
	private ConnectionDAO(){}
	
	public static ConnectionDAO getInstance(){
		if(instance == null){
			instance = new ConnectionDAO();
		}
		return instance;
	}
	
	public Connection getConnection(){
		if(this.conn == null){
			try {
				Class.forName("org.postgresql.Driver");
				this.conn = DriverManager.getConnection(URL, "postgres", "091468");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return this.conn;
	}
}
