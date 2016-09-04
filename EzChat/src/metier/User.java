package metier;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String password;
	private byte[] salt;
	
	private Date inscriptionDate;
	private Date lastConnexion;
	
	private int countMessage;
	private Date lastMessage;
	
	//	CONSTRUCTEURS
	public User() { }
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
		this.countMessage = 0;
	}
	
	public User(User user) {
		this.name = user.name;
	}
	
	//	GETTERS
	public int getId() { return id; }
	
	public String getName() { return name; }
	
	public String getPassword() { return password; }
	
	public byte[] getSalt() { return salt; }
	
	public int getCountMessage() { return countMessage; }
	
	public Date getInscriptionDate() { return inscriptionDate; }
	
	public Date getLastConnexion() { return lastConnexion; }
	
	public Date getLastMessage() { return lastMessage; }
	
	//	SETTERS
	public void setId(int id) { this.id = id; }
	
	public void setName(String name) { this.name = name; }
	
	public void setPassword(String password) { this.password = password; }
	
	public void setSalt(byte[] salt) { this.salt = salt; }
	
	public void setCountMessage(int countMessage) { this.countMessage = countMessage; }
	
	public void setInscriptionDate(Date inscriptionDate) { this.inscriptionDate = inscriptionDate; }
	
	public void setLastConnexion(Date lastConnexion) { this.lastConnexion = lastConnexion; }
	
	public void setLastMessage(Date lastMessage) { this.lastMessage = lastMessage; }
	
	//	METHODES
	public void setUser(User user) { this.name = user.name; }
	
	@Override
	public String toString() {
		String ch = "Utilisateur : \n";
		ch += "\tID : " + id + "\n";
		ch += "\tNom : " + name + "\n";
		ch += "\tMot de passe : " + password + "\n";
		ch += "\tSel : " + salt + "\n";
		ch += "\tDate d'inscription : " + inscriptionDate + "\n";
		ch += "\tDernière connexion : " + lastConnexion + "\n";
		ch += "\tDate du dernier message : " + lastMessage + "\n";
		ch += "\t" + countMessage + " messages envoyés\n";
		return ch;
	}
}
