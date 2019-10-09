package Library.app.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Library.app.App;
import Library.app.business.Author;
import Library.app.business.Book;
import Library.app.business.ControllerInterface;
import Library.app.business.SystemController;
import Library.app.util.Util;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
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
	private void authorAdrress(){
		
		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
	}

	@FXML
	private void submit() {

		String firstName;
		String lastName;
		String street;
		String city;
		String zip;
		String phoneNumber;
		Boolean Credential;
		String shortBio;
		

		ControllerInterface ci = new SystemController();

		if (isbn.getText().isEmpty() || title.getText().isEmpty() || authors.getText().isEmpty()
				|| checkoutlength.getText().isEmpty() || numofcopies.getText().isEmpty()) {
			Util.showAlertMessage(AlertType.WARNING, "Authentication err", "Field cannot be empty");

		} else {
			List<Author> auth = new ArrayList<>();

			// auth.add(new Author(authors.getText()));

			ci.addNewBook(new Book(isbn.getText(), title.getText(), Integer.valueOf(checkoutlength.getText()), auth));
		}

	}

	@FXML
	private void back() throws IOException {

		App.setRoot("dashboard");

	}
}
