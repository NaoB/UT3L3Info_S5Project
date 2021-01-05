import model.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class RealTimePanel extends JPanel {
	JComboBox<String> dataFilter;
	JTable dataTable;
	Object col[] = {"Capteur","Type Fluide","Bâtiment","Etage","Piece","Valeur"};
	JLabel alertLabel=new JLabel("",SwingConstants.CENTER);
	ArrayList<Integer> alerts = new ArrayList<>();
	public RealTimePanel() {
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		this.add(main);
		JPanel north = new JPanel();
		north.setLayout(new GridLayout(2,1));
		JLabel title = new JLabel("AFFICHAGE TEMPS REEL",SwingConstants.CENTER);
		title.setFont(new Font("Verdana",Font.BOLD,20));

		north.add(title);
		
		JPanel selecteur = new JPanel();
		selecteur.setLayout(new BorderLayout());
		
		JPanel labels = new JPanel();
		labels.setLayout(new BorderLayout());
		labels.add(new JLabel("Selection des données filtre"),BorderLayout.WEST);
		labels.add(new JLabel("Selection du type du filtre"),BorderLayout.EAST);
		
		selecteur.add(labels, BorderLayout.NORTH);
		dataFilter = new JComboBox<>();
		dataFilter.add(new JLabel(FilterEnum.BUILDING.toString()));
		dataFilter.add(new JLabel(FilterEnum.SENSOR_TYPE.toString()));
		
		selecteur.add(dataFilter,BorderLayout.WEST);
		ButtonGroup typeFilter = new ButtonGroup();
		JRadioButton bat = new JRadioButton("Batiment",true);
		JRadioButton fluide = new JRadioButton("Fluide",false);
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(2,1));
		
		typeFilter.add(bat);
		typeFilter.add(fluide);
		buttons.add(bat);
		buttons.add(fluide);
		selecteur.add(buttons,BorderLayout.EAST);
		north.add(selecteur); 
		
		main.add(north,BorderLayout.NORTH);
		
		Object[][] row = {{1,2,3,4,5,6}};
		dataTable = new JTable(row,col);
		SensorType EAU = new SensorType("EAU", "m3", 1, 10);
		Building bat1 = new Building("Batiment 1");
		afficherDonnees(new Sensor[] {new Sensor("Capteur 1", EAU, bat1, 1, "Salle201"),new Sensor("Capteur 2", EAU, bat1, 1, "Salle201"),new Sensor("Capteur 3", EAU, bat1, 1, "Salle201")});
		
		JScrollPane scrollPane= new  JScrollPane(dataTable);
		main.add(scrollPane,BorderLayout.CENTER);
		
		main.add(alertLabel,BorderLayout.SOUTH);
	}
	
	
	public void afficherDonnees(Sensor[] sensors) {
		Object[][] data = new Object[sensors.length][6];
		
		for (int i = 0; i < sensors.length; i++) {
			data[i][0] = sensors[i].getName();
			data[i][1] = sensors[i].getSensorType().getName();
			data[i][2] = sensors[i].getBuilding().getName();
			data[i][3] = sensors[i].getFloor();
			data[i][4] = sensors[i].getLocation();
			data[i][5] = sensors[i].getValue();
			if((sensors[i].getValue()<sensors[i].getMin()||sensors[i].getValue()>sensors[i].getMax())) {
				if(!(alerts.contains(i)))
					alerts.add(i);
			}
			else {
				if(alerts.contains(i))
					alerts.remove(i);
			}
		}
		dataTable = new JTable(data, col);
		toggleAlert();
	}
	
	public void toggleAlert() {
		if(alerts.isEmpty()) {
			alertLabel.setText("");
			
		}
		else {
			String alert = "ATTENTION LES CAPTEURS "+alerts.toString()+" ONT DES VALEURS ANORMALES";
			alertLabel.setText(alert);
			alertLabel.setForeground(Color.red);
			alertLabel.setFont(new Font("Verdana",Font.BOLD,20));
		}
	}



}

