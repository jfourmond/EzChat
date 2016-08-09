package metier;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	private String user;
	private String text;
	private Date date;

	//	CONSTRUCTEURS
	public Message(String author, String text) {
		this.user = author;
		this.text = text;
		date = new Date();
	}
	
	//	GETTERS
	public String getUser() { return user; }
	
	public String getText() { return text; }
	
	public Date getDate() { return date; }
	
	//	SETTERS
	public void setUser(String author) { this.user = author; }
	
	public void setText(String text) { this.text = text; }
	
	public void setDate(Date date) { this.date = date; }
	
	//	METHODES
	@Override
	public String toString() {
		String ch = "Author : " + user + "\n";
		ch += "Text : " + text + "\n";
		return ch;
	}
}
