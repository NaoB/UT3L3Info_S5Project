package sensormanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import model.Building;
import model.Sensor;

class SensorTreeModel implements TreeModel {
	
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode();
	private Map<Building, Map<Floor, List<Sensor>>> allSensors = new HashMap<>();
	
	private class Floor implements Comparable<Floor> {
		
		private Building building;
		private Integer floor;
		
		public Floor(Building building, Integer floor) {
			this.building = building;
			this.floor = floor;
		}

		public Building getBuilding() {
			return building;
		}

		public Integer getFloor() {
			return floor;
		}

		@Override
		public int compareTo(Floor arg0) {
			return floor - arg0.floor;
		}

		@Override
		public String toString() {
			return "Etage " + floor;
		}
							
	}
	
	public SensorTreeModel() {
		// Initialisation des batiments
		List<Building> buildings = Building.fetchAll();
		for (Building building : buildings) {
			allSensors.put(building, null);
			// On enregistres les capteurs dans le cache
			List<Sensor> sensorsInBuilding = building.getSensors();
			Map<Floor, List<Sensor>> floorMap = new TreeMap<>();
			for (Sensor sensor : sensorsInBuilding) {
				List<Sensor> sensorsAtFloor = floorMap.getOrDefault(new Floor(building, sensor.getFloor()), new ArrayList<>());
				sensorsAtFloor.add(sensor);
				floorMap.put(new Floor(building, sensor.getFloor()), sensorsAtFloor);
			}
			allSensors.put(building, floorMap);
		}
		
	}

	@Override
	public void addTreeModelListener(TreeModelListener arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public Object getChild(Object parent, int index) {
		if (parent instanceof DefaultMutableTreeNode) {
			// Si c'est la racine on renvoit les batiments
			return allSensors.keySet().toArray()[index];
		} else if (parent instanceof Building) {
			// Si c'est un batiment on renvoit les étages
			Building building = (Building) parent;
			return allSensors.get(building).keySet().toArray()[index];
		} else if (parent instanceof Floor) {
			Floor floor = (Floor) parent;
			return allSensors.get(floor.getBuilding()).get(floor).get(index);
		}
		return null;
	}

	@Override
	public int getChildCount(Object parent) {
		if (parent instanceof DefaultMutableTreeNode) {
			// Si c'est la racine on renvoit les batiments
			return allSensors.size();
		} else if (parent instanceof Building) {
			// Si c'est un batiment on renvoit les étages
			return allSensors.get((Building) parent).size();
		} else if (parent instanceof Floor) {
			Floor floor = (Floor) parent;
			return allSensors.get(floor.getBuilding()).get(floor).size();
		}
		return 0;
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if (parent instanceof DefaultMutableTreeNode) {
			// Si c'est la racine on renvoit les batiments
			return (new ArrayList<>(allSensors.keySet())).indexOf(child);
		} else if (parent instanceof Building) {
			// Si c'est un batiment on renvoit les étages
			return (new ArrayList<>(allSensors.get((Building) parent).keySet())).indexOf(child);
		} else if (parent instanceof Floor) {
			Floor floor = (Floor) parent;
			return allSensors.get(floor.getBuilding()).get(floor).indexOf(child);
		}
		return 0;
	}

	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public boolean isLeaf(Object arg0) {
		// Seuls les capteurs sont des feuilles
		if (arg0 instanceof Sensor) return true;
		return false;
	}

	@Override
	public void removeTreeModelListener(TreeModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueForPathChanged(TreePath arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
