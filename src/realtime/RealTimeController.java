package realtime;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;


public class RealTimeController implements ActionListener {
	private RealTimePanel panel;
	FilterEnum state;
	public RealTimeController(RealTimePanel panel) {
		this.panel = panel;
		state = FilterEnum.BUILDING;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object b = e.getSource();
		if(b instanceof JRadioButton) {
			//R�cup�ration donn�es capteur
			if(state.equals(FilterEnum.SENSOR_TYPE)) {
				String [] buildings = {"placeholder"};
				panel.updateBuildings(buildings);
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
