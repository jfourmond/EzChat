package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import metier.Command;
import metier.Message;
import metier.User;

public class DialogThread extends Thread {
	private User user;
	
	private Socket socket;
	
	private ObjectInputStream ois;
	
	private ObjectOutputStream oos;
	
	//	CONSTRUCTEURS
	public DialogThread(Socket socket, User user, ObjectInputStream ois, ObjectOutputStream oos) throws IOException {
		this.socket = socket;
		this.user = user;
		
		this.ois = ois;
		
		this.oos = oos;
	}
	
	//	GETTERS
	public User getUser() { return user; }
	
	public Socket getSocket() { return socket; }
	
	//	SETTERS
	public void setUser(User user) { this.user = user; }
	
	public void setSocket(Socket socket) { this.socket = socket; }
	
	//	METHODES
	@Override
	public void run() {
		Server.userDAO.updateLastConnexion(user.getId());
		
		System.out.println("Utilisateurs connectés " + Server.countDialog());
		try {
			Message M = null;
			Command C = null;
			
			while(true) {
				Object O = ois.readObject();
				
				if(O instanceof Message) {
					// Dans le cas d'un message
					M = (Message) O;
					treatMessage(M);
				} else if(O instanceof Command) {
					// Dans le cas d'une commande
					C = (Command) O;
					treatCommand(C);
				} else
					ServerLog.warning("Réception d'un objet inconnu");
			}
		} catch(EOFException eofe) {
			// Normal... normalement
		} catch(Exception E) {
			E.printStackTrace();
		} finally {
			try {
				ois.close();
				oos.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Server.removeDialog(this);
			ServerLog.info("Déconnexion de l'" + user);
			System.out.println("Utilisateurs connectés " + Server.countDialog());
		}
	}
	
	public void sendMessage(Message M) throws IOException { oos.writeObject(M); }
	
	public void sendCommand(Command C) throws IOException { oos.writeObject(C); }
	
	private void treatMessage(Message M) throws ClassNotFoundException, IOException {
		Server.userDAO.incrementCountMessage(user.getId());
		
		if (M != null ) {
			ServerLog.info("Réception d'un message");
			// Envoi du message aux différents clients
			new MessageManagement(M).start();
		} else System.err.println("Message vide !");
	}
	
	private void treatCommand(Command C) throws IOException {
		user = Server.userDAO.findByID(user.getId());
		String command;
		if(C != null) {
			ServerLog.info("Réception d'une commande");
			command = C.getCommand();
			// Traitement de la commande
			if(command.equals("stats")) {
				String result = "Nombre de messages : " + user.getCountMessage();
				C.setResult(result);
				sendCommand(C);
			} else if(command.equals("user")) {
				String result = user + "";
				C.setResult(result);
				sendCommand(C);
			}
		} else System.err.println("Commande console vide !");
	}
}
