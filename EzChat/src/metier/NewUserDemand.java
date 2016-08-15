package metier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewUserDemand implements Serializable {
	private static final long serialVersionUID = 1L;

	private User user;
	
	private List<NewUserDemandException> exceptions;
	
	//	CONSTRUCTEURS
	public NewUserDemand(String username, String password, String passwordConfirmation) {
		exceptions = new ArrayList<>();
		if(!password.equals(passwordConfirmation)) 
			exceptions.add(new NewUserDemandException("Les mots de passes diffèrent"));
		else {
			user = new User(username, password);
		}
	}
	
	//	GETTERS
	public User getUser() { return user; }
	
	public List<NewUserDemandException> getExceptions() { return exceptions; }
	
	//	SETTERS
	public void setUser(User user) { this.user = user; }
	
	//	METHODES
	public void addException(NewUserDemandException exception) {
		exceptions.add(exception);
	}
	
	public boolean isExceptionEmpty() {
		return exceptions.isEmpty();
	}
	
	@Override
	public String toString() {
		String ch = "Demande : \n";
		ch += "\t" + user + "\n";
		ch += "\tExceptions : (" + exceptions.size() + ") :\n";
		for(Exception e : exceptions) {
			ch += "\t\t" + e + "\n";
		}
		return ch;
	}
}
