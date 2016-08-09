package client;

import java.io.IOException;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CommunicationController {
	private Stage stage;
	private Client client;
	
	@FXML private TextArea message_list;
	@FXML private JFXTextField messageField;
	@FXML private Text info;
	
	//	GETTERS
	public Stage getStage() { return stage; }
	
	public Client getClient() { return client; }
	
	//	SETTERS
	public void setStage(Stage stage) { this.stage = stage; }

	public void setClient(Client client) { this.client = client; }
	
	//	METHODES
	@FXML
	protected void send(ActionEvent event) throws IOException {
		ClientLog.info("Envoi d'un message");
		client.send(messageField.getText());
	}
}
