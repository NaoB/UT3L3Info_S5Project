package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SensorType extends Model {
	
	/* Table properties */
	private static String tableName = "sensor_type";
	private static String primaryKey = "name";
	
	/* Model properties */
	protected String name;
	protected String unit;
	protected float min;
	protected float max;
	
	public SensorType(String name, String unit, float min, float max) {
		this.name = name;
		this.unit = unit;
		this.min = min;
		this.max = max;
	}
	
	private static SensorType fromResultSet(ResultSet rs) throws SQLException {
		String name = rs.getString("name");
		String unit = rs.getString("unit");
		Float min = rs.getFloat("min");
		Float max = rs.getFloat("max");
		return new SensorType(name, unit, min, max);
	}
	
	protected static List<SensorType> query(String qs) {
		List<SensorType> result = new ArrayList<>();
		ResultSet queryResult = Model.select(qs);
		try {
			while (queryResult.next()) {
				result.add(SensorType.fromResultSet(queryResult));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getName() {
		return name;
	}

	public String getUnit() {
		return unit;
	}

	public float getDefaultMin() {
		return min;
	}

	public float getDefaultMax() {
		return max;
	}
	
	public List<Sensor> getSensors() {
		Map<String, String> searchParams = new HashMap<>();
		searchParams.put("sensor_type", name);
		return Sensor.search(searchParams);
	}
	
	public static List<SensorType> fetchAll() {
		String qs = String.format("SELECT * FROM %s", tableName);
		return query(qs);
	}
	
	public static List<SensorType> find(String nameSearched) {
		String qs = String.format("SELECT * FROM %s WHERE %s = '%s'", tableName, primaryKey, nameSearched);
		return query(qs);
	}
	
	public static SensorType findOne(String nameSearched) {
		String qs = String.format("SELECT * FROM %s WHERE %s = '%s'", tableName, primaryKey, nameSearched);
		List<SensorType> result = query(qs);
		if (result.isEmpty()) return null;
		else return result.get(0);
	}
	
	public static List<SensorType> search(Map<String, ?> params) {
		String qs = String.format("SELECT * FROM %s WHERE ", tableName);
		for (Entry<String, ?> param : params.entrySet()) {
			qs.concat(String.format("%s = '%s' ", param.getKey(), param.getValue().toString()));
		}
		return query(qs);
	}

	@Override
	protected String getInsertSQL() {
		return String.format("INSERT INTO %s (name, unit, min, max) VALUES ('%s', '%s', %f, %f)", tableName, name, unit, min, max);
	}

	@Override
	protected String getUpdateSQL() {
		return null;
	}
	
	
	
}
