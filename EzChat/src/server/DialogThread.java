package server;

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
	
	private boolean connected;
	
	//	CONSTRUCTEURS
	public DialogThread(Socket socket, User user, ObjectInputStream ois, ObjectOutputStream oos) throws IOException {
		this.socket = socket;
		this.connected = true;
		
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
		try {
			Message M;
			
			while(connected) {
				M = (Message)ois.readObject();
				
				if (M != null ) {
					ServerLog.info("Réception d'un message");
					System.out.println(M);
					
					// Envoi du message au client
					oos.writeObject(M);
				} else System.out.println((String)ois.readObject());
			}
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
		}
	}
}
