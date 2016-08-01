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
	
	//	SETTERS
	public void setUser(String user) { this.user = user; }
	
	public void setHost(String host) { this.host = host; }
	
	public void setPort(int port) { this.port = port; }
	
	//	METHODES
	public void connect() throws UnknownHostException, IOException {
		socket = new Socket(host, port);
		os = socket.getOutputStream();
		oos = new ObjectOutputStream(os);
	}
	
	public void send(String msg) throws IOException {
		Message M = new Message(user, msg);

		oos.writeObject(M);
	}
	
	public void disconnect() throws IOException {
		oos.close();
		os.close();
		socket.close();
	}
}
