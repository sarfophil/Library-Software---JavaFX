package Library.app.ui;

import java.io.IOException;

import Library.app.App;
import Library.app.business.Book;
import Library.app.business.ControllerInterface;
import Library.app.business.LibraryMember;
import Library.app.business.SystemController;
import Library.app.exception.BookNotFoundException;
import Library.app.exception.MemberNotFoundException;
import Library.app.util.Util;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class DashboardController {

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

	public DashboardController() {
		controller = new SystemController();
		bookResultTv.setText("");
		memberIdTv.setText("");
		checkoutBtn.setVisible(false);
		searchedFlag = 0;
	}
	
	@FXML
	private void OnIsbn() {
		if(isbnTf.getText().isEmpty()) {
			searchedFlag = 0;
			checkoutBtn.setVisible(false);
		}
	}
	
	@FXML
	private void onMemberIdInput() {
		if(memberIdTf.getText().isEmpty()) {
			searchedFlag = 0;
			checkoutBtn.setVisible(false);
		}
	}

	@FXML
	private void search() {
		if (!memberIdTf.getText().isEmpty() && !isbnTf.getText().isEmpty()) {
			if (controller.findAvailableBookCopy(isbnTf.getText())) {
				try {

					// Library Member
					LibraryMember libraryMember = controller.findMemberById(memberIdTf.getText());
					memberIdTv.setText(libraryMember.getFirstName() + " " + libraryMember.getLastName());

					// Book
					Book book = controller.findBookByIsbn(isbnTf.getText());
					bookResultTv.setText(book.getTitle()+": Available Copies :"+ controller.countAvailableBooks(book));
					
					//Display Checkout Button
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
		}

	}

	@FXML
	private void addMemberPage() throws IOException {
		App.setRoot("addmember");
	}

	@FXML
	private void addBook() throws IOException {
		App.setRoot("AddBook");
	}

	@FXML
	private void memberList() throws IOException {
		App.setRoot("primary");
	}

	@FXML
	private void bookListPage() throws IOException {
		App.setRoot("primary");
	}

}
