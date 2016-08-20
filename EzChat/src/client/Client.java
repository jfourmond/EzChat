package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.util.Pair;
import metier.Command;
import metier.Message;
import metier.NewUserDemand;
import metier.User;

public class Client {
	private User user;
	private String host = "localhost";
	private int port = 7030;
	
	private Socket socket;
	private OutputStream os;
	private ObjectOutputStream oos;
	
	private InputStream is;
	private ObjectInputStream ois;
	
	//	CONSTRUCTEURS
	public Client(String host, int port) throws UnknownHostException, IOException {
		this.host = host;
		this.port = port;
		
		connect();
	}
	
	//	GETTERS
	public User getUser() { return user; }
	
	public String getHost() { return host; }
	
	public int getPort() { return port; }
	
	public Socket getSocket() { return socket; }
	
	public boolean isConnected() { return socket.isConnected(); }
	
	public InputStream getInputStream() { return is; }
	
	public ObjectInputStream getObjectInputStream() { return ois; }
	
	public OutputStream getOutputStream() { return os; }
	
	public ObjectOutputStream getObjectOutputStream() { return oos; }
	
	//	SETTERS
	public void setUser(User user) { this.user = user; }
	
	public void setHost(String host) { this.host = host; }
	
	public void setPort(int port) { this.port = port; }
	
	public void setSocket(Socket socket) { this.socket = socket; }
	
	//	METHODES
	public void connect() throws UnknownHostException, IOException {
		socket = new Socket(host, port);
		os = socket.getOutputStream();
		oos = new ObjectOutputStream(os);
		
		is = socket.getInputStream();
		ois = new ObjectInputStream(is);
		
		ClientLog.info("Connexion sur " + host + ":" + port + " réussie.");
	}
	
	public void authentificate(String username, String password) throws Exception {
		Pair<String, String> p = new Pair<String, String>(username, password);
		// Envoi des identifiants
		oos.writeObject(p);
		// Lecture du resultat de l'authentification
		User user = (User) ois.readObject();
		
		if(user != null)
			setUser(user);
		else {
			disconnect();
			throw new Exception("L'utilisateur n'existe pas.");
		}
		System.out.println(this.user);
	}
	
	public void signIn(NewUserDemand demand) throws IOException, ClassNotFoundException {
		// Envoi de la demande
		oos.writeObject(demand);
		
		// Lecture du résultat de l'enregistrement
		demand = (NewUserDemand) ois.readObject();
		System.out.println(demand);
	}
	
	public boolean sendMessage(String message) {
		Message M = new Message(user.getName(), message);
		try {
			oos.writeObject(M);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean sendCommand(String command) {
		Command C = new Command(command);
		try {
			oos.writeObject(C);
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
