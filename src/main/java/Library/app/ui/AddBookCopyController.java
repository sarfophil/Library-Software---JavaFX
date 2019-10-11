package Library.app.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Library.app.App;
import Library.app.business.Book;
import Library.app.business.ControllerInterface;
import Library.app.business.SystemController;
import Library.app.exception.BookNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AddBookCopyController implements Initializable {

	private ControllerInterface controller;

	@FXML
	private Button addCopyBtn;

	@FXML
	private TextField isbnTF;

	@FXML
	private Button backBtn;

	@FXML
	private Text resultStatus;
	
	public AddBookCopyController() {
		controller = new SystemController();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addCopyBtn.setVisible(false);
		resultStatus.setVisible(false);

	}

	@FXML
	private void search() {
		if (!isbnTF.getText().isEmpty()) {
			try {
				controller.findBookByIsbn(isbnTF.getText());
				resultStatus.setVisible(true);
				resultStatus.setText("Book Found !!");
				addCopyBtn.setVisible(true);
			} catch (BookNotFoundException e) {
				resultStatus.setVisible(true);
				resultStatus.setText("Book Unavailable");
			}
		}
	}
	
	@FXML
	private void add() {
		if (!isbnTF.getText().isEmpty()) {
			Book book;
			try {
				book = controller.findBookByIsbn(isbnTF.getText());

				controller.addBookToCollection(book);
				resetUI();
			} catch (BookNotFoundException e) {
				resultStatus.setText("Book Unavailable");
			}
		} else {
			resultStatus.setText("Please enter Isbn");
		}
	}
	
	@FXML
	private void back() throws IOException {
		App.setRoot("Dashboard");
	}
	
	private void resetUI() {
		addCopyBtn.setVisible(false);
		resultStatus.setVisible(false);
		isbnTF.setText("");
	}

}
