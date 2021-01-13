import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import graphic.GraphicPanel;
import realtime.RealTimePanel;

import network.Server;
import sensormanagement.ManagementPanel;

public class Project extends JFrame {
	private static final long serialVersionUID = 1L;
<<<<<<< HEAD

=======
	private static final JLabel label=new JLabel("Visualisation des donn�es a posteriori",JLabel.CENTER);
	private static final String[] fluids = {"EAU","ELECTRICITE","TEMPERATURE","AIR COMPRIME"};
	private static final JComboBox<String> fluidList = new JComboBox<>(fluids);
	private static SpinnerDateModel model = new SpinnerDateModel();
	private static SpinnerDateModel model2 = new SpinnerDateModel();
	private static Calendar cal = Calendar.getInstance();
	private static Date date = cal.getTime();
    private static final JSpinner infBound = new JSpinner(model);
    private static final JSpinner supBound = new JSpinner(model2);
    private static List<JCheckBox> checkBoxes = new ArrayList<>();	
    
    private static final JButton buttonOk = new JButton("OK");
	
>>>>>>> 9f70e4f (Modif btn & timer)
	public static void main(String[] args) {
		
		// Lancer serveur sur port 8952
		Server server = new Server(8952);
		server.open();
<<<<<<< HEAD

=======
				
		SensorType EAU = new SensorType("EAU", "m3", 0, 10);
		SensorType AIR = new SensorType("AIRCOMPRIME", "m3/h", 0, 5);
		SensorType ELECTRICITE = new SensorType("ELECTRICITE", "kWh", 10, 500);
		SensorType TEMPERATURE = new SensorType("TEMPERATURE", "�C", 17, 22);
		
>>>>>>> 9f70e4f (Modif btn & timer)
		// Creation fenetre principale
		JFrame frame = new JFrame("Capteurs du campus");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 900); 
				
		// Creation, parametrage et positionnement des composants
		// Panel pour les deux blocs a gauche (Temps Reel et Gestion)
<<<<<<< HEAD
		JSplitPane splitHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new RealTimePanel(), new ManagementPanel());
=======
		JSplitPane splitHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JPanel() /* ICI */ , new ManagementPanel());
>>>>>>> 9f70e4f (Modif btn & timer)
		
		// Visualisation a posteriori	

		JPanel jpanelText = new JPanel();
		jpanelText.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		// Panel pour le precedent et les graphiques
<<<<<<< HEAD
		JSplitPane splitVertical = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitHorizontal, new GraphicPanel());
=======
		JPanel panel = new JPanel();
		JSplitPane splitVertical = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitHorizontal, panel);
		
		showBasicPanel(label, fluidList, infBound, supBound, panel,buttonOk);
		
		buttonOk.addActionListener(new ActionListener(){
			@Override public void 
			actionPerformed(ActionEvent e) {
				// Vider list Sensors
				sensors.clear();
				checkBoxes.clear();
				
				// R�cup�rer valeurs temps
				Date start,stop;
				start = (Date) infBound.getValue();
				stop = (Date) supBound.getValue();
	
				// R�cup�rer fluide
				String fluid = fluidList.getSelectedItem().toString();
				switch(fluid) { // R�cup�rer et afficher capteurs du fluide s�lectionn� 
				case "EAU" : 
					findSensorsByFLuid(EAU, frame, sensors, panel, start, stop);
					break;			
				case "ELECTRICITE" :
					findSensorsByFLuid(ELECTRICITE, frame, sensors, panel, start, stop);
					break;			
				case "TEMPERATURE" :
					findSensorsByFLuid(TEMPERATURE, frame, sensors, panel, start, stop);
					break;
				default : // air comprime
					findSensorsByFLuid(AIR, frame, sensors, panel, start, stop);
					break;
				}
			}
		});
>>>>>>> 9f70e4f (Modif btn & timer)
		
		frame.add(splitVertical);
		//frame.add(new RealTimePanel());
		splitHorizontal.setDividerLocation(frame.getHeight() / 2);
		splitVertical.setDividerLocation(frame.getWidth() / 2);
	
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}
	
<<<<<<< HEAD
=======
	private static void findSensorsByFLuid(SensorType type, JFrame frame, List<Sensor> sensors, JPanel panel,
			Date start, Date stop) {
		if(type.getSensors().isEmpty()) {
			panel.removeAll();
			showBasicPanel(label, fluidList, infBound, supBound, panel,buttonOk);
			panel.add(new JLabel("Aucun capteur trouv� pour ce fluide"));
			panel.revalidate();
			panel.repaint();
		}else {
			for (Sensor s : type.getSensors()) {
				showSensors(frame, sensors, panel, start, stop, s);
			}
		}
	}
	
	private static void showSensors(JFrame frame, List<Sensor> sensors, JPanel panel, Date start, Date stop,
			Sensor s) {
		JCheckBox cac = showCheckBox(buttonOk, label, fluidList, infBound, supBound, panel, s);	
		checkBoxes.add(cac);
		cac.addActionListener(new ActionListener(){
			@Override public void 
			actionPerformed(ActionEvent e) {
				updateGraphic(frame, sensors, panel, start, stop, s, cac);	
			}
		});
	}
	
	private static JCheckBox showCheckBox(JButton buttonOk, JLabel label, JComboBox<String> fluidList,
			JSpinner infBound, JSpinner supBound, JPanel panel, Sensor s) {
		JCheckBox cac = new JCheckBox(s.getName());
		checkBoxes.add(cac);
		panel.removeAll();
		showBasicPanel(label, fluidList, infBound, supBound, panel,buttonOk);
		for(JCheckBox cb : checkBoxes) {
			panel.add(cb);
		}
		panel.revalidate();
		panel.repaint();
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
		line2.add(new JLabel("Date d�but : "));
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

	public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}
	
	private static void updateGraphic(JFrame frame, List<Sensor> sensors, JPanel panel, Date start, Date stop, Sensor s, JCheckBox cac) {
		if(cac.isSelected()) {
			sensors.add(s);
		}else {
			sensors.remove(s);
		}
		
		panel.removeAll();
		if(sensors.size()<3) {
			for(JCheckBox cb : checkBoxes) {
				cb.setEnabled(true);
			}
		}
		showBasicPanel(label, fluidList, infBound, supBound, panel,buttonOk);
		for(JCheckBox cb : checkBoxes) {
			panel.add(cb);
		}
		panel.revalidate();
		panel.repaint();
		
		if(sensors.size()==3) {
			JOptionPane.showMessageDialog(panel, "Attention vous pouvez s�lectionner 3 capteurs maximum � la fois ");
			for(JCheckBox cb : checkBoxes) {
				if(!cb.isSelected()) {
					cb.setEnabled(false);
				}
			}
		}
		Chart chart = new Chart(sensors,convertToLocalDateTimeViaInstant(start),convertToLocalDateTimeViaInstant(stop));
		ChartPanel chartPanel = chart.show(sensors,convertToLocalDateTimeViaInstant(start),convertToLocalDateTimeViaInstant(stop), frame);
		panel.add(chartPanel);
		panel.revalidate();
		panel.repaint();
	}
>>>>>>> 9f70e4f (Modif btn & timer)
}
