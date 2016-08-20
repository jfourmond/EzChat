package client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CommunicationStage extends Stage {
	private final static String title = "EzChat - ";
	
	private Client client;
	
	//	PROPERTIES
	private static final String PROPERTIES_FILE		= "./client.properties";
	private static final String PROPERTY_USERNAME = "username";
	private static final String PROPERTY_HOST = "host";
	private static final String PROPERTY_PORT = "port";
	
	//	CONSTRUCTEURS
	public CommunicationStage(Client client) throws IOException {
		this.client = client;
		
		URL fxml = getClass().getResource("CommunicationView.fxml");  
		ResourceBundle bundle = ResourceBundle.getBundle("client.string_fr"); 
		
		FXMLLoader loader = new FXMLLoader(fxml, bundle);
		Parent root = (Parent)loader.load();
		
		Scene scene = new Scene(root, 600, 400);
		
		CommunicationController controller = (CommunicationController) loader.getController();
		controller.setStage(this);
		controller.setClient(this.client);
		
		setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				ClientLog.info("Fermeture de l'interface de Communication");
				try {
					controller.disconnect();
					
					Properties properties = new Properties();
					
					FileOutputStream propertiesFile = new FileOutputStream(PROPERTIES_FILE);
					
					properties.setProperty(PROPERTY_USERNAME, client.getUser().getName());
					properties.setProperty(PROPERTY_HOST, client.getHost());
					properties.setProperty(PROPERTY_PORT, String.valueOf(client.getPort()));
					
					properties.store(propertiesFile, null);
					
					propertiesFile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		setTitle(title + client.getUser().getName());
		setScene(scene);
		show();
		
		controller.start();
	}
	
	//	GETTERS
	public Client getClient() { return client; }
	
	//	SETTERS
	public void setClient(Client client) { this.client = client; }
}
