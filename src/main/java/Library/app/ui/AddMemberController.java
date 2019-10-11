package Library.app.ui;

import java.io.IOException;
import java.util.UUID;

import Library.app.App;
import Library.app.business.Address;
import Library.app.business.ControllerInterface;
import Library.app.business.LibraryMember;
import Library.app.business.SystemController;

import Library.app.util.Util;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddMemberController {
	@FXML
	private TextField memberId;

	@FXML
	private TextField firstname;

	@FXML
	private TextField lastname;

	@FXML
	private TextField street;

	@FXML
	private TextField city;

	@FXML
	private TextField state;

	@FXML
	private TextField zip;

	@FXML
	private TextField phonenumber;

	@FXML
	private Button addButton;

	@FXML
	private Button backButton;

	@FXML
	private void add() {
		ControllerInterface c = new SystemController();
		if (firstname.getText().isEmpty() || lastname.getText().isEmpty()
				|| street.getText().isEmpty() || city.getText().isEmpty() || state.getText().isEmpty()
				|| zip.getText().isEmpty() || phonenumber.getText().isEmpty()) {
			Util.showAlertMessage(AlertType.WARNING, "Autentication err", "All Fields are required");
		} else {
			try {
				Integer.parseInt(phonenumber.getText().toString());
				Address add = new Address(street.getText(), street.getText(), state.getText(), zip.getText());
				c.createNewLibraryMember(new LibraryMember(UUID.randomUUID().toString(), firstname.getText(),
						lastname.getText(), phonenumber.getText(), add));
				Util.showAlertMessage(AlertType.INFORMATION, "Success", "Member Created !");
				this.resetForm();
			} catch (NumberFormatException e) {
				Util.showAlertMessage(AlertType.WARNING, "Warning", "Invalid Phone number");

			}

		}

	}
	
	private void resetForm() {
		firstname.setText("");
		lastname.setText("");
		street.setText("");
		city.setText("");
		state.setText("");
		zip.setText("");
		phonenumber.setText("");
	}

	@FXML
	private void back() throws IOException {
		App.setRoot("Dashboard");
	}

}
