package metier;

import java.io.Serializable;

public class QuitMessage extends Message implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//	Constructeurs
	public QuitMessage(String user, String text) {
		super(user, text);
	}
}
