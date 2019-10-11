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
import javafx.scene.control.Alert.AlertType;
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
	private void back() throws IOException {
		App.setRoot("SearchForBook");
	}
	
	
	@FXML
	private void onBack() throws IOException {
		App.setRoot("Dashboard");
	}
	
	private void displayResult(CheckoutRecord checkoutRecord) {
		String message = "Book Title : "+checkoutRecord.getBook().getBook().getTitle()+
				"\n"+"Book Isb : "+checkoutRecord.getBook().getBook().getIsbn()+
				"\n"+"Copy Id : "+ checkoutRecord.getBook().getCopyNum()+" Books"+
				"\n"+"Member Name: "+ checkoutRecord.getLibraryMember().getFirstName()+" "+checkoutRecord.getLibraryMember().getLastName();
		
		Util.showAlertMessage(AlertType.INFORMATION, "Checkout Info", message);
		
	}

}
