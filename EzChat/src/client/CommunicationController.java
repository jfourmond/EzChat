package client;

import java.io.IOException;
import java.io.ObjectInputStream;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import metier.Message;

public class CommunicationController extends Thread {
	private Stage stage;
	private Client client;
	
	@FXML private JFXTextArea messageList;
	@FXML private JFXTextField messageField;
	@FXML private Text info;
	@FXML private JFXButton sendButton;

	private ObjectInputStream ois;
	
	//	GETTERS
	public Stage getStage() { return stage; }
	
	public Client getClient() { return client; }
	
	//	SETTERS
	public void setStage(Stage stage) { this.stage = stage; }

	public void setClient(Client client) { this.client = client; }
	
	//	METHODES
	@FXML protected void initialize() {
		sendButton.setDefaultButton(true);
	}
	
	
	@FXML
	protected void send(ActionEvent event) throws IOException {
		ClientLog.info("Envoi d'un message");
		boolean b = client.send(messageField.getText());
		
		if(b) messageField.clear();
		else info.setText("Echec de l'envoi du message.");
	}
	
	@Override
	public void run() {
		try {
			ois = client.getObjectInputStream();
			
			Message M;
			
			while(true) {
				M = (Message) ois.readObject();
				
				if (M != null ) append(M);
			}
		} catch (Exception e) { ClientLog.warning(e.getMessage()); }
	}
	
	public void disconnect() throws IOException { ois.close(); }
	
	private void append(Message M) {
		messageList.appendText(M.getUser() + " > " + M.getText() + "\n");
	}
}
