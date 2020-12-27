package model;

import java.util.ArrayList;
import java.util.List;

public class Building {
	
	private String name;

	public Building(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public List<Sensor> getSensors() {
		return new ArrayList<>();
	}
}
