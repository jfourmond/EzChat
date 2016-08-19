package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
		System.out.println("Utilisateurs connectés " + Server.countDialog());
		try {
			Message M;
			
			while(true) {
				M = (Message)ois.readObject();
				
				if (M != null ) {
					ServerLog.info("Réception d'un message");
					System.out.println(M);
					
					// Envoi du message aux différents clients
					new MessageManagement(M).start();
				} else System.out.println((String)ois.readObject());
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
	
	public void send(Message M) throws IOException {
		oos.writeObject(M);
	}
}
