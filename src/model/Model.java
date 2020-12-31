package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

abstract class Model {
	
	private boolean exists = false;
	
	protected abstract String getInsertSQL();
	protected abstract String getUpdateSQL();
		
	public void save() {		
		Connection connect = Database.getInstance().getConnection();
		try (Statement stmt = connect.createStatement()) {
			if (!exists) {
				stmt.executeUpdate(this.getInsertSQL());
				exists = true;
			} else if (this.getUpdateSQL() != null){
				stmt.executeUpdate(this.getUpdateSQL());
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
