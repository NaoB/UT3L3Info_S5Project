package sensormanagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.Timer;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import model.Sensor;

public class ManagementPanel extends JSplitPane {
	
	private static final long serialVersionUID = -118482075845316111L;

	// Composants visuels
	private JTree tree;
	private DetailsPanel details;
	
	private int delay = 2000;
	
	public ManagementPanel() {
		super(JSplitPane.HORIZONTAL_SPLIT);
		
		createTree();
		createDetails();
		
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("UI updated");
			}
		};
		
		Timer timer = new Timer(delay, action);
		timer.start();
	}
	
	private void createTree() {
		// Arbre des capteurs
		tree = new JTree(new SensorTreeModel());
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				treeOnSelection(tree.getLastSelectedPathComponent());
			}
		});
		this.add(tree);
	}
	
	private void createDetails() {
		// Panneau des details
		details = new DetailsPanel();
		this.add(details);
	}
	
	private void treeOnSelection(Object node) {
		if (node instanceof Sensor) {
			details.setSensor((Sensor) node);
		}
	}
}
