package Library.app.ui;

import java.io.IOException;

import Library.app.App;
import Library.app.business.ControllerInterface;
import Library.app.business.SystemController;
import Library.app.exception.LoginException;
import Library.app.util.Util;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginContoller {
	@FXML
	private TextField uid;

	@FXML
	private PasswordField pass;

	@FXML
	private Button login;

	@FXML
	private void submit() throws IOException {
		ControllerInterface c = new SystemController();
		if (uid.getText().isEmpty() || pass.getText().isEmpty())
			Util.showAlertMessage(AlertType.WARNING, "Authentication err", "Invalid Username / Password");
		else {
			try {
				c.login(uid.getText(), pass.getText());
				App.setRoot("dashboard");
			} catch (LoginException e) {
				Util.showAlertMessage(AlertType.WARNING, "Authentication err", "Invalid Username / Password");
			}
		}

	}

}
