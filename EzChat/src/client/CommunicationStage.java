package client;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CommunicationStage extends Stage {
	private final static String title = "EzChat - Login";
	
	private Client client;
	
	//	CONSTRUCTEURS
	public CommunicationStage(Client client) throws IOException {
		this.client = client;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("communication_view.fxml"));
		Parent root = (Parent)loader.load();
		
		CommunicationController controller = (CommunicationController)loader.getController();
		controller.setStage(this);
		controller.setClient(this.client);
		
		Scene scene = new Scene(root, 300, 200);
		
		setTitle(title);
		setScene(scene);
		show();
	}
	
	//	GETTERS
	public Client getClient() { return client; }
	
	//	SETTERS
	public void setClient(Client client) { this.client = client; }
}
