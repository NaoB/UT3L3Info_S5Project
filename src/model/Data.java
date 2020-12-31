package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Data extends Model{
	
	/* Table properties */
	private static String tableName = "data";
	private static String primaryKey = "id";
	
	/* Model properties */
	protected LocalDateTime moment;
	protected Sensor sensor;
	protected float value;
	
	public Data(LocalDateTime moment, Sensor sensor, float value) {
		this.moment = moment;
		this.sensor = sensor;
		this.value = value;
	}
	
	private static Data fromResultSet(ResultSet rs) throws SQLException {
		LocalDateTime moment = rs.getTimestamp("moment").toLocalDateTime();
		Sensor sensor = Sensor.find(rs.getString("sensor")).get(0);
		float value = rs.getFloat("value");
		return new Data(moment, sensor, value);
	}
	
	protected static List<Data> query(String qs) {
		List<Data> result = new ArrayList<>();
		ResultSet queryResult = Model.select(qs);
		try {
			while (queryResult.next()) {
				result.add(Data.fromResultSet(queryResult));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public LocalDateTime getMoment() {
		return moment;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public float getValue() {
		return value;
	}
	
	public static List<Data> fetchAll() {
		String qs = String.format("SELECT * FROM %s", tableName);
		return query(qs);
	}

	public static List<Data> find(String nameSearched) {
		String qs = String.format("SELECT * FROM %s WHERE %s = '%s'", tableName, primaryKey, nameSearched);
		return query(qs);
	}
	
	public static List<Data> search(Map<String, ?> params) {
		String qs = String.format("SELECT * FROM %s WHERE ", tableName);
		for (Entry<String, ?> param : params.entrySet()) {
			qs.concat(String.format("%s = '%s' ", param.getKey(), param.getValue().toString()));
		}
		return query(qs);
	}

	@Override
	protected String getInsertSQL() {
		return String.format("INSERT INTO %s (moment, sensor, value) VALUES ('%s', '%s', %f)", tableName, Timestamp.valueOf(moment), sensor.name, value);
	}

	@Override
	protected String getUpdateSQL() {
		return null;
	}
	
}
