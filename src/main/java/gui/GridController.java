package gui;

import ClientSide.PeerDrumClient;
import Domain.DrumSet;
import Domain.TimeStep;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;


public class GridController implements DrumSetListener {
	
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
		client.addListener(this);
	}
    
	/**
	 * Called when the user clicks a panel.
	 */
	@FXML
	public void handlePanelClick(ActionEvent e) {
		ToggleButton button = (ToggleButton) e.getSource();
		String buttonId = button.idProperty().getValue();
		client.setStepAndBroadcast(0, Integer.parseInt(buttonId), button.isSelected());
	}

	@FXML
	private void handleSyncClick() {
		client.sync();
	}

	@Override
	public void updateDrumSet() {
		DrumSet drumSet = this.client.drumSet;
		//for (DrumTrack track : drumSet.tracks) {
			for (int i = 0; i < drumSet.tracks.get(0).getSteps().size(); i++) {
				Node node = panelGrid.getChildren().get(i);
			    ToggleButton toggleButton = (ToggleButton) node;
				TimeStep timeStep = drumSet.tracks.get(0).getSteps().get(i);
				toggleButton.setSelected(timeStep.isSet);
			}
		//}
	}
}