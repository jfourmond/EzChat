package server;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import metier.Message;

public class DialogThread extends Thread {

	private Socket socket;
	
	private InputStream is;
	private ObjectInputStream ois;
	
	private OutputStream os;
	private ObjectOutputStream oos;
	
	private boolean connected;
	
	//	CONSTRUCTEURS
	public DialogThread(Socket socket) {
		this.socket = socket;
		this.connected = true;
	}
	
	//	GETTERS
	public Socket getSocket() { return socket; }
	
	public InputStream getInputStream() { return is; }
	
	public ObjectInputStream getObjectInputStream() { return ois; }
	
	public OutputStream getOutputStream() { return os; }
	
	public ObjectOutputStream getOjectOutputStream() { return oos; }
	
	//	SETTERS
	public void setSocket(Socket socket) { this.socket = socket; }
	
	public void setInputStream(InputStream is) { this.is = is; }
	
	public void setObjectInputStream(ObjectInputStream ois) { this.ois = ois; }
	
	public void setOutputStream(OutputStream os) { this.os = os; }
	
	public void setObjectOutputStream(ObjectOutputStream oos) { this.oos = oos; }
	
	//	METHODES
	@Override
	public void run() {
		try {
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			
			os = socket.getOutputStream();
			oos = new ObjectOutputStream(os);
			
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
			
			ois.close();
			is.close();
			
			oos.close();
			os.close();
			
			socket.close();
		} catch(Exception E) {
			E.printStackTrace();
		}
	}
}
