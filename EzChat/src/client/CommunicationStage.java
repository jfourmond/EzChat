package client;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CommunicationStage extends Stage {
	private final static String title = "EzChat - Login";
	
	private Client client;
	
	//	CONSTRUCTEURS
	public CommunicationStage(Client client) throws IOException {
		this.client = client;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CommunicationView.fxml"));
		Parent root = (Parent)loader.load();
		
		Scene scene = new Scene(root, 600, 400);
		
		CommunicationController controller = (CommunicationController) loader.getController();
		controller.setStage(this);
		controller.setClient(this.client);
		
		setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				ClientLog.info("Fermeture de l'interface de Communication");
				try {
					controller.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		setTitle(title);
		setScene(scene);
		show();
		
		controller.start();
	}
	
	//	GETTERS
	public Client getClient() { return client; }
	
	//	SETTERS
	public void setClient(Client client) { this.client = client; }
}
