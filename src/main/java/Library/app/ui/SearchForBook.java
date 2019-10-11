/**
 * 
 */
package Library.app.ui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import Library.app.App;
import Library.app.business.Book;
import Library.app.business.BookCopy;
import Library.app.business.CheckoutRecord;
import Library.app.business.ControllerInterface;
import Library.app.business.SystemController;
import Library.app.datatable.BookCopyDatable;
import Library.app.exception.BookNotFoundException;
import Library.app.util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * @author SARFO PHILIP
 *
 */
public class SearchForBook implements Initializable{
	
	private ControllerInterface controller;
	
	@FXML
	private TextField isbn;
	
	@FXML
	private TableView<BookCopyDatable> tableVw;
	
	@FXML
	private TableColumn<BookCopyDatable,String> isbnColumn;
	
	@FXML
	private TableColumn<BookCopyDatable,String> bookTitleColumn;
	
	@FXML
	private TableColumn<BookCopyDatable,String> copyNumberColumn;
	
	@FXML
	private TableColumn<BookCopyDatable,String> libraryMemberColumn;
	
	@FXML
	private TableColumn<BookCopyDatable,String> dueDateColumn;
	
	
	private ObservableList<BookCopyDatable> dataset;
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		controller = new SystemController();
	}
	
	
	@FXML
	private void back() throws IOException {
		App.setRoot("Dashboard");
	}
	
	
	@FXML
	private void search() {
		if(!isbn.getText().isEmpty()) {
			this.initializeTable(isbn.getText());
		}else {
			Util.showAlertMessage(AlertType.WARNING, "Input Required", "All Fields are required");
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private void initializeTable(String isbn) {
		dataset = this.getBookList(isbn);
		
		tableVw.setItems(dataset);
		
		//Populate Table
		isbnColumn.setCellValueFactory(new PropertyValueFactory<BookCopyDatable,String>("isbn"));
		bookTitleColumn.setCellValueFactory(new PropertyValueFactory<BookCopyDatable,String>("bookTitle"));
		copyNumberColumn.setCellValueFactory(new PropertyValueFactory<BookCopyDatable,String>("copyNumber"));
		libraryMemberColumn.setCellValueFactory(new PropertyValueFactory<BookCopyDatable,String>("libraryMember"));
		dueDateColumn.setCellValueFactory(new PropertyValueFactory<BookCopyDatable,String>("dueDate"));
		
		tableVw.getColumns().setAll(Arrays.asList(isbnColumn,bookTitleColumn,copyNumberColumn,libraryMemberColumn,dueDateColumn));
		
	}
	
	private ObservableList<BookCopyDatable> getBookList(String isbn){
		try {
			Book book = controller.findBookByIsbn(isbn);
			List<BookCopyDatable> copies = new ArrayList<>();
			for(BookCopy bookCopy : book.getCopies()) {
				
				//Finding the library Member
				CheckoutRecord record = controller.findCheckoutByBookCopyId(bookCopy.getCopyNum());
				String libraryMember = "N/A";
				String dueDate = "N/A";
				if(record != null) {
					libraryMember = record.getLibraryMember().getFirstName()+" "+record.getLibraryMember().getLastName();
					dueDate = record.getCheckoutRecordEntry().getDueDate().toString();
				}
				BookCopyDatable bookDataTable = new BookCopyDatable();
				
				bookDataTable.setIsbn(bookCopy.getBook().getIsbn());
				bookDataTable.setBookTitle(bookCopy.getBook().getTitle());
				bookDataTable.setCopyNumber(String.valueOf(bookCopy.getCopyNum()));
				bookDataTable.setLibraryMember(libraryMember);
				bookDataTable.setDueDate(dueDate);
				bookDataTable.setIsDueStatus(record == null?"":this.dueStatus(record));
				
				
				copies.add(bookDataTable);
			}
			return FXCollections.observableArrayList(copies);
		} catch (BookNotFoundException e) {
			Util.showAlertMessage(AlertType.ERROR, "Error", "Book Unavailable");
		}
		return null;
		
	}
	
	private String dueStatus(CheckoutRecord record) {
		String status = "Overdue";
		if(record.getCheckoutRecordEntry().getCheckoutDate().compareTo(LocalDate.now()) > 0) {
			status = "Not Overdue";
		}else if(record.getCheckoutRecordEntry().getCheckoutDate().compareTo(LocalDate.now()) == 0) {
			status = "Overdue today";
		}
		return status;
	}
	
	
}
