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

import graphic.GraphicPanel;
import model.Building;
import model.Data;
import model.Sensor;
import model.SensorType;

import org.jfree.chart.ChartFactory;

import network.Server;
import sensormanagement.ManagementPanel;

public class Project extends JFrame {

	public static void main(String[] args) {
		
		// Lancer serveur sur port 8952
		Server server = new Server(8952);
		server.open();

		// Creation fenetre principale
		JFrame frame = new JFrame("Capteurs du campus");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 900); 
				
		// Creation, parametrage et positionnement des composants
		// Panel pour les deux blocs a gauche (Temps Reel et Gestion)
		JSplitPane splitHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JPanel(), new ManagementPanel());
		
		// Visualisation a posteriori	

		JPanel jpanelText = new JPanel();
		jpanelText.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		// Panel pour le precedent et les graphiques
		JSplitPane splitVertical = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitHorizontal, new GraphicPanel());
		
		frame.add(splitVertical);
		//frame.add(new RealTimePanel());
		splitHorizontal.setDividerLocation(frame.getHeight() / 2);
		splitVertical.setDividerLocation(frame.getWidth() / 2);
	
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}
	
}
