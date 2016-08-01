package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {
	private Stage stage;
	
	@FXML private TextField usernameField;
	@FXML private TextField hostField;
	@FXML private TextField portField;
	@FXML private Text info;
	
	//	GETTERS
	public Stage getStage() { return stage; }
	
	//	SETTERS
	public void setStage(Stage stage) { this.stage = stage; }
	
	//	METHODES
	@FXML
	protected void connect(ActionEvent event) {
		System.out.println("Connect pressed");
	}
}
