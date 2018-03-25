import ClientSide.PeerDrumClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Peerdrum.fxml"));
		Scene scene = new Scene(root);

		// Title
		primaryStage.setTitle("Peerdrum");
		
		// Szene setzen
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
         
        // Stage anzeigen
        primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
