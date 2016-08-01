package metier;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	private String author;
	private String text;

	//	CONSTRUCTEURS
	public Message(String author, String text) {
		this.author = author;
		this.text = text;
	}
	
	//	GETTERS
	public String getAuthor() { return author; }
	
	public String getText() { return text; }
	
	//	SETTERS
	public void setAuthor(String author) { this.author = author; }
	
	public void setText(String text) { this.text = text; }
	
	//	METHODES
	@Override
	public String toString() {
		String ch = "Author : " + author + "\n";
		ch += "Text : " + text + "\n";
		return ch;
	}
}
