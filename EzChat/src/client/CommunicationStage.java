package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CommunicationStage extends Stage {
	private final static String title = "EzChat - ";
	
	private Client client;
	
	//	CONSTRUCTEURS
	public CommunicationStage(Client client) throws IOException {
		this.client = client;
		
		URL fxml = getClass().getResource("CommunicationView.fxml");  
		ResourceBundle bundle = ResourceBundle.getBundle("client.string_fr"); 
		
		FXMLLoader loader = new FXMLLoader(fxml, bundle);
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
		
		setTitle(title + client.getUser().getName());
		setScene(scene);
		show();
		
		controller.start();
	}
	
	//	GETTERS
	public Client getClient() { return client; }
	
	//	SETTERS
	public void setClient(Client client) { this.client = client; }
}
