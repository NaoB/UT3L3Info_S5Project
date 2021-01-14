package sensormanagement;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import model.Sensor;

public class DetailsPanel extends JPanel {

	private static final long serialVersionUID = 8165715963212744423L;
	
	private Sensor sensor;
	
	private int delay = 1000;

	private ActionListener onModifyBtnClick;
	private ActionListener onRefreshTimer;
	
	public DetailsPanel() {
		super();
		layoutSetup();
		Component that = this;
		
		onModifyBtnClick = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Modify button clicked");
				JTextField minTxt = new JTextField(5);
				JTextField maxTxt = new JTextField(5);
				minTxt.setText(String.valueOf(sensor.getMin()));
				maxTxt.setText(String.valueOf(sensor.getMax()));

			    JPanel dialogPanel = new JPanel();
			    dialogPanel.add(new JLabel("Min:"));
			    dialogPanel.add(minTxt);
			    dialogPanel.add(new JLabel("Max:"));
			    dialogPanel.add(maxTxt);
			    int result = JOptionPane.showConfirmDialog(that, dialogPanel, "Entrez les bornes pour les valeurs du capteur", JOptionPane.OK_CANCEL_OPTION);
			    if (result == JOptionPane.OK_OPTION) {
			    	try {
			    		float min = Float.valueOf(minTxt.getText());
				    	float max = Float.valueOf(maxTxt.getText());
				    	sensor.setMin(min);
				    	sensor.setMax(max);
				    	sensor.save();
				    	JOptionPane.showMessageDialog(that, "Les bornes du capteur ont été modifiées");
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(that, "ERREUR : Les valeurs entrées sont invalides", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
			    }
			}
		};
		
		onRefreshTimer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (sensor != null) {
					System.out.println("Update details panel");
					sensor = Sensor.findOne(sensor.getName());
					removeAll();
					layoutSetup();
					revalidate();
					repaint();
				}
			}
		};
		
		Timer refreshTimer = new Timer(delay, onRefreshTimer);
		refreshTimer.start();
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
		setBorder(new EmptyBorder(10, 10, 10, 10));
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
			JButton modifyBtn = new JButton("Modifier");
			modifyBtn.addActionListener(onModifyBtnClick);
			return modifyBtn;
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
