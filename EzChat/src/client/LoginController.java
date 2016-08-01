package client;

import java.io.IOException;
import java.net.UnknownHostException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {
	private Stage stage;
	
	@FXML private TextField userField;
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
		Client client;
		
		String user = userField.getText();
		String host = hostField.getText();
		String port = portField.getText();
		
		ClientLog.info("Tentative de connection : " + user + " sur " + host + ":" + port);
		
		try {
			client = new Client(user, host, port);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		stage.close();
	}
}
