package Library.app.ui;



import Library.app.business.Address;
import Library.app.business.ControllerInterface;
import Library.app.business.LibraryMember;
import Library.app.business.SystemController;

import Library.app.util.Util;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddMemberController {
	@FXML
	private TextField memberId;
	
	@FXML
	private TextField firstname;
	
	@FXML
	private TextField lastname;
	
	@FXML
	private TextField street;
	
	@FXML
	private TextField city;
	
	@FXML
	private TextField state;
	
	@FXML
	private TextField zip;
	
	@FXML
	private TextField phonenumber;
	
	@FXML
	private Button addButton;
	
	@FXML
	private Button backButton;
	
	
	
	
	@FXML
	private void add() {
		ControllerInterface c = new SystemController();
		if(memberId.getText().isEmpty()||firstname.getText().isEmpty()||lastname.getText().isEmpty()||street.getText().isEmpty()||city.getText().isEmpty()||state.getText().isEmpty()||zip.getText().isEmpty()||phonenumber.getText().isEmpty()) {
			Util.showAlertMessage(AlertType.WARNING, "Autentication err", "Invalid member information");
		}else {
			try {
				Address add = new Address(street.getText(), street.getText(), state.getText(), zip.getText());
				c.createNewLibraryMember(new LibraryMember(memberId.getText(), firstname.getText(), lastname.getText(), phonenumber.getText(), add));		
			}catch(Exception e){
				Util.showAlertMessage(AlertType.WARNING, "Autentic", "Invalid member information");
				
			}
			
		}
		
	}
	
	@FXML
	private void back() {
		
	}
	
	

}
