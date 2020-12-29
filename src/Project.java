import javax.swing.JFrame;
import javax.swing.JOptionPane;
import network.Server;

public class Project {

	public static void main(String[] args) {
		
		// Lancer serveur sur port 8952
		Server server = new Server(8952);
		server.open();
		
		// Creation fenêtre principale
		JFrame frame = new JFrame("Capteurs du campus");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 900);
		
		// Création, paramétrage et positionnement des composants
		/*TODO*/
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
}
