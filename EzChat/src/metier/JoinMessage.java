package metier;

import java.io.Serializable;

public class JoinMessage extends Message implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//	Constructeurs
	public JoinMessage(String user) {
		super(user, "");
	}
}
