package gui;

import javafx.fxml.FXML;


public class GridController {

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }
    
	/**
	 * Called when the user clicks a panel.
	 */
	@FXML
	private void handlePanelClick() {
	    System.out.println("Button was presssed");
	}


}