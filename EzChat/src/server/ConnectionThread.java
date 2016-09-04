package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

import dao.DAOException;
import javafx.util.Pair;
import metier.NewUserDemand;
import metier.NewUserDemandException;
import metier.Security;
import metier.User;

public class ConnectionThread extends Thread {
	private Socket socket;
	
	private InputStream is;
	private ObjectInputStream ois;
	
	private OutputStream os;
	private ObjectOutputStream oos;
	
	//	CONSTRUCTEURS
	public ConnectionThread(Socket socket) throws IOException {
		this.socket = socket;
		
		is = socket.getInputStream();
		ois = new ObjectInputStream(is);
		
		os = socket.getOutputStream();
		oos = new ObjectOutputStream(os);
	}
	
	//	METHODES
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		// Authentification ou enregistrement
		Pair<String, String> pair = null;
		NewUserDemand demand = null;
		User user = null;
		
		try {
			Object O = ois.readObject();
			if(O instanceof Pair<?, ?>) {
				pair = (Pair<String, String>) O;
				// Dans le cas d'une authentification
				treatAuthentification(pair, user);
			} else if(O instanceof NewUserDemand) {
				demand = (NewUserDemand) O;
				// Dans le cas d'une demande
				treatDemand(demand, user);
			} else
				ServerLog.warning("Réception d'un objet inconnu");
		} catch (Exception e1) {
			e1.printStackTrace();
			disconnect();
		}
	}
	
	public User getUser(Pair<String, String> p) throws NoSuchAlgorithmException {
		byte[] salt = Security.getSalt();
		String encryptedPassword = Security.encryptPassword(p.getValue(), salt);
		User user = Server.userDAO.find(p.getKey(), encryptedPassword);
		return user;
	}
	
	public void treatAuthentification(Pair<String, String> pair, User user) throws IOException, AuthentificationException, NoSuchAlgorithmException {
		// Récupération des informations de l'utilisateur
		user = getUser(pair);
		// Renvoi au client des informations de l'utilisateur
		oos.writeObject(user);
		// Connexion au dialogue
		if(user != null) {
			ServerLog.info("Authentification réussie de l'" + user);
			DialogThread dt = new DialogThread(socket, user, ois, oos);
			dt.start();
			Server.addDialog(dt);
		} else
			throw new AuthentificationException("Utilisateur invalide");
	}
	
	public void treatDemand(NewUserDemand demand, User user) throws Exception {
		// Récupération de la demande
		user = demand.getUser();
		if(demand.isExceptionEmpty()) {
			// Crypatage du mot de passe de l'utilisateur
			byte[] salt = Security.getSalt();
			System.out.println("Mot de passe : " + user.getPassword());
			user.setPassword(Security.encryptPassword(user.getPassword(), salt));
			System.out.println("Mot de passe crypté : " + user.getPassword());
			try {
				// Ajout de l'utilisateur de la base
				Server.userDAO.create(user);
			} catch(DAOException DAOe) {
				DAOe.printStackTrace();
				demand.addException(new NewUserDemandException(DAOe.getMessage(), DAOe.getCause()));
			} finally {
				// Renvoi au client de la demande complétée
				oos.writeObject(demand);
			}
		} else
			throw new Exception("Exception dans la demande");
	}
	
	public void disconnect() {
		try {
			oos.close();
			os.close();
			
			ois.close();
			is.close();
			
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
