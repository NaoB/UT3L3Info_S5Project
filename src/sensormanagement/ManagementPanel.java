package sensormanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.Building;
import model.Sensor;

public class ManagementPanel extends JSplitPane {
	
	// Composants visuels
	JTree tree;
	JPanel details;
	
	public ManagementPanel() {
		super(JSplitPane.HORIZONTAL_SPLIT);
		this.add(buildTree());
		this.add(new JLabel("Hello"));
	}
	
	private JTree buildTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		List<Building> buildings = Building.fetchAll();
		for (Building building : buildings) {
			DefaultMutableTreeNode buildingNode = new DefaultMutableTreeNode("Batiment " + building.getName());
			root.add(buildingNode);
			List<Sensor> sensors = building.getSensors();
			Map<Integer, List<Sensor>> floors = new HashMap<>();
			for (Sensor sensor : sensors) {
				List<Sensor> sensorsAtFloor = floors.getOrDefault(sensor.getFloor(), new ArrayList<>());
				sensorsAtFloor.add(sensor);
				floors.put(sensor.getFloor(), sensorsAtFloor);
			}
			for (Entry<Integer, List<Sensor>> floor : floors.entrySet()) {
				DefaultMutableTreeNode floorNode = new DefaultMutableTreeNode("Etage " + floor.getKey());
				buildingNode.add(floorNode);
				for (Sensor sensor : floor.getValue()) {
					DefaultMutableTreeNode sensorNode = new DefaultMutableTreeNode(sensor.getName());
					floorNode.add(sensorNode);
				}
			}
		}
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setLeafIcon(null);
		renderer.setOpenIcon(null);
		renderer.setClosedIcon(null);
		JTree out = new JTree(root);
		out.setCellRenderer(renderer);
		out.setRootVisible(false);
		out.setShowsRootHandles(true);
		return out;
	}

	
	
}
