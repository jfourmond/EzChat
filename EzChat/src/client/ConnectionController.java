package client;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import metier.NewUserDemand;
import metier.NewUserDemandException;

public class ConnectionController {
	private Stage stage;
	private Scene scene;
	
	@FXML private GridPane gridPane;
	
	//	SERVER PANE
	@FXML private JFXTextField hostField;
	@FXML private JFXTextField portField;
	@FXML private JFXButton contactButton;
	@FXML private Text infoServer;
	
	//	LOGIN PANE
	@FXML private JFXTextField userField;
	@FXML private JFXPasswordField passwordField;
	@FXML private HBox hbox;
	@FXML private JFXButton noAccountButton;
	@FXML private JFXButton loginButton;
	@FXML private Text infoLogin;
	
	private boolean showSignIn = false;
	//	SIGNIN PANE
	private JFXPasswordField passwordConfirmationField;
	
	private Client client;
	//	PROPERTIES
	private static final String PROPERTIES_FILE		= "./client.properties";
	private static final String PROPERTY_USERNAME = "username";
		private String username;
	private static final String PROPERTY_HOST = "host";
		private String host;
	private static final String PROPERTY_PORT = "port";
		private String port;
	
	
	//	GETTERS
	public Stage getStage() { return stage; }
	
	public Scene getScene() { return scene; }
	
	public Client getClient() { return client; }
	
	//	SETTERS
	public void setScene(Scene scene) { this.scene = scene; }
	
	public void setStage(Stage stage) { this.stage = stage; }
	
	public void setClient(Client client) { this.client = client; }
	
	//	METHODES
	@FXML
	protected void initialize() {
		Properties properties = new Properties();

		FileInputStream propertiesFile = null;
		
		try {
			propertiesFile = new FileInputStream(PROPERTIES_FILE);
			properties.load(propertiesFile);
			username = properties.getProperty(PROPERTY_USERNAME);
			host = properties.getProperty(PROPERTY_HOST);
			port = properties.getProperty(PROPERTY_PORT);
			
			propertiesFile.close();
		} catch (IOException e) {
			// e.printStackTrace();
			ClientLog.warning("Impossible de charger le fichier de propriétés " + PROPERTIES_FILE);
		}
		
		if(username != null) userField.setText(username);
		if(host != null) hostField.setText(host);
		if(port != null) portField.setText(port);
	}
	
	@FXML
	protected void logIn(ActionEvent event) {
		infoServer.setText("");
		
		String host = hostField.getText();
		String port = portField.getText();
		
		// Etablissement de la connexion
		try {
			client = new Client(host, Integer.parseInt(port));
		} catch(Exception e) {
			e.printStackTrace();
			ClientLog.severe("Erreur de connexion : " + e.getMessage());
			infoServer.setText("Impossible de se connecter");
			return;
		}
		
		ClientLog.info("Demande de connexion au serveur : " + host + " sur le port " + port);
		
		String username = userField.getText();
		String password = passwordField.getText();
		
		try {
			client.authentificate(username, password);
			new CommunicationStage(client);
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
			ClientLog.severe("Erreur de connexion : " + e.getMessage());
			infoServer.setText("Utilisateur inconnu");
			return;
		}
	}
	
	@FXML
	protected void noAccount(ActionEvent event) {
		infoServer.setText("");
		
		if(!showSignIn) {
			passwordConfirmationField = new JFXPasswordField();
			passwordConfirmationField.setPromptText("Confirmation mot de passe");
			passwordConfirmationField.setLabelFloat(true);
			
			gridPane.add(passwordConfirmationField, 4, 2, 2, 1);
			
			noAccountButton.setText("S'enregistrer");
			noAccountButton.setOnAction(new SignIn());
			
			loginButton.setText("Annuler");
			loginButton.setOnAction(new CancelSignIn());
			
			showSignIn = true;
		}
	}
	
	protected void toLogInForm() {
		infoServer.setText("");
		
		noAccountButton.setText("Non enregistré ?");
		noAccountButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				noAccount(event);
			}
		});
		
		loginButton.setText("S'authentifier");
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				logIn(event);
			}
		});
		
		gridPane.getChildren().remove(passwordConfirmationField);
		
		showSignIn = false;
	}
	
	protected class SignIn implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			infoServer.setText("");
			
			ClientLog.info("Demande de création d'utilisateur");
			
			String host = hostField.getText();
			String port = portField.getText();
			
			String username = userField.getText();
			String password = passwordField.getText();
			String passwordConfirmation = passwordConfirmationField.getText();
			
			// 	Connexion au serveur
			try {
				client = new Client(host, Integer.parseInt(port));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
			NewUserDemand demand = new NewUserDemand(username, password, passwordConfirmation);
			
			if(!demand.isExceptionEmpty()) {
				String ch = "";
				for(NewUserDemandException e : demand.getExceptions())
					ch += e.getMessage() + "\n";
				infoLogin.setText(ch);
			} else {
				try {
					client.signIn(demand);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(demand.isExceptionEmpty()) {
					infoLogin.setText("Enregistrement validé.");
					toLogInForm();
				} else {
					String ch = "";
					for(NewUserDemandException e : demand.getExceptions())
						ch += e.getMessage() + "\n";
					infoLogin.setText(ch);
				}
			}
		}
	}
	
	protected class CancelSignIn implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			toLogInForm();
		}
	}
}