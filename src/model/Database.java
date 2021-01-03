package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	private static Database INSTANCE = new Database();
	
	private String dbHost = "78.47.240.104";
	private String dbName = "project";
	private String dbUser = "root";
	private String dbPass = "projetS5AAB";
	private Connection connection;
	
	private Database() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + dbHost + "/" + dbName, dbUser, dbPass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Database getInstance() {
		return INSTANCE;
	}

	public Connection getConnection() {
		return connection;
	}
}
