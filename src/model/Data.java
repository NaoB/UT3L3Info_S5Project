package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

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
		LocalDateTime moment = rs.getTimestamp("moment").toLocalDateTime().minusHours(1); // -1 heure pour régler le probleme de décalage
		Sensor sensor = Sensor.find(rs.getString("sensor")).get(0);
		float value = rs.getFloat("value");
		return new Data(moment, sensor, value);
	}
	
	private static Data fromResultSet(ResultSet rs, Model owner) throws SQLException {
		LocalDateTime moment = rs.getTimestamp("moment").toLocalDateTime().minusHours(1);
		Sensor sensor = (Sensor) owner;
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
	
	public static Data findOne(String nameSearched) {
		String qs = String.format("SELECT * FROM %s WHERE %s = '%s'", tableName, primaryKey, nameSearched);
		List<Data> result = query(qs);
		if (result.isEmpty()) return null;
		else return result.get(0);
	}
	
	public static List<Data> search(Map<String, ?> params) {
		String qs = String.format("SELECT * FROM %s", tableName);
		if (!params.isEmpty()) qs = qs.concat(" WHERE ");
		for (Entry<String, ?> param : params.entrySet()) {
			qs = qs.concat(String.format("%s = '%s' AND ", param.getKey(), param.getValue().toString()));
		}
		return query(qs.substring(0, qs.length() - 4));
	}
	
	public static List<Data> belongsTo(Sensor sensor) {
		String qs = String.format("SELECT * FROM %s WHERE %s = '%s'", tableName, "sensor", sensor.getName());
		List<Data> result = new ArrayList<>();
		ResultSet queryResult = Model.select(qs);
		try {
			while (queryResult.next()) {
				result.add(Data.fromResultSet(queryResult, sensor));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected String getInsertSQL() {
		return String.format("INSERT INTO %s (moment, sensor, value) VALUES ('%s', '%s', %f)", tableName, Timestamp.valueOf(moment), sensor.name, value);
	}

	@Override
	protected String getUpdateSQL() {
		return null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(moment, sensor, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Data other = (Data) obj;
		return moment.equals(other.moment) && sensor.equals(other.sensor) && value == other.value;
	}
	
	
	
}
