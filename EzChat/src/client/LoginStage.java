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

public class LoginStage extends Stage {
	private final static String title = "EzChat - Login";
	
	public LoginStage() throws IOException { 
		URL fxml = getClass().getResource("login_view.fxml");  
		ResourceBundle bundle = ResourceBundle.getBundle("client.string_fr"); 
		
		FXMLLoader loader = new FXMLLoader(fxml, bundle);
		Parent root = (Parent)loader.load();
		
		LoginController controller = (LoginController)loader.getController();
		controller.setStage(this);
		
		Scene scene = new Scene(root, 300.0 ,240.0);
		
		setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				ClientLog.info("Fermeture de l'interface de Login");
			}
		});
		
		setTitle(title);
		setScene(scene);
		show();
	}
}
