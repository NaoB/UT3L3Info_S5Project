

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
			//Récupération données capteur
			if(state.equals(FilterEnum.SENSOR_TYPE)) {
				String [] buildings = {"placeholder"};
				panel.updateBuildings(buildings);
				panel.toggleFilter();
				state = FilterEnum.BUILDING;
				System.out.println("Lel");
			}
			else {
				panel.toggleFilter();
				state = FilterEnum.SENSOR_TYPE;
				System.out.println("lul");
			}
		}
		if(b instanceof JComboBox<?>) {
			@SuppressWarnings("unchecked")
			JComboBox<String> j = (JComboBox<String>) b;
			System.out.println("hey");
			if(j.getItemAt(0)!=null) {
				panel.filter();
				System.out.println("Ho");
			}
		}

	}

}
