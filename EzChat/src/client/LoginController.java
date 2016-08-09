package client;

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
			// TODO Connection et fenêtre de conversation serveur
			new CommunicationStage(client);
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
			ClientLog.severe("Erreur de connexion : " + e.getMessage());
			
			info.setText("Impossible de se connecter");
			return;
		}
		
		stage.close();
	}
	
	public static double map(double value, double start, double stop, double targetStart, double targetStop) {
		return targetStart + (targetStop - targetStart) * ((value - start) / (stop - start));
	}
}
