package realtime;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.Timer;

import model.Building;
import model.Sensor;


public class RealTimeController implements ActionListener {
	private RealTimePanel panel;
	FilterEnum state;
	int delay = 1000;
	public RealTimeController(RealTimePanel panel) {
		this.panel = panel;
		state = FilterEnum.BUILDING;
		List<Building> buildings = Building.fetchAll();
		List<String> buildingsName = new ArrayList<>();
		for (int i = 0; i < buildings.size(); i++) {
			buildingsName.add(buildings.get(i).getName());
		}
		panel.updateBuildings(buildingsName);
		panel.toggleFilter();
		
		panel.afficherDonnees(Sensor.fetchAll());
		
		ActionListener onRefreshTimer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.updateBuildings(buildingsName);
				panel.filter();
				panel.afficherDonnees(Sensor.fetchAll());
			}
		};
		
		Timer refreshTimer = new Timer(delay, onRefreshTimer);
		refreshTimer.start();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object b = e.getSource();
		if(b instanceof JRadioButton) {
			//Récupération données capteur
			if(state.equals(FilterEnum.SENSOR_TYPE)) {
				List<Building> buildings = Building.fetchAll();
				List<String> buildingsName = new ArrayList<>();
				for (int i = 0; i < buildings.size(); i++) {
					buildingsName.add(buildings.get(i).getName());
				}
				panel.updateBuildings(buildingsName);
				panel.toggleFilter();
				state = FilterEnum.BUILDING;
			}
			else {
				panel.toggleFilter();
				state = FilterEnum.SENSOR_TYPE;
			}
		}
		if(b instanceof JComboBox<?>) {
			@SuppressWarnings("unchecked")
			JComboBox<String> j = (JComboBox<String>) b;
			if(j.getItemAt(0)!=null) {
				panel.filter();
			}
		}

	}

}
