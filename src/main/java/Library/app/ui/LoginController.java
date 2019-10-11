package Library.app.ui;

import java.io.IOException;

import Library.app.App;
import Library.app.business.ControllerInterface;
import Library.app.business.SystemController;
import Library.app.dataaccess.TestData;
import Library.app.exception.LoginException;
import Library.app.util.Util;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
	@FXML
	private TextField uid;

	@FXML
	private PasswordField pass;

	@FXML
	private Button loginBtn;
	
	public LoginController() {
		TestData testData = new TestData();
		testData.userData();
	}

	@FXML
	private void login() throws IOException {
		ControllerInterface c = new SystemController();

		if(uid.getText().isEmpty() || pass.getText().isEmpty())
			Util.showAlertMessage(AlertType.WARNING, "Authentication err", "Please provide your User Id / Password");

		else {
			try {
				c.login(uid.getText(), pass.getText());
				App.setRoot("Dashboard");
			} catch (LoginException e) {
				Util.showAlertMessage(AlertType.WARNING, "Authentication err", "Invalid Username / Password");
			}
		}

	}

}
