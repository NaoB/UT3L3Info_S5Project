package model;

import java.time.LocalDateTime;

public class Data {

	private LocalDateTime moment;
	private Sensor sensor;
	private float value;
	
	public Data(LocalDateTime moment, Sensor sensor, float value) {
		this.moment = moment;
		this.sensor = sensor;
		this.value = value;
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
	
}
