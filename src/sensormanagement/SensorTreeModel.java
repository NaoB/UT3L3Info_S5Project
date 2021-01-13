package sensormanagement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import model.Building;
import model.Sensor;

class SensorTreeModel implements TreeModel {
	
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode();
	private List<Building> buildingsCache = new ArrayList<>();
	private Map<Building, List<Floor>> floorsCache = new HashMap<>();
	private Map<Floor, List<Sensor>> sensorsCache = new HashMap<>();
	
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
		loadDatas();
	}
		
	public void loadDatas() {
		System.out.println("Loading tree datas from DB...");
		buildingsCache = Building.fetchAll();
		
		for (Building building : buildingsCache) {
			SortedSet<Floor> floors = new TreeSet<>();
			for (Sensor sensor : building.getSensors()) {
				floors.add(new Floor(building, sensor.getFloor()));
			}
			ArrayList<Floor> floorsList = new ArrayList<>();
			floorsList.addAll(floors);
			floorsCache.put(building, floorsList);
		}

		for (List<Floor> floorsList : floorsCache.values()) {
			for (Floor floor : floorsList) {
				Map<String, String> search = new HashMap<>();
				search.put("building", floor.getBuilding().getName());
				search.put("floor", floor.getFloor().toString());
				sensorsCache.put(floor, Sensor.search(search));
			}
		}
	}

	@Override
	public Object getChild(Object parent, int index) {
		System.out.print("[sensormanagement.SensorTreeModel] getChild");
		if (parent instanceof DefaultMutableTreeNode) {
			// Si c'est la racine on renvoit les batiments
			Building building = buildingsCache.get(index);
			System.out.println(" building " + index + " : " + building.getName());
			return building;
		} else if (parent instanceof Building) {
			// Si c'est un batiment on renvoit les étages
			Floor floor = floorsCache.get((Building) parent).get(index);
			System.out.println(" floor " + index + " : " + floor.getFloor());
			return floor;
		} else if (parent instanceof Floor) {
			Sensor sensor = sensorsCache.get((Floor) parent).get(index);
			System.out.println(" sensor " + index + " : " + sensor.getName());
			return sensor;
		}
		return null;
	}

	@Override
	public int getChildCount(Object parent) {
		if (parent instanceof DefaultMutableTreeNode) {
			// Si c'est la racine on renvoit les batiments
			if (buildingsCache.isEmpty()) buildingsCache = Building.fetchAll();
			int count = buildingsCache.size();
			System.out.println("[sensormanagement.SensorTreeModel] getChildCount : " + count + " buildings");
			return count;
		} else if (parent instanceof Building) {
			// Si c'est un batiment on renvoit les étages
			Building building = (Building) parent;
			if (!floorsCache.containsKey(building)) {
				SortedSet<Floor> floors = new TreeSet<>();
				for (Sensor sensor : building.getSensors()) {
					floors.add(new Floor(building, sensor.getFloor()));
				}
				ArrayList<Floor> floorsList = new ArrayList<>();
				floorsList.addAll(floors);
				floorsCache.put(building, floorsList);
			}
			int count = floorsCache.get(building).size();
			System.out.println("[sensormanagement.SensorTreeModel] " + ((Building) parent).getName() + " getChildCount : " + count + " floors");
			return count;
		} else if (parent instanceof Floor) {
			Floor floor = (Floor) parent;
			if (!floorsCache.containsKey(floor)) {
				Map<String, String> search = new HashMap<>();
				search.put("building", floor.getBuilding().getName());
				search.put("floor", floor.getFloor().toString());
				sensorsCache.put(floor, Sensor.search(search));
			}
			int count = sensorsCache.get(floor).size();
			System.out.println("[sensormanagement.SensorTreeModel] " + ((Floor) parent).getFloor() + " getChildCount : " + count + " sensors");
			return count;
		}
		return 0;
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if (parent instanceof DefaultMutableTreeNode) {
			// Si c'est la racine on renvoit les batiments
			return buildingsCache.indexOf(child);
		} else if (parent instanceof Building) {
			// Si c'est un batiment on renvoit les étages
			return floorsCache.get((Building) parent).indexOf(child);
		} else if (parent instanceof Floor) {
			return sensorsCache.get((Floor) parent).indexOf(child);
		}
		return -1;
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
	public void valueForPathChanged(TreePath arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTreeModelListener(TreeModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTreeModelListener(TreeModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

}
