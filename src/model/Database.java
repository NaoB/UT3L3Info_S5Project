package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	private static Database INSTANCE = new Database();
	
	private String dbHost = "localhost";
	private String dbName = "project";
	private String dbUser = "root";
	private String dbPass = "root";
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
