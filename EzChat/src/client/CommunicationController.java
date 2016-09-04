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
import metier.Command;
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
		String text = messageField.getText();
		
		boolean b;
		if(text.startsWith("/")) {
			// Traitement d'une commande console
			ClientLog.info("Envoi d'une commande");
			b = client.sendCommand(text.substring(1));
			
			if(b) messageField.clear();
			else info.setText("Echec de l'envoi de la commande.");
		} else {
			ClientLog.info("Envoi d'un message");
			b = client.sendMessage(messageField.getText());
			
			if(b) messageField.clear();
			else info.setText("Echec de l'envoi du message.");
		}
	}
	
	@Override
	public void run() {
		try {
			ois = client.getObjectInputStream();
			
			Message M;
			Command C;
			
			while(true) {
				Object O = ois.readObject();
				if(O instanceof Message) {
					M = (Message) O;
					if (M != null ) append(M);
				} else if(O instanceof Command) {
					C = (Command) O;
					if(C != null) append(C);
				}
			}
		} catch (Exception e) { ClientLog.warning(e.getMessage()); }
	}
	
	public void disconnect() throws IOException { ois.close(); }
	
	private void append(Message M) {
		messageList.appendText(M.getDate() + " - " + M.getUser() + " > " + M.getText() + "\n");
	}
	
	private void append(Command C) {
		messageList.appendText(C.getResult());
	}
}
