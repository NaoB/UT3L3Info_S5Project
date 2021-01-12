
import model.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class RealTimePanel extends JPanel {
	private JComboBox<String> dataFilter;
	private JTable dataTable;
	private JLabel alertLabel=new JLabel("",SwingConstants.CENTER);
	private Set<Integer> alerts = new HashSet<>();
	private DataModel dataModel;
	private final String[] fluids = {"EAU","ELECTRICITE","TEMPERATURE","AIR COMPRIME"};
	private String[] buildings;
	JRadioButton bat = new JRadioButton("Batiment",true);
	JRadioButton fluide = new JRadioButton("Fluide",false);
	TableRowSorter<DataModel> sorter;   

	public RealTimePanel() {
		RealTimeController c = new RealTimeController(this);
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
		dataFilter.addActionListener(c);

		selecteur.add(dataFilter,BorderLayout.WEST);
		ButtonGroup typeFilter = new ButtonGroup();
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(2,1));
		bat.addActionListener(c);
		fluide.addActionListener(c);
		bat.setEnabled(false);
		fluide.setEnabled(true);
		typeFilter.add(bat);
		typeFilter.add(fluide);
		buttons.add(bat);
		buttons.add(fluide);
		selecteur.add(buttons,BorderLayout.EAST);
		north.add(selecteur); 

		main.add(north,BorderLayout.NORTH);
		dataModel = new DataModel();
		dataTable = new JTable(dataModel);


		DataTableCellRenderer renderer = new DataTableCellRenderer();
		 for (int i = 0; i < 6; i++) {
		     dataTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
		 }
		SensorType EAU = new SensorType("EAU", "m3", 0, 10);
		Building bat1 = new Building("Batiment 1");
		updateBuildings(new String[] {bat1.getName()});

		afficherDonnees(new Sensor[] {new Sensor("Capteur 1", EAU, bat1, 1, "Salle201"),new Sensor("Capteur 2", EAU, bat1, 1, "Salle201"),new Sensor("Capteur 3", EAU, bat1, 1, "Salle201")});

		JScrollPane scrollPane= new  JScrollPane(dataTable);
		main.add(scrollPane,BorderLayout.CENTER);
		main.add(alertLabel,BorderLayout.SOUTH);
		sorter= new TableRowSorter<DataModel>(dataModel);
		dataTable.setRowSorter(sorter);
		for (int i = 0; i < 6; i++) {
			sorter.setSortable(i, false);
		}
		toggleFilter();
	}


	public void toggleFilter() {
		dataFilter.removeAllItems();
		if(bat.isSelected()) {
			for (int i = 0; i < buildings.length; i++) {
				dataFilter.addItem(buildings[i]);
			}
			bat.setEnabled(false);
			fluide.setEnabled(true);
		}
		else {
			for (int i = 0; i < fluids.length; i++) {
				dataFilter.addItem(fluids[i]);
			}
			bat.setEnabled(true);
			fluide.setEnabled(false);

		}
		filter();
	}


	public void updateBuildings(String[] buildings) {
		this.buildings=buildings;
	}


	public void afficherDonnees(Sensor[] sensors) {
		alerts=dataModel.updateData(sensors);
		toggleAlert();
	}

	public void toggleAlert() {
		if(alerts.isEmpty()) {
			alertLabel.setText("");
		}
		else {
			String alert = "ATTENTION";
			if(alerts.size() > 1)
				alert += "LES CAPTEURS "+alerts.toString()+" ONT DES VALEURS ANORMALES";
			else
				alert+= "LE CAPTEUR" + alerts.toString()+" A UNE VALEUR ANORMALE";
			alertLabel.setText(alert);
			alertLabel.setForeground(Color.red);
			alertLabel.setFont(new Font("Verdana",Font.BOLD,20));
			dataModel.setColorRow(alerts);
		}

	}
	
	public void filter() {
		if(bat.isSelected())
			sorter.setRowFilter(RowFilter.regexFilter(dataFilter.getSelectedItem().toString(), 2));
		else
			sorter.setRowFilter(RowFilter.regexFilter(dataFilter.getSelectedItem().toString(), 1));
	}



	static class DataModel extends DefaultTableModel {
		private final String col[] = {"Capteur","Type Fluide","Bâtiment","Etage","Piece","Valeur"};
		private List<Sensor> data =new ArrayList<>();
		private HashMap<Integer,Color> colors = new HashMap<>();
		int n = 0;
		@Override
		
		public int getColumnCount() {
			return 6;
		}
		
		@Override
		public int getRowCount() {
			return n;
		}


		public Set<Integer> updateData(Sensor[] sensors) {
			Set<Integer> alerts = new HashSet<>();
			for (int i = 0; i < sensors.length; i++) {
				if(!(data.contains(sensors[i])))
					data.add(sensors[i]);
				if(sensors[i].getValue()<sensors[i].getMin()||sensors[i].getValue()>sensors[i].getMax())
					alerts.add(i);
				else
					alerts.remove(i);
			}
			fireTableDataChanged();
			n=data.size();
			return alerts;
		}
		
		public void setColorRow(Set<Integer> alerts) {
			for (int i = 0; i < data.size(); i++) {
				if(alerts.contains(i))
					colors.put(i, Color.RED);
				else
					colors.put(i,Color.WHITE);
			}
			fireTableRowsUpdated(0, n-1);
		}
		
		public Color getRowColor(int row) {
			return colors.get(row);
		}
		
		public String getColumnName(int columnIndex) {
	        return col[columnIndex];
	    }
		
		public Object getValueAt(int rowIndex, int columnIndex) {
	        switch(columnIndex){
	            case 0:
	                return data.get(rowIndex).getName();
	            case 1:
	                return data.get(rowIndex).getSensorType().getName();
	            case 2:
	                return data.get(rowIndex).getBuilding().getName();
	            case 3:
	                return data.get(rowIndex).getFloor();
	            case 4:
	                return data.get(rowIndex).getLocation();
	            case 5:
	            	return data.get(rowIndex).getValue();
	            default:
	                return null;
	        }
	    }
	}
	
	static class DataTableCellRenderer extends DefaultTableCellRenderer {

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        DataModel model = (DataModel) table.getModel();
	        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        c.setBackground(model.getRowColor(row));
	        return c;
	    }
	}
}

