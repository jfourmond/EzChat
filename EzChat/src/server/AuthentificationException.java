package server;

public class AuthentificationException extends Exception {
	private static final long serialVersionUID = 1L;

	public AuthentificationException() { super(); }

	public AuthentificationException(String message) { super(message); }
	
	public AuthentificationException(Throwable cause) { super(cause); }
	
	public AuthentificationException(String message, Throwable cause) { super(message, cause); }
}
