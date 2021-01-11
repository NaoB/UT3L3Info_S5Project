package graphic;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import javax.swing.SpinnerDateModel;

import org.jfree.chart.ChartPanel;

import model.Sensor;
import model.SensorType;

public class GraphicPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static final JLabel label=new JLabel("Visualisation des données a posteriori",JLabel.CENTER);
	private static final String[] fluids = {"EAU","ELECTRICITE","TEMPERATURE","AIR COMPRIME"};
	private static final JComboBox<String> fluidList = new JComboBox<>(fluids);
	private static SpinnerDateModel model = new SpinnerDateModel();
	private static SpinnerDateModel model2 = new SpinnerDateModel();
	private static Calendar cal = Calendar.getInstance();
	private static Date date = cal.getTime();
    private static final JSpinner infBound = new JSpinner(model);
    private static final JSpinner supBound = new JSpinner(model2);
    private static List<JCheckBox> checkBoxes = new ArrayList<>();	
    
    private SensorType EAU = new SensorType("EAU", "m3", 0, 10);
    private SensorType AIR = new SensorType("AIRCOMPRIME", "m3/h", 0, 5);
    private SensorType ELECTRICITE = new SensorType("ELECTRICITE", "kWh", 10, 500);
    private SensorType TEMPERATURE = new SensorType("TEMPERATURE", "°C", 17, 22);
	
    
    private static final JButton buttonOk = new JButton("OK");
    
    private static List<Sensor> sensors = new ArrayList<>();
	
	public GraphicPanel() {
		fluidList.setMaximumSize(new Dimension(50,30));	 
		model.setValue(date);
	    infBound.setMaximumSize(new Dimension(50,30));
		supBound.setMaximumSize(new Dimension(50,30));
		
		showBasicPanel(label, fluidList, infBound, supBound,buttonOk);
		
		buttonOk.addActionListener(new ActionListener(){
			@Override public void 
			actionPerformed(ActionEvent e) {
				// Vider list Sensors
				sensors.clear();
				checkBoxes.clear();
				
				// Récupérer valeurs temps
				Date start,stop;
				start = (Date) infBound.getValue();
				stop = (Date) supBound.getValue();
	
				// Récupérer fluide
				String fluid = fluidList.getSelectedItem().toString();
				switch(fluid) { // Récupérer et afficher capteurs du fluide sélectionné 
				case "EAU" : 
					findSensorsByFLuid(EAU,getParent(), sensors, start, stop);
					break;			
				case "ELECTRICITE" :
					findSensorsByFLuid(ELECTRICITE, getParent(), sensors, start, stop);
					break;			
				case "TEMPERATURE" :
					findSensorsByFLuid(TEMPERATURE, getParent(), sensors, start, stop);
					break;
				default : // air comprime
					findSensorsByFLuid(AIR, getParent(), sensors, start, stop);
					break;
				}
			}
		});
		
	}

	private void findSensorsByFLuid(SensorType type, Container frame, List<Sensor> sensors,Date start, Date stop) {
		if(type.getSensors().isEmpty()) {
			this.removeAll();
			showBasicPanel(label, fluidList, infBound, supBound,buttonOk);
			this.add(new JLabel("Aucun capteur trouvé pour ce fluide"));
			this.revalidate();
			this.repaint();
		}else {
			for (Sensor s : type.getSensors()) {
				showSensors(frame, sensors, start, stop, s);
			}
		}
	}
		
	private void showSensors(Container frame, List<Sensor> sensors, Date start, Date stop,Sensor s) {
		JCheckBox cac = showCheckBox(buttonOk, label, fluidList, infBound, supBound, s);	
		checkBoxes.add(cac);
		cac.addActionListener(new ActionListener(){
			@Override public void 
			actionPerformed(ActionEvent e) {
				updateGraphic(frame, sensors,start, stop, s, cac);	
			}
		});
	}
		
	private JCheckBox showCheckBox(JButton buttonOk, JLabel label, JComboBox<String> fluidList, JSpinner infBound, JSpinner supBound,Sensor s) {
		JCheckBox cac = new JCheckBox(s.getName());
		checkBoxes.add(cac);
		this.removeAll();
		showBasicPanel(label, fluidList, infBound, supBound,buttonOk);
		for(JCheckBox cb : checkBoxes) {
			this.add(cb);
		}
		this.revalidate();
		this.repaint();
		return cac;
	}

	private void showBasicPanel(JLabel label, JComboBox<String> fluidList, JSpinner infBound,JSpinner supBound,JButton btn) {
		Box line1=new Box(BoxLayout.X_AXIS);
		line1.add(Box.createHorizontalGlue());
		line1.add(label);
		line1.add(Box.createHorizontalGlue());
			
		Box line2=new Box(BoxLayout.X_AXIS);
		line2.add(new JLabel("Fluide : "));
		line2.add(fluidList);
		line2.add(Box.createHorizontalGlue());
		line2.add(new JLabel("Date début : "));
		line2.add(infBound);
		line2.add(Box.createHorizontalGlue());
		line2.add(new JLabel("Date fin : "));
		line2.add(supBound);
		line2.add(Box.createHorizontalGlue());
		line2.add(btn);
				
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.add(line1);
		this.add(line2);
	}

	public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant()
		      .atZone(ZoneId.systemDefault())
		      .toLocalDateTime();
	}
		
	private void updateGraphic(Container frame, List<Sensor> sensors, Date start, Date stop, Sensor s, JCheckBox cac) {
		if(cac.isSelected()) {
			sensors.add(s);
		}else {
			sensors.remove(s);
		}
			
		this.removeAll();
		if(sensors.size()<3) {
			for(JCheckBox cb : checkBoxes) {
				cb.setEnabled(true);
			}
		}
		showBasicPanel(label, fluidList, infBound, supBound,buttonOk);
		for(JCheckBox cb : checkBoxes) {
			this.add(cb);
		}
		this.revalidate();
		this.repaint();
			
		if(sensors.size()==3) {
			JOptionPane.showMessageDialog(this, "Attention vous pouvez sélectionner 3 capteurs maximum à la fois ");
			for(JCheckBox cb : checkBoxes) {
				if(!cb.isSelected()) {
					cb.setEnabled(false);
				}
			}
		}
		Chart chart = new Chart(sensors,convertToLocalDateTimeViaInstant(start),convertToLocalDateTimeViaInstant(stop));
		ChartPanel chartPanel = chart.show(sensors,convertToLocalDateTimeViaInstant(start),convertToLocalDateTimeViaInstant(stop), frame);
		this.add(chartPanel);
		this.revalidate();
		this.repaint();
	}

}
