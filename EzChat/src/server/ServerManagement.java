package server;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ServerManagement extends Stage {
	private final static String title = "EzChat Server - ";
	
	//	CONSTRUCTEURS
	public ServerManagement() throws IOException {
		URL fxml = getClass().getResource("ServerView.fxml");  
		ResourceBundle bundle = ResourceBundle.getBundle("server.string_fr"); 
		
		FXMLLoader loader = new FXMLLoader(fxml, bundle);
		Parent root = (Parent)loader.load();
		
		Scene scene = new Scene(root, 600, 400);
		
		setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				
			}
		});
		
		setTitle(title);
		setScene(scene);
		show();
	}
}
