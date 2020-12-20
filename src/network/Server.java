package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private int port;
	private ServerSocket server = null;
	private boolean isRunning = true;
	
	public Server(int port) {
		this.port = port;
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			server = null;
		}
	}
	
	public void open() {
		if (server == null) {
			return;
		}
		Thread t = new Thread(new Runnable() {
			public void run() {
				System.out.println("Server started on port " + port);
				while (isRunning == true) {

					try {
						Socket client = server.accept();

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