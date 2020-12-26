package model;

import java.util.ArrayList;
import java.util.List;

public class SensorType {
	
	private String name;
	private String unit;
	private float min;
	private float max;
	
	public SensorType(String name, String unit, float min, float max) {
		this.name = name;
		this.unit = unit;
		this.min = min;
		this.max = max;
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
		return new ArrayList<>();
	}
}
