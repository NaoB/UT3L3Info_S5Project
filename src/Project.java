import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.SpinnerModel;

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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		
		// Lancer serveur sur port 8952
		Server server = new Server(8952);
		server.open();
				
		SensorType EAU = new SensorType("EAU", "m3", 0, 10);
		SensorType AIR = new SensorType("AIRCOMPRIME", "m3/h", 0, 5);
		SensorType ELECTRICITE = new SensorType("ELECTRICITE", "kWh", 10, 500);
		SensorType TEMPERATURE = new SensorType("TEMPERATURE", "°C", 17, 22);
		
		// Creation fenêtre principale
		JFrame frame = new JFrame("Capteurs du campus");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 900); 
				
		// Création, paramétrage et positionnement des composants
		
		// Visualisation a posteriori
		List<Sensor> sensors = Sensor.fetchAll();
		List<Data> list = new ArrayList<>();
	
		Chart chart = new Chart(sensors,new Date(), new Date(),list);
		ChartPanel chartPanel = chart.show(sensors, new Date(),  new Date(), frame);
		
		JLabel label=new JLabel("Visualisation des données a posteriori",JLabel.CENTER);
		label.setSize(300,200);
		String[] fluids = {"EAU","ELECTRICITE","TEMPERATURE","AIR COMPRIME"};
		JComboBox<String> fluidList = new JComboBox<>(fluids);
		
		JSpinner infBound = new JSpinner();
		JSpinner supBound = new JSpinner();


		JPanel jpanelText = new JPanel();
		jpanelText.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		// Panel pour les deux blocs à  gauche (Temps Reel et Gestion)
		JSplitPane splitHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JPanel(), new ManagementPanel());
		
		// Panel pour le précedent et les graphiques
		JPanel panel = new JPanel();
		JSplitPane splitVertical = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitHorizontal, panel);
		
		panel.add(label);
		panel.add(new JLabel("Fluide"));
		panel.add(fluidList);
		panel.add(new JLabel("Date début (secondes)"));
		panel.add(infBound);
		panel.add(new JLabel("Date fin (secondes)"));
		panel.add(supBound);
		JButton buttonOk = new JButton("OK");
		panel.add(buttonOk);
		
		buttonOk.addActionListener(new ActionListener(){
			@Override public void 
			actionPerformed(ActionEvent e) {
				String fluid = fluidList.getSelectedItem().toString();
				switch(fluid) {
				case "EAU" : 
					for (Sensor s : EAU.getSensors()) {
						JCheckBox cac = new JCheckBox(s.getName());
						panel.add(cac);
					}
					break;
					
				case "ELECTRICITE" :
					for (Sensor s : ELECTRICITE.getSensors()) {
						JCheckBox cac = new JCheckBox(s.getName());
						panel.add(cac);
					}
					break;
					
				case "TEMPERATURE" :
					for (Sensor s : TEMPERATURE.getSensors()) {
						JCheckBox cac = new JCheckBox(s.getName());
						panel.add(cac);
					}
					break;
				default : // air comprimé
					for (Sensor s : AIR.getSensors()) {
						JCheckBox cac = new JCheckBox(s.getName());
						panel.add(cac);
					}
					break;
				}
			}
		});
		
		frame.add(splitVertical);

		splitHorizontal.setDividerLocation(frame.getHeight() / 2);
		splitVertical.setDividerLocation(frame.getWidth() / 2);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	
}
