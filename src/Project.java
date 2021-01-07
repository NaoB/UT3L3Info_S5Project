import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import model.Building;
import model.Data;
import model.Sensor;
import model.SensorType;

import org.jfree.chart.ChartFactory;

import network.Server;
import sensormanagement.ManagementPanel;

public class Project extends JFrame {

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
    
    private static final JButton buttonOk = new JButton("OK");
	
	public static void main(String[] args) {
		
		// Lancer serveur sur port 8952
		Server server = new Server(8952);
		server.open();
				
		SensorType EAU = new SensorType("EAU", "m3", 0, 10);
		SensorType AIR = new SensorType("AIRCOMPRIME", "m3/h", 0, 5);
		SensorType ELECTRICITE = new SensorType("ELECTRICITE", "kWh", 10, 500);
		SensorType TEMPERATURE = new SensorType("TEMPERATURE", "°C", 17, 22);
		//JButton buttonOk = new JButton("OK");
		
		// Creation fenêtre principale
		JFrame frame = new JFrame("Capteurs du campus");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 900); 
				
		// Création, paramétrage et positionnement des composants
		// Panel pour les deux blocs à  gauche (Temps Reel et Gestion)
		JSplitPane splitHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JPanel(), new ManagementPanel());
		
		// Visualisation a posteriori
		List<Sensor> sensors = new ArrayList<>();
				
		/*JLabel label=new JLabel("Visualisation des données a posteriori",JLabel.CENTER);
		String[] fluids = {"EAU","ELECTRICITE","TEMPERATURE","AIR COMPRIME"};
		JComboBox<String> fluidList = new JComboBox<>(fluids);*/
		fluidList.setMaximumSize(new Dimension(50,30));
		
		SpinnerDateModel model = new SpinnerDateModel();
		SpinnerDateModel model2 = new SpinnerDateModel();
		/*Calendar cal = Calendar.getInstance();
	    Date date = cal.getTime();*/
		 
		model.setValue(date);
	    /*JSpinner infBound = new JSpinner(model);
	    JSpinner supBound = new JSpinner(model2);*/
	    infBound.setMaximumSize(new Dimension(50,30));
		supBound.setMaximumSize(new Dimension(50,30));

		JPanel jpanelText = new JPanel();
		jpanelText.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		// Panel pour le précedent et les graphiques
		JPanel panel = new JPanel();
		JSplitPane splitVertical = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitHorizontal, panel);
		
		showBasicPanel(label, fluidList, infBound, supBound, panel,buttonOk);
		
		buttonOk.addActionListener(new ActionListener(){
			@Override public void 
			actionPerformed(ActionEvent e) {
				// Récupérer valeurs temps
				Date start,stop;
				start = (Date) infBound.getValue();
				stop = (Date) supBound.getValue();
				
				// Récupérer fluide
				String fluid = fluidList.getSelectedItem().toString();
				switch(fluid) {
				case "EAU" : 
					for (Sensor s : EAU.getSensors()) {
						JCheckBox cac = showCheckBox(buttonOk, label, fluidList, infBound, supBound, panel, s);		
						cac.addActionListener(new ActionListener(){
							@Override public void 
							actionPerformed(ActionEvent e) {
								updateGraphic(frame, sensors, panel, start, stop, s, cac);	
							}
						});
					}
					break;			
				case "ELECTRICITE" :
					for (Sensor s : ELECTRICITE.getSensors()) {
						JCheckBox cac = showCheckBox(buttonOk, label, fluidList, infBound, supBound, panel, s);		
						cac.addActionListener(new ActionListener(){
							@Override public void 
							actionPerformed(ActionEvent e) {
								updateGraphic(frame, sensors, panel, start, stop, s, cac);
							}
						});
					}
					break;			
				case "TEMPERATURE" :
					for (Sensor s : TEMPERATURE.getSensors()) {
						JCheckBox cac = showCheckBox(buttonOk, label, fluidList, infBound, supBound, panel, s);		
						cac.addActionListener(new ActionListener(){
							@Override public void 
							actionPerformed(ActionEvent e) {
								updateGraphic(frame, sensors, panel, start, stop, s, cac);	
							}
						});		
					}
					break;
				default : // air comprimé
					for (Sensor s : AIR.getSensors()) {
						JCheckBox cac = showCheckBox(buttonOk, label, fluidList, infBound, supBound, panel, s);	
						cac.addActionListener(new ActionListener(){
							@Override public void 
							actionPerformed(ActionEvent e) {
									updateGraphic(frame, sensors, panel, start, stop, s, cac);
								
							}
						});
					}
					break;
				}
			}
		});
		
		frame.add(splitVertical);
		//frame.add(new RealTimePanel());
		splitHorizontal.setDividerLocation(frame.getHeight() / 2);
		splitVertical.setDividerLocation(frame.getWidth() / 2);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}
	
	private static JCheckBox showCheckBox(JButton buttonOk, JLabel label, JComboBox<String> fluidList,
			JSpinner infBound, JSpinner supBound, JPanel panel, Sensor s) {
		JCheckBox cac = new JCheckBox(s.getName());
		panel.removeAll();
		showBasicPanel(label, fluidList, infBound, supBound, panel,buttonOk);
		panel.add(cac);
		return cac;
	}

	private static void showBasicPanel(JLabel label, JComboBox<String> fluidList, JSpinner infBound,
			JSpinner supBound, JPanel panel,JButton btn) {
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
		
		
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(line1);
		panel.add(line2);
	}

	private static void showGraphic(JFrame frame, List<Sensor> sensors, JPanel panel,JCheckBox cac,Date start,Date stop) {
		if(cac.isSelected()) {
			Chart chart = new Chart(sensors,convertToLocalDateTimeViaInstant(start),convertToLocalDateTimeViaInstant(stop));
			ChartPanel chartPanel = chart.show(sensors,convertToLocalDateTimeViaInstant(start),convertToLocalDateTimeViaInstant(stop), frame);
			panel.add(chartPanel);
		}
	}
	
	public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}
	
	private static void updateGraphic(JFrame frame, List<Sensor> sensors, JPanel panel, Date start, Date stop, Sensor s, JCheckBox cac) {
		//if(cac.isSelected()) {
		if(cac.isSelected()) {
			sensors.add(s);
		}
		else {
			panel.removeAll();
			sensors.remove(s);
			showBasicPanel(label, fluidList, infBound, supBound, panel,buttonOk);
			panel.add(cac);
			
		}
			if(sensors.size()>3) {
				JOptionPane.showMessageDialog(panel, "Attention vous pouvez sélectionner 3 capteurs maximum à la fois ");
			}
			showGraphic(frame, sensors, panel, cac,start,stop);
			
		/*}else {
			if(sensors.contains(s)) {
				sensors.remove(Sensor.findOne(cac.getName()));
			}
			showGraphic(frame, sensors, list, panel, cac,start,stop);
		}*/
	}
	
	/*cac.addChangeListener(new ChangeListener(){
	@Override public void 
	stateChanged(ChangeEvent e) {
		if(!cac.isSelected()) {
			sensors.add(Sensor.findOne(cac.getName()));	
			panel.removeAll();
			JCheckBox cac = showCheckBox(buttonOk, label, fluidList, infBound, supBound, panel, s);	
			showGraphic(frame, sensors, list, panel, cac,start,stop);
		}else {
			sensors.remove(Sensor.findOne(cac.getName()));
			showGraphic(frame, sensors, list, panel, cac,start,stop);
		}
	}
});*/
	
}
