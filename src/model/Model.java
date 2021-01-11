package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

abstract class Model {
	
	private static boolean DEBUG = false;
	
	protected boolean exists = false;
	
	protected abstract String getInsertSQL();
	protected abstract String getUpdateSQL();
		
	public void save() {		
		Connection connect = Database.getInstance().getConnection();
		try (Statement stmt = connect.createStatement()) {
			String query;
			if (!exists) {
				query = this.getInsertSQL();
				stmt.executeUpdate(query);
				if (DEBUG) System.out.println("[model.Model] SQL Update : " + query);
				exists = true;
			} else if (this.getUpdateSQL() != null){
				query = this.getUpdateSQL();
				stmt.executeUpdate(query);
				if (DEBUG) System.out.println("[model.Model] SQL Update : " + query);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected static ResultSet select(String query) {
		ResultSet result = null;
		Connection connect = Database.getInstance().getConnection();
		Statement stmt;
		try {
			stmt = connect.createStatement();
			result = stmt.executeQuery(query);
			if (DEBUG) System.out.println("[model.Model] SQL Query : " + query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
