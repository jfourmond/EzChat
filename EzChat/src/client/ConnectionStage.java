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

public class ConnectionStage extends Stage {
private final static String title = "EzChat";
	
	public ConnectionStage() throws IOException { 
		URL fxml = getClass().getResource("ConnectionView.fxml");  
		ResourceBundle bundle = ResourceBundle.getBundle("client.string_fr"); 
		
		FXMLLoader loader = new FXMLLoader(fxml, bundle);
		Parent root = (Parent)loader.load();
		
		Scene scene = new Scene(root, 600.0 ,240.0);
		
		ConnectionController controller = (ConnectionController)loader.getController();
		controller.setScene(scene);
		controller.setStage(this);
		
		setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				ClientLog.info("Fermeture de l'interface de connexion");
			}
		});
		
		setTitle(title);
		setScene(scene);
		show();
		
		ClientLog.info("Ouverture de l'interface de connexion");
	}
}
