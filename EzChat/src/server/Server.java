package server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static int port = 7030;
	
	private static boolean launch;
	
	public static void main(String[] args) throws Exception {
		InetAddress ad = InetAddress.getLocalHost();
		ServerLog.info("Lancement du serveur : " + ad);
		
		launch = true;
		
		ServerSocket server = new ServerSocket(port);
		
		while(launch) {	// TODO stopper la boucle
			Socket socket = server.accept();
			DialogThread dt = new DialogThread(socket);
			dt.start();
		}
		
		server.close();
		
		ServerLog.info("Arrêt du serveur");
	}

}
