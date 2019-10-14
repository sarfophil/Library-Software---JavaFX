/**
 * 
 */
package Library.app.ui;

import java.io.IOException;
import java.util.List;

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
				List<CheckoutRecord> checkoutRecord = controller.findCheckoutByMemberId(memberTv.getText());
				this.printConsoleTableHeader();
				checkoutRecord.forEach(checkout->{
					System.out.println(checkout);
				});
				Util.showAlertMessage(AlertType.INFORMATION, "Result", "Checkout Printed to console!");
			} catch (MemberNotFoundException e) {
				Util.showAlertMessage(AlertType.WARNING, "Response", "Member not Found");
			}
		}else {
			Util.showAlertMessage(AlertType.WARNING, "Input Validation", "Please enter a member Id");
		}
	}
	
	private void printConsoleTableHeader() {
		System.out.format("%32s%32s%16s%32s%16s%16s%16s", "Checkout Id","Book Id","Book Title","ISBN","Checkout Date","Due Date","Status");
		System.out.println("\n");
	}
	
	@FXML
	private void back() throws IOException {
		App.setRoot("SearchForBook");
	}
	
	
	@FXML
	private void onBack() throws IOException {
		App.setRoot("Dashboard");
	}
	
	

}
