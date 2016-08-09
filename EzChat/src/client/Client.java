package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import metier.Message;

public class Client {
	private String user;
	private String host = "localhost";
	private int port = 7030;
	
	private boolean connected;
	
	private Socket socket;
	private OutputStream os;
	private ObjectOutputStream oos;
	
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
	
	public Socket getSocket() { return socket; }
	
	public boolean isConnected() { return connected; }
	
	//	SETTERS
	public void setUser(String user) { this.user = user; }
	
	public void setHost(String host) { this.host = host; }
	
	public void setPort(int port) { this.port = port; }
	
	public void setSocket(Socket socket) { this.socket = socket; }
	
	public void setConnected(boolean connected) { this.connected = connected; }
	
	//	METHODES
	public void connect() throws UnknownHostException, IOException {
		socket = new Socket(host, port);
		os = socket.getOutputStream();
		oos = new ObjectOutputStream(os);
		
		ClientLog.info("Connexion de " + user + " sur " + host + ":" + port + " réussie.");
		
		connected = true;
	}
	
	public boolean send(String msg) {
		Message M = new Message(user, msg);
		
		System.out.println(M);
		
		try {
			oos.writeObject(M);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void disconnect() throws IOException {
		oos.close();
		os.close();
		
		socket.close();
	}
}
