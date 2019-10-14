package Library.app.ui;

import java.io.IOException;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Library.app.App;
import Library.app.business.Book;
import Library.app.business.ControllerInterface;
import Library.app.business.LibraryMember;
import Library.app.business.SystemController;
import Library.app.exception.BookNotFoundException;
import Library.app.exception.MemberNotFoundException;
import Library.app.util.Util;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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

	@FXML
	private Text resultTitle;

	@FXML
	private Text role;

	@FXML
	private Button addMemberMenuBtn;

	@FXML
	private Button addBookMenuBtn;

	@FXML
	private Button checkoutMenuBtn;

	@FXML
	private Button searchMenuBtn;

	@FXML
	private Button addBookCollectionBtn;

	@FXML
	private Pane checkoutPane;
	

	private Book book;

	private LibraryMember libraryMember;

	public DashboardController() {
		controller = new SystemController();
	}

	@FXML
	private void onLogout() throws IOException {
		App.setRoot("login");
	}
	
	@FXML
	private void MemberMenu() throws IOException{
		
	}
	
	@FXML
	private void BookListMenu() throws IOException{
		
	}
	
	@FXML
	private void CheckoutMenuList() throws IOException{
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		bookResultTv.setText("");
		memberIdTv.setText("");
		resultTitle.setText("");
		checkoutBtn.setVisible(false);
		role.setText("Current User:" + Util.getCurrentPerson().getAuthorization().name());
		this.hideUIMenu();
		this.loadUIComponents();
		
	}

	private void hideUIMenu() {
		checkoutPane.setVisible(false);
		addMemberMenuBtn.setVisible(false);
		addBookMenuBtn.setVisible(false);
		checkoutMenuBtn.setVisible(false);
		searchMenuBtn.setVisible(false);
		addBookCollectionBtn.setVisible(false);
	}

	/* Determine User Role */
	private void loadUIComponents() {
		switch (Util.getCurrentPerson().getAuthorization()) {
		case LIBRARIAN:
			checkoutMenuBtn.setVisible(true);
			searchMenuBtn.setVisible(true);
			checkoutPane.setVisible(true);
			break;
		case ADMIN:
			addMemberMenuBtn.setVisible(true);
			addBookMenuBtn.setVisible(true);
			addBookCollectionBtn.setVisible(true);
			break;
		case BOTH:
			addMemberMenuBtn.setVisible(true);
			addBookMenuBtn.setVisible(true);
			checkoutMenuBtn.setVisible(true);
			searchMenuBtn.setVisible(true);
			addBookCollectionBtn.setVisible(true);
			break;
		}
	}

	@FXML
	private void goToCheckoutRecordPage() throws IOException {
		App.setRoot("checkoutsearch");
	}

	@FXML
	private void OnIsbn() {

	}

	@FXML
	private void onMemberIdInput() {

	}

	@FXML
	private void checkout() {
		this.checkoutComponent();
	}

	@FXML
	private void search() {
		if (!memberIdTf.getText().isEmpty() && !isbnTf.getText().isEmpty()) {
			if (controller.findAvailableBookCopy(isbnTf.getText())) {
				try {

					// Library Member
					libraryMember = controller.findMemberById(memberIdTf.getText());
					memberIdTv.setText(
							"Member Name: " + libraryMember.getFirstName() + " " + libraryMember.getLastName());

					// Book
					book = controller.findBookByIsbn(isbnTf.getText());
					bookResultTv.setText("Book: " + book.getTitle() + ": Available Copies : "
							+ book.countAvailableCopies() + " Copies");

					// Title
					resultTitle.setText("Result:");

					// Display Checkout Button
					if (book.countAvailableCopies() > 0) {
						checkoutBtn.setVisible(true);
					} else {
						Util.showAlertMessage(AlertType.WARNING, "Warning", "Sorry, There are no copies available");
					}

					// Checks if Library Member has checked out this book already
					if (!controller.checkLibraryCheckoutStatus(libraryMember.getMemberId(), book)) {
						checkoutBtn.setVisible(false);
						Util.showAlertMessage(AlertType.WARNING, "Warning",
								"Library Member has checked out this book already");
					}

				} catch (MemberNotFoundException e) {
					Util.showAlertMessage(AlertType.WARNING, "Response", "Member Not found");
				} catch (BookNotFoundException e) {
					Util.showAlertMessage(AlertType.WARNING, "Response", "Book Unavailable");
				}
			} else {
				Util.showAlertMessage(AlertType.WARNING, "Warning", "No book copy available");
			}
		} else {
			Util.showAlertMessage(AlertType.WARNING, "Warning", "All Fields are required");
		}

	}

	@FXML
	private void bookCollection() throws IOException {
		App.setRoot("addBookCopy");
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
	private void searchBookPage() throws IOException {
		App.setRoot("SearchForBook");
	}

	/*
	 * Checkout Dialog Box
	 */
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

		TextField dueDate = new TextField();
		dueDate.setPromptText("Due Date");
		dueDate.setDisable(true);
		dueDate.setPromptText(LocalDate.now().plusDays(book.getMaxCheckoutLength()).toString());
		
		Text checkoutLength = new Text();
		checkoutLength.setText(String.valueOf(book.getMaxCheckoutLength())+" days");

		TextField fine = new TextField();
		fine.setPromptText("Fine Fee $");

		grid.add(new Label("Member :"), 0, 0);
		grid.add(member, 1, 0);

		grid.add(new Label("Isbn"), 0, 1);
		grid.add(bookIsbn, 1, 1);

		grid.add(new Label("Due Date"), 0, 2);
		grid.add(dueDate, 1, 2);
		
		grid.add(checkoutLength, 2, 2);

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
					if (!member.getText().isEmpty() && !bookIsbn.getText().isEmpty() && dueDate.getText().isEmpty()
							&& !fine.getText().isEmpty()) {
						try {
							Double fineAmt = Double.parseDouble(fine.getText().toString());
							controller.checkoutBook(libraryMember.getMemberId(), book.getIsbn(),LocalDate.now().plusDays(book.getMaxCheckoutLength()),
									fineAmt);
							resetDashboardUi();
						} catch (NumberFormatException e) {
							Util.showAlertMessage(AlertType.ERROR, "Warning", "Enter a valid amount");
						}

					} else {

						Util.showAlertMessage(AlertType.CONFIRMATION, "Warning", "All inputs are required");

					}
				} catch (DateTimeException e) {
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
		bookResultTv.setText("");
		checkoutBtn.setVisible(false);
		resultTitle.setText("");

	}

}
