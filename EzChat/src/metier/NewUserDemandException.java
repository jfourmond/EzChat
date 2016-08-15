package metier;

public class NewUserDemandException extends Exception {
	private static final long serialVersionUID = 1L;

	public NewUserDemandException() { super(); }

	public NewUserDemandException(String message) { super(message); }
	
	public NewUserDemandException(Throwable cause) { super(cause); }
	
	public NewUserDemandException(String message, Throwable cause) { super(message, cause); }
}
