package sensormanagement;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
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
	private JPanel treeContainer;
	
	public ManagementPanel() {
		super(JSplitPane.HORIZONTAL_SPLIT);
		
		ActionListener onRefreshClick = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Refreshing tree...");
				refreshTree();
			}
		};
		
		createTree();
		treeContainer = new JPanel(new BorderLayout());
		treeContainer.setBorder(new EmptyBorder(5, 5, 5, 5));
		JButton refreshButton = new JButton("Rafraichir");
		refreshButton.addActionListener(onRefreshClick);
		treeContainer.add(tree, BorderLayout.PAGE_START);
		treeContainer.add(refreshButton, BorderLayout.PAGE_END);
		this.add(treeContainer);
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
		treeContainer.remove(tree);
		createTree();
		treeContainer.add(tree, BorderLayout.PAGE_START);
		revalidate();
		repaint();
	}
}
