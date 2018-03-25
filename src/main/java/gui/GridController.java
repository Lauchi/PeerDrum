package gui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;


public class GridController {
	
	@FXML
	private GridPane panelGrid;

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
		ObservableList<Node> childlist = panelGrid.getChildren();
		
		for (Node node : childlist) {
			ToggleButton button = (ToggleButton) node;
			System.out.print(button.isSelected() + " ");
		}
		
	    System.out.println("Button was presssed");
	}


}