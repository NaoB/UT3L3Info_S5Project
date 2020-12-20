package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private int port = 5555;
	private ServerSocket server = null;
	private boolean isRunning = true;
	
	public Server(int port) {
		this.port = port;
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void open() {

		// Toujours dans un thread à part vu qu'il est dans une boucle infinie
		Thread t = new Thread(new Runnable() {
			public void run() {
				System.out.println("Server started on port " + port);
				while (isRunning == true) {

					try {
						// On attend une connexion d'un client
						Socket client = server.accept();

						// Une fois reçue, on la traite dans un thread séparé
						System.out.println("Connexion cliente reçue.");
						Thread t = new Thread(new ClientProcessor(client));
						t.start();

					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
					server = null;
				}
			}
		});

		t.start();
	}

}