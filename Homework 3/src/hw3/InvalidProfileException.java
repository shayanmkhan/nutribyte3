//Name: Shayan Khan
//AndrewID: shayank

package hw3;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;

@SuppressWarnings("serial")
public class InvalidProfileException extends RuntimeException{
	
	InvalidProfileException(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText("Profile Data Error" );
		alert.setTitle("NutriByte 3.0");
		alert.setContentText(message);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.showAndWait();
	}
}
