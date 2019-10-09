package Library.app.ui;

import java.io.IOException;
import java.net.URL;
import java.time.DateTimeException;
import java.util.ResourceBundle;

import Library.app.App;
import Library.app.business.Book;
import Library.app.business.ControllerInterface;
import Library.app.business.LibraryMember;
import Library.app.business.SystemController;
import Library.app.exception.BookNotFoundException;
import Library.app.exception.MemberNotFoundException;
import Library.app.util.Util;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class DashboardController implements Initializable {

	private ControllerInterface controller;

	@FXML
	private Text bookResultTv;

	@FXML
	private Text memberIdTv;

	@FXML
	private TextField isbnTf;

	@FXML
	private TextField memberIdTf;

	@FXML
	private Button checkoutBtn;

	private int searchedFlag;

	private Book book;

	private LibraryMember libraryMember;

	public DashboardController() {
		controller = new SystemController();
		searchedFlag = 0;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		bookResultTv.setText("");
		memberIdTv.setText("");
		checkoutBtn.setVisible(false);
	}
	
	@FXML
	private void goToCheckoutRecordPage() throws IOException {
		App.setRoot("checkoutsearch");
	}

	@FXML
	private void OnIsbn() {
		if (isbnTf.getText().isEmpty()) {
			searchedFlag = 0;
			checkoutBtn.setVisible(false);
		}
	}

	@FXML
	private void onMemberIdInput() {
		if (memberIdTf.getText().isEmpty()) {
			searchedFlag = 0;
			checkoutBtn.setVisible(false);
		}
	}

	@FXML
	private void checkout() {
		this.checkoutComponent();
	}

	@FXML
	private void search() {
		if (!memberIdTf.getText().toString().isEmpty() && !isbnTf.getText().toString().isEmpty()) {
			if (controller.findAvailableBookCopy(isbnTf.getText())) {
				try {

					// Library Member
					libraryMember = controller.findMemberById(memberIdTf.getText());
					memberIdTv.setText(libraryMember.getFirstName() + " " + libraryMember.getLastName());

					// Book
					book = controller.findBookByIsbn(isbnTf.getText());
					bookResultTv
							.setText(book.getTitle() + ": Available Copies :" + controller.countAvailableBooks(book));

					// Display Checkout Button
					checkoutBtn.setVisible(true);

					searchedFlag = 1;

				} catch (MemberNotFoundException e) {
					Util.showAlertMessage(AlertType.WARNING, "Response", "Member Not found");
					searchedFlag = 0;
				} catch (BookNotFoundException e) {
					searchedFlag = 0;
					Util.showAlertMessage(AlertType.WARNING, "Response", "Book Unavailable");
				}
			} else {
				searchedFlag = 0;
				Util.showAlertMessage(AlertType.WARNING, "Warning", "Book Unavailable");
			}
		} else {
			Util.showAlertMessage(AlertType.WARNING, "Warning", "All Fields are required");
		}

	}

	@FXML 
	private void bookCollection() {
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Add Book Copy");
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField isbn = new TextField();
		isbn.setPromptText("");
		
		Text status = new Text();
		status.setText("");
		
		ButtonType buttonType = new ButtonType("Click to Add Copy",ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(buttonType, ButtonType.CANCEL);
		
		
		grid.add(new Label("Isbn Number :"), 0, 0);
		grid.add(isbn, 1, 0);
		
		
		dialog.setResultConverter(dialogButton->{
			if(!isbn.getText().isEmpty()) {
				try {
					Book book = controller.findBookByIsbn(isbn.getText());
					controller.addBookToCollection(book);
				} catch (BookNotFoundException e) {
					Util.showAlertMessage(AlertType.INFORMATION, "Sorry", "Book Unavailable");
				}
			}
			return null;
		});
		
		dialog.showAndWait();
		
		
	}
	
	@FXML
	private void addMember() throws IOException {
		App.setRoot("addmember");
	}

	@FXML
	private void addbook() throws IOException {
		App.setRoot("AddBook");
	}

	@FXML
	private void memberList() throws IOException {
		App.setRoot("primary");
	}

	@FXML
	private void booklist() throws IOException {
		App.setRoot("primary");
	}

	private void checkoutComponent() {
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Checkout");
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField member = new TextField();
		member.setPromptText("Member");
		member.setDisable(true);
		member.setText(libraryMember.getFirstName().toUpperCase() + " " + libraryMember.getLastName().toUpperCase());

		TextField bookIsbn = new TextField();
		bookIsbn.setPromptText("Isbn");
		bookIsbn.setDisable(true);
		bookIsbn.setText(book.getIsbn());

		DatePicker picker = new DatePicker();
		picker.setPromptText("Due Date");

		TextField fine = new TextField();
		fine.setPromptText("Fine Fee $");

		grid.add(new Label("Member :"), 0, 0);
		grid.add(member, 1, 0);

		grid.add(new Label("Isbn"), 0, 1);
		grid.add(bookIsbn, 1, 1);

		grid.add(new Label("Due Date"), 0, 2);
		grid.add(picker, 1, 2);

		grid.add(new Label("Fine Fee $"), 0, 3);
		grid.add(fine, 1, 3);

		Text displayMessage = new Text();
		grid.add(displayMessage, 1, 4);

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Checkout", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			switch (dialogButton.getButtonData()) {
			case OK_DONE:
				try {
					if (!member.getText().isEmpty() && !bookIsbn.getText().isEmpty() && picker.getValue() != null
							&& !fine.getText().isEmpty()) {
						try {
							Double fineAmt = Double.parseDouble(fine.getText().toString());
							controller.checkoutBook(libraryMember.getMemberId(), book.getIsbn(), picker.getValue(),
									fineAmt);
							resetDashboardUi();
						} catch (NumberFormatException e) {
							Util.showAlertMessage(AlertType.ERROR, "Warning", "Enter a valid amount");
						}

					} else {

						Util.showAlertMessage(AlertType.CONFIRMATION, "Warning", "All inputs are required");

					}
				}catch (DateTimeException e) {
					Util.showAlertMessage(AlertType.WARNING, "Error", "Please pick a valid date");
				}
				break;
			default:

				break;
			}

			return null;
		});

		dialog.showAndWait();

	}

	private void resetDashboardUi() {
		isbnTf.setText("");
		memberIdTf.setText("");
		memberIdTv.setText("");
		checkoutBtn.setVisible(false);
	}

}
