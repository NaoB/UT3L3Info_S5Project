import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

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

public class Project extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		
		// Lancer serveur sur port 8952
		Server server = new Server(8956);
		server.open();
		
//		SensorType EAU = new SensorType("EAU", "m3", 0, 10);
//		SensorType AIR = new SensorType("AIRCOMPRIME", "m3/h", 0, 5);
//		SensorType ELECTRICITE = new SensorType("ELECTRICITE", "kWh", 10, 500);
//		SensorType TEMPERATURE = new SensorType("TEMPERATURE", "Â°C", 17, 22);
//		EAU.save();
//		AIR.save();
//		ELECTRICITE.save();
//		TEMPERATURE.save();
		
//		Building bat1 = new Building("Batiment 1");
//		bat1.save();
//		
//		Sensor s1 = new Sensor("S1", EAU, bat1, 1, "Salle201");
//		s1.save();
//		Sensor s2 = new Sensor("S2", EAU, bat1, 2, "Salle301");
//		s2.save();
//		
//		Random rdm = new Random();
//		for (int i = 0; i < 10; i++) {
//			Data x = new Data(LocalDateTime.now(), i % 2 == 0 ? s1 : s2, rdm.nextFloat());
//			x.save();
//		}
//		
//		for (Sensor s : EAU.getSensors()) {
//			System.out.println(s.toString());
//		}
		
		
		
		// Creation fenêtre principale
		JFrame frame = new JFrame("Capteurs du campus");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 900);
				
		// Création, paramétrage et positionnement des composants
				/*TODO*/
		
		// CHART
		List<Data> list = new ArrayList<>();
		List<Sensor> sensors = new ArrayList<>();
		SensorType EAU = new SensorType("EAU", "m3", 0, 10);
		
		Building bat1 = new Building("Batiment 1");
		Sensor sensor1 = new Sensor("Capteur 1", EAU, bat1, 1, "Salle201");
		Sensor sensor2 = new Sensor("Capteur 2", EAU, bat1, 1, "Salle201");
		Sensor sensor3 = new Sensor("Capteur 3", EAU, bat1, 1, "Salle201");
		sensors.add(sensor1);sensors.add(sensor2);sensors.add(sensor3);
		
		Chart chart = new Chart(sensor1,new Date(), new Date(),list);
		ChartPanel c = chart.show(sensors, new Date(),  new Date(), list, frame);
		JPanel jpanelText = new JPanel();
		jpanelText.setLayout(new
		FlowLayout(FlowLayout.CENTER));
		
		JSplitPane jsp = new JSplitPane(0,c,jpanelText);
		jsp.setDividerLocation(450);
		frame.add(jsp);
		//frame.add(c);
	
				
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	
}
