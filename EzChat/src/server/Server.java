package server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import dao.DAOFactory;
import dao.UserDAO;
import dao.UserDAOImpl;
import metier.User;

public class Server {
	private static List<DialogThread> sockets;
	public static UserDAO userDAO;
	
	private static int port = 7030;
	
	private static boolean launch = true;
	
	public static void main(String[] args) throws Exception {
		InetAddress ad = InetAddress.getLocalHost();
		ServerLog.info("Lancement du serveur : " + ad);
		
		userDAO = new UserDAOImpl(DAOFactory.getInstance());
		
		// Affichage des utilisateurs enregistrés dans la base de données
		List<User> users = userDAO.getAllUsers();
		for(User user : users)
			System.out.println(user);
		
		sockets = new ArrayList<>();
		
		ServerSocket server = new ServerSocket(port);
		
		while(launch) {	// TODO stopper la boucle
			Socket socket = server.accept();
			ServerLog.info("Nouveau contact d'un client");
			ConnectionThread ct = new ConnectionThread(socket);
			ct.start();
		}
		
		server.close();
		
		ServerLog.info("Arrêt du serveur");
	}
	
	public static void addDialog(DialogThread dt) { sockets.add(dt); }
	
	public static void removeDialog(DialogThread dt) { sockets.remove(dt); }
	
	public static int countDialog() { return sockets.size(); }
	
	public static List<DialogThread> getSockets() { return sockets; }
}
