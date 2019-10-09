/**
 * 
 */
package Library.app.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import Library.app.business.Book;
import Library.app.business.BookCopy;
import Library.app.business.CheckoutRecord;
import Library.app.business.ControllerInterface;
import Library.app.business.SystemController;
import Library.app.exception.BookNotFoundException;
import Library.app.util.Util;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * @author SARFO PHILIP
 *
 */
public class SearchForBook implements Initializable{
	
	private ControllerInterface controller;
	
	@FXML
	private TextField isbn;
	
	
	
	
	@FXML
	TableView<BookCopy> table;
	
	private ObservableList<BookCopy> dataset;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		controller = new SystemController();
		dataset = FXCollections.observableList(new ArrayList<>());
	}
	
	
	@FXML
	private void search() {
		if(!isbn.getText().isEmpty()) {
			this.initializeTable(isbn.getText());
		}else {
			Util.showAlertMessage(AlertType.WARNING, "Input Required", "All Fields are required");
		}
	}
	
	
	private void initializeTable(String isbn) {
		dataset = this.getBookList(isbn);
		table = new TableView<BookCopy>();
		table.setItems(dataset);
		
		//Edit Table Columns
		TableColumn<BookCopy,String> isbnColumn = new TableColumn<BookCopy,String>("Isbn");
		TableColumn<BookCopy,String> bookTitle = new TableColumn<BookCopy,String>("Book Title");
		TableColumn<BookCopy,String> copyNumber = new TableColumn<BookCopy,String>("Copy Number");
		TableColumn<BookCopy,String> libraryMember = new TableColumn<BookCopy,String>("Library Member");
		TableColumn<BookCopy,String> dueDate = new TableColumn<BookCopy,String>("Due Date");
		
		//Isbn Column
		isbnColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BookCopy,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<BookCopy, String> cell) {
				
				return new ReadOnlyObjectWrapper<String>(cell.getValue().getBook().getIsbn());
			}
		});
		
		//Book Title
		bookTitle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BookCopy,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<BookCopy, String> cell) {
				
				return new ReadOnlyObjectWrapper<String>(cell.getValue().getBook().getTitle());
			}
		});
		
		//Copy Number 
		copyNumber.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BookCopy,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<BookCopy, String> cell) {
				
				return new ReadOnlyObjectWrapper<String>(String.valueOf(cell.getValue().getCopyNum()));
			}
		});
		
		//library member
		libraryMember.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BookCopy,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<BookCopy, String> param) {
				//Finding the library Member
				CheckoutRecord record = controller.findCheckoutByBookCopyId(param.getValue().getCopyNum());
				String libraryMember = "N/A";
				if(record != null)
					libraryMember = record.getLibraryMember().getFirstName()+" "+record.getLibraryMember().getLastName();
				return new ReadOnlyObjectWrapper<String>(libraryMember);
			}
		
		});
		
		//Due Date
		dueDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BookCopy,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<BookCopy, String> param) {
				//Finding the Due Date
				CheckoutRecord record = controller.findCheckoutByBookCopyId(param.getValue().getCopyNum());
				String dueDate = "N/A";
				if(record != null)
					dueDate = record.getCheckoutRecordEntry().getCheckoutDate().toString();
				return new ReadOnlyObjectWrapper<String>(dueDate);
			}
			
		});
		
		
		
	}
	
	private ObservableList<BookCopy> getBookList(String isbn){
		try {
			Book book = controller.findBookByIsbn(isbn);
			List<BookCopy> copies = new ArrayList<>();
			for(BookCopy bookCopy : book.getCopies()) {
				copies.add(bookCopy);
			}
			return FXCollections.observableArrayList(copies);
		} catch (BookNotFoundException e) {
			Util.showAlertMessage(AlertType.ERROR, "Error", "Book Unavailable");
		}
		return null;
		
	}
	
	
}
