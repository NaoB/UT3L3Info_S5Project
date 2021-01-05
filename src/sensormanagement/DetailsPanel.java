package sensormanagement;

import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Sensor;

public class DetailsPanel extends JPanel {

	private static final long serialVersionUID = 8165715963212744423L;
	
	private Sensor sensor;
	
	public DetailsPanel() {
		super();
		layoutSetup();
	}
	
	public void setSensor(Sensor sensor) {
		System.out.println("New sensor selected : " + sensor.getName());
		this.sensor = sensor;
		removeAll();
		layoutSetup();
		repaint();
		revalidate();
	}

	private void layoutSetup() {
		setLayout(new GridLayout(8,2));
		
		add(new JLabel("Nom :"));
		add(sensorName());
		
		add(new JLabel("Type : "));
		add(sensorType());
		
		add(new JLabel("Lieu : "));
		add(sensorLocation());
		
		add(new JLabel("Etat : "));
		add(sensorState());
		
		add(new JLabel("Valeur : "));
		add(sensorValue());
		
		add(new JLabel("Min : "));
		add(sensorMin());
		
		add(new JLabel("Max : "));
		add(sensorMax());
		
		add(sensorModify());
	}

	private Component sensorModify() {
		if (sensor != null) {
			return new JButton("Modifier");
		}
		return new JLabel("...");
	}

	private Component sensorMin() {
		if (sensor != null) {
			return new JLabel(String.valueOf(sensor.getMin()) + sensor.getSensorType().getUnit());
		}
		return new JLabel("...");
	}

	private Component sensorMax() {
		if (sensor != null) {
			return new JLabel(String.valueOf(sensor.getMax()) + sensor.getSensorType().getUnit());
		}
		return new JLabel("...");
	}

	private Component sensorValue() {
		if (sensor != null) {
			return new JLabel(String.valueOf(sensor.getValue()) + sensor.getSensorType().getUnit());
		}
		return new JLabel("...");
	}

	private Component sensorLocation() {
		if (sensor != null) {
			return new JLabel("<html>" + sensor.getBuilding() + "<br>Etage " + sensor.getFloor() + "<br>" + sensor.getLocation());
		}
		return new JLabel("...");
	}
	
	private Component sensorState() {
		if (sensor != null) {
			return new JLabel(sensor.isConnected() ? "ON" : "OFF");
		}
		return new JLabel("...");
	}
	
	private Component sensorType() {
		if (sensor != null) {
			return new JLabel(sensor.getSensorType().toString());
		}
		return new JLabel("...");
	}

	private Component sensorName() {
		if (sensor != null) {
			return new JLabel(sensor.getName());
		}
		return new JLabel("...");
	}
	
	
	
	
}
