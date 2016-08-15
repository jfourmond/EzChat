package client;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		new ConnectionStage();
	}
	
	public static void main(String[] args) {
		ClientLog.info("Lancement du client");
		launch(args);
		ClientLog.info("Arrêt du client");
	}
}
