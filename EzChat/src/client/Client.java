package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import metier.Message;

public class Client extends Thread {
	private String user;
	private String host = "localhost";
	private int port = 7030;
	
	private boolean connected;
	
	private Socket socket;
	private OutputStream os;
	private ObjectOutputStream oos;
	
	private InputStream is;
	private ObjectInputStream ois;
	
	//	CONSTRUCTEURS
	public Client(String user, String host, String port) throws UnknownHostException, IOException {
		this.user = user;
		this.host = host;
		this.port = Integer.parseInt(port);
		
		connect();
	}
	
	public Client(String user, String host, int port) throws UnknownHostException, IOException {
		this.user = user;
		this.host = host;
		this.port = port;
		
		connect();
	}
	
	//	GETTERS
	public String getUser() { return user; }
	
	public String getHost() { return host; }
	
	public int getPort() { return port; }
	
	//	SETTERS
	public void setUser(String user) { this.user = user; }
	
	public void setHost(String host) { this.host = host; }
	
	public void setPort(int port) { this.port = port; }
	
	//	METHODES
	public void connect() throws UnknownHostException, IOException {
		socket = new Socket(host, port);
		os = socket.getOutputStream();
		oos = new ObjectOutputStream(os);
		
		ClientLog.info("Connexion de " + user + " sur " + host + ":" + port + " réussie.");
		
		connected = true;
		
		start();
	}
	
	public void send(String msg) throws IOException {
		Message M = new Message(user, msg);
		
		System.out.println(M);
		
		oos.writeObject(M);
	}
	
	@Override
	public void run() {
		try {
			is = socket.getInputStream();
			ois = new ObjectInputStream(is);
			
			Message M;
			
			while(connected) {
					M = (Message)ois.readObject();
					
					if (M != null ) 
						System.out.println(M);
					else
						System.out.println((String)ois.readObject());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void disconnect() throws IOException {
		oos.close();
		os.close();
		
		ois.close();
		is.close();
		
		socket.close();
	}
}
