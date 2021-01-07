package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class Building extends Model {
	
	/* Table properties */
	private static String tableName = "building";
	private static String primaryKey = "name";
	
	/* Model properties */
	protected String name;

	public Building(String name) {
		this.name = name;
	}
	
	private static Building fromResultSet(ResultSet rs) throws SQLException {
		String name = rs.getString("name");
		return new Building(name);
	}
	
	protected static List<Building> query(String qs) {
		List<Building> result = new ArrayList<>();
		ResultSet queryResult = Model.select(qs);
		try {
			while (queryResult.next()) {
				result.add(Building.fromResultSet(queryResult));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getName() {
		return name;
	}
	
	public List<Sensor> getSensors() {
		Map<String, String> searchParams = new HashMap<>();
		searchParams.put("building", name);
		return Sensor.search(searchParams);
	}
	
	public static List<Building> fetchAll() {
		String qs = String.format("SELECT * FROM %s", tableName);
		return query(qs);
	}

	public static List<Building> find(String nameSearched) {
		String qs = String.format("SELECT * FROM %s WHERE %s = '%s'", tableName, primaryKey, nameSearched);
		return query(qs);
	}
	
	public static Building findOne(String nameSearched) {
		String qs = String.format("SELECT * FROM %s WHERE %s = '%s'", tableName, primaryKey, nameSearched);
		List<Building> result = query(qs);
		if (result.isEmpty()) return null;
		else return result.get(0);
	}

	public static List<Building> search(Map<String, ?> params) {
		String qs = String.format("SELECT * FROM %s", tableName);
		if (!params.isEmpty()) qs = qs.concat(" WHERE ");
		for (Entry<String, ?> param : params.entrySet()) {
			qs = qs.concat(String.format("%s = '%s' AND ", param.getKey(), param.getValue().toString()));
		}
		return query(qs.substring(0, qs.length() - 4));
	}
		
	@Override
	protected String getInsertSQL() {
		return String.format("INSERT INTO %s (name) VALUES ('%s')", tableName, name);
	}

	@Override
	protected String getUpdateSQL() {
		return null;
	}
	
	

	@Override
	public String toString() {
		return "Batiment " + name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Building other = (Building) obj;
		return name.equals(other.name);
	}
	
}
