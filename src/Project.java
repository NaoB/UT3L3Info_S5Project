import network.Server;

public class Project {

	public static void main(String[] args) {
		
		Server server = new Server(8952);
		server.open();
	}
	
}
