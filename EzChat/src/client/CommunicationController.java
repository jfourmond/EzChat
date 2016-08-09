package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import metier.Message;

public class CommunicationController extends Thread {
	private Stage stage;
	private Client client;
	
	@FXML private TextArea messageList;
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
	private void initialize() {
		start();
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
		InputStream is;
		ObjectInputStream ois;
		
		try {
			is = client.getSocket().getInputStream();
			ois = new ObjectInputStream(is);
			
			Message M;
			
			while(client.isConnected()) {
					M = (Message)ois.readObject();
					
					if (M != null ) append(M);
					else
						System.out.println((String)ois.readObject());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void append(Message M) {
		messageList.appendText(M.getAuthor() + " > " + M.getText() + "\n");
	}
}
