/**
 * 
 */
package Library.app.ui;

import java.io.IOException;

import Library.app.App;
import Library.app.business.CheckoutRecord;
import Library.app.business.ControllerInterface;
import Library.app.business.SystemController;
import Library.app.exception.MemberNotFoundException;
import Library.app.util.Util;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

/**
 * @author SARFO PHILIP
 *
 */
public class SearchMemberController {
	
	private ControllerInterface controller;
	
	@FXML
	private TextField memberTv;
	
	public SearchMemberController() {
		controller = new SystemController();
	}
	
	@FXML
	private void search() {
		if(!memberTv.getText().isEmpty()) {
			try {
				CheckoutRecord checkoutRecord = controller.findCheckoutByMemberId(memberTv.getText());
				this.displayResult(checkoutRecord);
			} catch (MemberNotFoundException e) {
				Util.showAlertMessage(AlertType.WARNING, "Response", "Member not Found");
			}
		}else {
			Util.showAlertMessage(AlertType.WARNING, "Input Validation", "Please enter a member Id");
		}
	}
	
	
	@FXML
	private void onBack() throws IOException {
		App.setRoot("Dashboard");
	}
	
	private void displayResult(CheckoutRecord checkoutRecord) {
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Checkout Details");
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		Text bookTitle = new Text();
		bookTitle.setText("Book Title :"+checkoutRecord.getBook().getBook().getTitle());
		
		Text bookIsbn = new Text();
		bookTitle.setText("Book Isb :"+checkoutRecord.getBook().getBook().getIsbn());
		
		Text copyId = new Text();
		bookTitle.setText("Copy Id :"+ checkoutRecord.getBook().getCopyNum());
		
		Text memberName = new Text();
		memberName.setText("Member Name :"+ checkoutRecord.getLibraryMember().getFirstName()+" "+checkoutRecord.getLibraryMember().getLastName());
		
		
		
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
		
		dialog.getDialogPane().setContent(grid);
		
		
		grid.add(bookTitle, 0, 0);
		grid.add(bookIsbn, 0, 1);
		grid.add(copyId, 0, 2);
		grid.add(memberName, 0, 3);
		
		dialog.showAndWait();
	}

}
