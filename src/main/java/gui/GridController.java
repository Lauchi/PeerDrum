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
		client = new PeerDrumClient("127.0.0.1", 9001, 128);
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
		int buttonIdParsed = Integer.parseInt(buttonId);
		int stepNo = buttonIdParsed % 16;
		int channelNo = buttonIdParsed / 16;
		client.setStepAndBroadcast(channelNo, stepNo, button.isSelected());
	}

	@Override
	public void updateDrumSet() {
		DrumSet drumSet = this.client.drumSet;
		for (int outer = 0; outer < drumSet.tracks.size(); outer++) {
			for (int inner = 0; inner < drumSet.tracks.get(outer) .getSteps().size(); inner++) {
				Node node = panelGrid.getChildren().get(inner + outer * 16);
			    ToggleButton toggleButton = (ToggleButton) node;
				TimeStep timeStep = drumSet.tracks.get(outer).getSteps().get(inner);
				toggleButton.setSelected(timeStep.isSet);
			}
		}
	}
	@FXML
	public void handleNoteClick(ActionEvent actionEvent) {
		ToggleButton button = (ToggleButton) actionEvent.getSource();
		String buttonId = button.idProperty().getValue();
		int buttonIdParsed = Integer.parseInt(buttonId);
		int stepNo = buttonIdParsed % 16;
		client.sendNote(stepNo);
	}
}