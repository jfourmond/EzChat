package metier;

import java.io.Serializable;

public class Command implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String command;
	private String result;
	
	//	CONSTRUCTEURS
	public Command(String command) {
		this.command = command;
	}
	
	// GETTERS
	public String getCommand() { return command; }
	
	public String getResult() { return result; }
	
	// SETTERS
	public void setCommand(String command) { this.command = command; }
	
	public void setResult(String result) { this.result = result; }
	
	//	METHODES
	@Override
	public String toString() {
		String ch = "Commande : " + command + "\n";
		ch += "Resultat : " + result + "\n";
		return ch;
	}
}
