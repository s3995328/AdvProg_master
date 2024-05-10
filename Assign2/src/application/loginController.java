package application;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class loginController {
	@FXML
	private TextField usernameTextBox;
	@FXML
	private TextField passowrdTextBox;

	// Event Listener on Button.onAction
	@FXML
	public void loginClick(ActionEvent event) {
		Stage mainWindow = (Stage) usernameTextBox.getScene().getWindow();
		String userName = usernameTextBox.getText();
		mainWindow.setTitle(userName);
	}
}
