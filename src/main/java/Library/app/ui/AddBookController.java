package Library.app.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Library.app.App;
import Library.app.business.Address;
import Library.app.business.Author;
import Library.app.business.Book;
import Library.app.business.ControllerInterface;
import Library.app.business.SystemController;
import Library.app.util.Util;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AddBookController {

	@FXML
	private TextField isbn;

	@FXML
	private TextField title;

	@FXML
	private Button addauthor;

	@FXML
	private TextField checkoutlength;

	@FXML
	private TextField numofcopies;

	@FXML
	private Button save;

	@FXML
	private Button cancle;

	@FXML
	private TableView tableView;
	@FXML
	private TableColumn firstname;
	@FXML
	private TableColumn lastname;
	@FXML
	private TableColumn address;
	@FXML
	private TableColumn phonenumber;
	@FXML
	private TableColumn shortbio;

	@FXML
	private void addAuthors() {
		this.addAuthorPopUp();
	}

	private void addAuthorPopUp() {

		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Authors");
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField authorFirstName = new TextField();
		authorFirstName.setPromptText("Author's FirstName");
		// authorFirstName.setDisable(true);
		// authorFirstName.setText();

		TextField authorLastName = new TextField();
		authorLastName.setPromptText("Author's LastName");

		TextField street = new TextField();
		street.setPromptText("Street");

		TextField city = new TextField();
		city.setPromptText("City");

		TextField state = new TextField();
		state.setPromptText("State");

		TextField zip = new TextField();
		zip.setPromptText("Zip");

		TextField phoneNumber = new TextField();
		phoneNumber.setPromptText("PhoneNumber");

		TextField shortBio = new TextField();
		shortBio.setPromptText("ShortBio");

		ChoiceBox<String> credentials = new ChoiceBox<String>();
		credentials.setItems(FXCollections.observableArrayList("Yes", "NO "));

		grid.add(new Label("FirstName :"), 0, 0);
		grid.add(authorFirstName, 1, 0);

		grid.add(new Label("LastName"), 0, 1);
		grid.add(authorLastName, 1, 1);

		grid.add(new Label("Street"), 0, 2);
		grid.add(street, 1, 2);

		grid.add(new Label("City"), 0, 3);
		grid.add(city, 1, 3);

		grid.add(new Label("State"), 0, 4);
		grid.add(city, 1, 4);

		grid.add(new Label("Zip"), 0, 5);
		grid.add(zip, 1, 5);

		grid.add(new Label("Phone Number"), 0, 6);
		grid.add(phoneNumber, 1, 6);

		grid.add(new Label("Short Bio"), 0, 7);
		grid.add(shortBio, 1, 7);

		grid.add(new Label("Credentials"), 0, 8);
		grid.add(credentials, 1, 8);

		Text displayMessage = new Text();
		grid.add(displayMessage, 1, 9);

		// Set the button types.
		ButtonType addAuthorButton = new ButtonType("Add Author", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(addAuthorButton, ButtonType.CANCEL);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			switch (dialogButton.getButtonData()) {
			case OK_DONE:
				if (!authorFirstName.getText().isEmpty() && !authorLastName.getText().isEmpty()
						&& street.getText().isEmpty() && !city.getText().isEmpty() && !zip.getText().isEmpty()
						&& !phoneNumber.getText().isEmpty() && !shortBio.getText().isEmpty()
						&& !state.getText().isEmpty() && !credentials.getSelectionModel().getSelectedItem().isEmpty() ) {

					List<Author> authinfor = new ArrayList<>();
					authinfor.add(new Author(authorFirstName.getText(), authorLastName.getText(), phoneNumber.getText(),
							new Address(street.getText(), city.getText(), state.getText(), zip.getText()),
							shortBio.getText()));

//					for(Author art:authinfor) {
//						
//						firstname.setCellValueFactory(art.getFirstName());
//						firstname.cellFactoryProperty(art.getFirstName());
//					}
//
				}

				else {

					Util.showAlertMessage(AlertType.CONFIRMATION, "Warning", "All inputs are required");

				}
				break;
			default:

				break;
			}

			return null;
		});
		
		dialog.showAndWait();
	}

	@FXML
	private void submit() {

		ControllerInterface ci = new SystemController();

		if (isbn.getText().isEmpty() || title.getText().isEmpty() || checkoutlength.getText().isEmpty()
				|| numofcopies.getText().isEmpty() && tableView.hasProperties()) {
			Util.showAlertMessage(AlertType.WARNING, "Authentication err", "Field cannot be empty");

		} else {
			try {

				Integer num_copies = Integer.parseInt(numofcopies.getText().toString());
				Integer checkout_len = Integer.parseInt(checkoutlength.getText().toString());

				List<Author> auth = new ArrayList<>();

				// auth.add(new Author(authors.getText()));

				ci.addNewBook(new Book(isbn.getText(), title.getText(), checkout_len, auth));
				Util.showAlertMessage(AlertType.INFORMATION, "Success", "Book Added !");
				
			} catch (NumberFormatException e) {
				Util.showAlertMessage(AlertType.ERROR, "Warning", "Invalid CheckOutLength OR Number of Copies");
			}

		}

	}

	@FXML
	private void back() throws IOException {

		App.setRoot("dashboard");

	}
}
