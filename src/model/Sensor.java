package model;

import java.util.ArrayList;
import java.util.List;

public class Sensor {

	private int id;
	private String name;
	private SensorType sensorType;
	private Building building;
	private int floor;
	private String location;
	private boolean connected;
	private float value;
	private float min;
	private float max;
	
	public Sensor(String name, SensorType sensorType, Building building, int floor, String location) {
		this.name = name;
		this.sensorType = sensorType;
		this.building = building;
		this.floor = floor;
		this.location = location;
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
		return new ArrayList<>();
	}
}
