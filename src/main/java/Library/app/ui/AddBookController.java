package Library.app.ui;

import java.io.IOException;

import Library.app.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddBookController {

	@FXML
	private TextField isbn;

	@FXML
	private TextField title;

	@FXML
	private TextField authors;

	@FXML
	private TextField checkoutlength;

	@FXML
	private TextField numofcopies;

	@FXML
	private Button save;

	@FXML
	private Button cancle;

	@FXML
	private void submit() {

		
	}

	@FXML
	private void back() throws IOException {

		App.setRoot("dashboard");

	}
}
