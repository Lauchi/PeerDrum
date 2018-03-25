package gui;

import ClientSide.PeerDrumClient;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;


public class GridController {
	
	@FXML
	private GridPane panelGrid;
	private PeerDrumClient client;

	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
		client = new PeerDrumClient("127.0.0.1", 9001, false, 128);
		client.start();
	}
    
	/**
	 * Called when the user clicks a panel.
	 */
	@FXML
	private void handlePanelClick() {
		ObservableList<Node> childlist = panelGrid.getChildren();

		for (int i = 0; i < childlist.size(); i++) {
			Node node = childlist.get(i);
			ToggleButton button = (ToggleButton) node;
			System.out.print(button.isSelected() + " ");
			client.setStep(i, button.isSelected());
		}
		
	    System.out.println("Button was presssed");
	}

	@FXML
	private void handleSyncClick() {
		client.sync();
	}
}