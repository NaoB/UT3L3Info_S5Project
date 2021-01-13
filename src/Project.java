import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import graphic.GraphicPanel;
import realtime.RealTimePanel;

import network.Server;
import sensormanagement.ManagementPanel;

public class Project extends JFrame {
	private static final long serialVersionUID = 1L;

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
		JSplitPane splitHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new RealTimePanel(), new ManagementPanel());
		
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
