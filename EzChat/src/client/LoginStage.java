package client;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginStage extends Stage {
	private final static String title = "EzChat - Login";
	
	public LoginStage() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("login_view.fxml"));
		Parent root = (Parent)loader.load();
		
		LoginController controller = (LoginController)loader.getController();
		controller.setStage(this);
		
		Scene scene = new Scene(root, 300, 200);
		
		setTitle(title);
		setScene(scene);
		show();
	}
	
}
