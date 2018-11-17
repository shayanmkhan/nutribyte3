package hw3;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

@SuppressWarnings("serial")
public class InvalidProfileException extends RuntimeException{
	
	InvalidProfileException(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText("Profile Data Error" );
		alert.setTitle("NutriByte 3.0");
		alert.setContentText(message );
		alert.showAndWait();
	}
}
