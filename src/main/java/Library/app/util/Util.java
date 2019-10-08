/**
 * 
 */
package Library.app.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author Group 9
 *
 */
public class Util {
	
	/**
	 * Alert Module
	 * @param alertType
	 * @param title
	 * @param message
	 */
	public static void showAlertMessage(AlertType alertType,String title,String message) {
		Alert alert  = new Alert(alertType);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
