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
		this.add(tree);
		createDetails();
		this.add(details);
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
	}
	
	private void createDetails() {
		// Panneau des details
		details = new DetailsPanel();
	}
	
	private void treeOnSelection(Object node) {
		if (node instanceof Sensor) {
			details.setSensor((Sensor) node);
		}
	}
	
	private void refreshTree() {
		this.remove(tree);
		createTree();
	}
}
