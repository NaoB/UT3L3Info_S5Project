package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Sensor extends Model {

	/* Table properties */
	private static String tableName = "sensor";
	private static String primaryKey = "name";
	
	/* Model properties */
	protected String name;
	protected SensorType sensorType;
	protected Building building;
	protected int floor;
	protected String location;
	protected boolean connected;
	protected float value;
	protected float min;
	protected float max;
	
	public Sensor(String name, SensorType sensorType, Building building, int floor, String location) {
		this.name = name;
		this.sensorType = sensorType;
		this.building = building;
		this.floor = floor;
		this.location = location;
		this.min = sensorType.getDefaultMin();
		this.max = sensorType.getDefaultMax();
	}
	
	private static Sensor fromResultSet(ResultSet rs) throws SQLException {
		String name = rs.getString("name");
		SensorType sensorType = SensorType.find(rs.getString("sensor_type")).get(0);
		Building building = Building.find(rs.getString("building")).get(0);
		int floor = rs.getInt("floor");
		String location = rs.getString("location");
		boolean connected = rs.getBoolean("connected");
		float value = rs.getFloat("value");
		float min = rs.getFloat("min");
		float max = rs.getFloat("max");
		Sensor s = new Sensor(name, sensorType, building, floor, location);
		s.setMin(min);
		s.setMax(max);
		s.setConnected(connected);
		s.setValue(value);
		s.exists = true;
		return s;
	}
	
	protected static List<Sensor> query(String qs) {
		List<Sensor> result = new ArrayList<>();
		ResultSet queryResult = Model.select(qs);
		try {
			while (queryResult.next()) {
				result.add(Sensor.fromResultSet(queryResult));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public String getName() {
		return name;
	}

	public SensorType getSensorType() {
		return sensorType;
	}

	public Building getBuilding() {
		return building;
	}

	public int getFloor() {
		return floor;
	}

	public String getLocation() {
		return location;
	}
	
	public List<Data> getDatas() {
		Map<String, String> searchParams = new HashMap<>();
		searchParams.put("sensor", name);
		return Data.search(searchParams);
	}
	
	public static List<Sensor> fetchAll() {
		String qs = String.format("SELECT * FROM %s", tableName);
		return query(qs);
	}
	
	public static List<Sensor> find(String nameSearched) {
		String qs = String.format("SELECT * FROM %s WHERE %s = '%s'", tableName, primaryKey, nameSearched);
		return query(qs);
	}
	
	public static List<Sensor> search(Map<String, ?> params) {
		String qs = String.format("SELECT * FROM %s WHERE ", tableName);
		for (Entry<String, ?> param : params.entrySet()) {
			qs = qs.concat(String.format("%s = '%s' ", param.getKey(), param.getValue().toString()));
		}
		return query(qs);
	}

	@Override
	protected String getInsertSQL() {
		return String.format("INSERT INTO %s (name, sensor_type, building, floor, location, connected, min, max) VALUES ('%s', '%s', '%s', %d, '%s', %d, %f, %f)",
				tableName, name, sensorType.name, building.name, floor, location, connected ? 1 : 0, min, max);
	}

	@Override
	protected String getUpdateSQL() {
		return String.format("UPDATE %s SET sensor_type = '%s', building = '%s', floor = %d, location = '%s', connected = %d, value = %f, min = %f, max = %f WHERE %s = '%s'",
				tableName, sensorType.name, building.name, floor, location, connected ? 1 : 0, value, min, max, primaryKey, name);
	}
	
	@Override
	public String toString() {
		return String.format("Sensor(%s, %s, %s, Floor %d, %s, %s, Value %f, Min %f, Max %f)", name, sensorType.name, building.name, floor, location, connected ? "Connected" : "Not connected", value, min, max);
	}
	
}
