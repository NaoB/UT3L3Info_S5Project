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
				
		// Creation fen�tre principale
		JFrame frame = new JFrame("Capteurs du campus");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 900);
				
		// Cr�ation, param�trage et positionnement des composants
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
		ChartPanel chartPanel = chart.show(sensors, new Date(),  new Date(), list, frame);
		JPanel jpanelText = new JPanel();
		jpanelText.setLayout(new
		FlowLayout(FlowLayout.CENTER));
		
		// Panel pour les deux blocs à gauche (Temps Reel et Gestion)
		JSplitPane splitHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JPanel(), new ManagementPanel());
		// Panel pour le précedent et les graphiques
		JSplitPane splitVertical = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitHorizontal, chartPanel);
		frame.add(splitVertical);

		splitHorizontal.setDividerLocation(frame.getHeight() / 2);
		splitVertical.setDividerLocation(frame.getWidth() / 2);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	
}
