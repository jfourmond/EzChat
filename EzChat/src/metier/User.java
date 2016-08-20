package metier;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String password;	// TODO Penser à crypter le mdp
	private int countMessage;
	
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
	
	public int getCountMessage() { return countMessage; }
	
	//	SETTERS
	public void setId(int id) { this.id = id; }
	
	public void setName(String name) { this.name = name; }
	
	public void setPassword(String password) { this.password = password; }
	
	public void setCountMessage(int countMessage) { this.countMessage = countMessage; }
	
	//	METHODES
	public void setUser(User user) { this.name = user.name; }
	
	@Override
	public String toString() {
		String ch = "Utilisateur : \n";
		ch += "\tID : " + id + "\n";
		ch += "\tNom : " + name + "\n";
		ch += "\tPassword : " + password + "\n";
		ch += "\t" + countMessage + " messages envoyés\n";
		return ch;
	}
}
