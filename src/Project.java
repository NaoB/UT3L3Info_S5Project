import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
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
				
		// Creation fenêtre principale
		JFrame frame = new JFrame("Capteurs du campus");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 900);
				
		// Création, paramétrage et positionnement des composants
		
		// Visualisation a posteriori
		List<Sensor> sensors = Sensor.fetchAll();
		List<Data> list = new ArrayList<>();
	
		Chart chart = new Chart(sensors,new Date(), new Date(),list);
		ChartPanel chartPanel = chart.show(sensors, new Date(),  new Date(), frame);
		
		JLabel label=new JLabel("Visualisation des données a posteriori");
		String[] fluids = {"EAU","ELECTRICITE","TEMPERATURE","AIR COMPRIME"};
		JComboBox<String> fluidList = new JComboBox<>(fluids);
		
		JSpinner infBound = new JSpinner();
		JSpinner supBound = new JSpinner();


		JPanel jpanelText = new JPanel();
		jpanelText.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		// Panel pour les deux blocs à  gauche (Temps Reel et Gestion)
		JSplitPane splitHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JPanel(), new ManagementPanel());
		
		// Panel pour le précedent et les graphiques
		JSplitPane splitVertical = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitHorizontal, chartPanel);
		
		chartPanel.add(label);
		chartPanel.add(new JLabel("Fluide"));
		chartPanel.add(fluidList);
		chartPanel.add(new JLabel("Date début (secondes)"));
		chartPanel.add(infBound);
		chartPanel.add(new JLabel("Date fin (secondes)"));
		chartPanel.add(supBound);
		chartPanel.add(new JButton("OK"));
		
		
		frame.add(splitVertical);

		splitHorizontal.setDividerLocation(frame.getHeight() / 2);
		splitVertical.setDividerLocation(frame.getWidth() / 2);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	
}
