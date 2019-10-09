package Library.app.business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Library.app.dataaccess.Auth;
import Library.app.dataaccess.DataAccess;
import Library.app.dataaccess.DataAccessFacade;
import Library.app.dataaccess.User;
import Library.app.exception.BookNotFoundException;
import Library.app.exception.LoginException;
import Library.app.exception.MemberNotFoundException;
import Library.app.util.Util;
import javafx.scene.control.Alert.AlertType;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;

	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if (!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if (!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();

		// Store Current Person
		Util.setPerson(map.get(id));
	}

	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}

	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	/**
	 * Add a new Library member to the system
	 * 
	 * @param person
	 */
	@Override
	public void createNewLibraryMember(LibraryMember libraryMember) {
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(libraryMember);
	}

	/**
	 * Add a new book to the library collection
	 * 
	 * @param book
	 */
	@Override
	public void addNewBook(Book book) {
		DataAccess da = new DataAccessFacade();
		da.saveNewBook(book);
	}

	/**
	 * This method checkouts a book for a member
	 * 
	 * @param memberId
	 * @param ISBN
	 */
	@Override
	public void checkoutBook(String memberId, String ISBN, LocalDate dueDate, Double fine) {
		DataAccess da = new DataAccessFacade();

		try {
			Book book = da.findBookByIsbn(ISBN);
			LibraryMember library = da.findLibraryMemberById(memberId);
			BookCopy bookCopy = findAvailableBook(book);

			// Tag the book copy as unavailable
			bookCopy.changeAvailability();

			// Checkout entry
			CheckoutRecordEntry entry = new CheckoutRecordEntry(LocalDate.now(), dueDate, fine);

			// Checkout Record
			CheckoutRecord checkout = new CheckoutRecord(bookCopy, library, entry);

			da.saveNewActivityRecord(checkout);

			Util.showAlertMessage(AlertType.INFORMATION, "Success", "Checkout Successful");

		} catch (BookNotFoundException e) {
			Util.showAlertMessage(AlertType.WARNING, "Warning", "Book is not available");
		} catch (MemberNotFoundException e) {
			Util.showAlertMessage(AlertType.WARNING, "Warning", "Member is not in the system");

		}

	}

	/**
	 * Find available copy of a book
	 * 
	 * @param bookCopies
	 * @return
	 */
	private BookCopy findAvailableBook(Book bookCopies) {
		BookCopy copy = null;
		for (BookCopy bookCopy : bookCopies.getCopies()) {
			if (bookCopy.isAvailable()) {
				copy = bookCopy;
				break;
			}
		}
		return copy;
	}

	/**
	 * This method adds a book copy to a book collection
	 * 
	 * @param book
	 */
	@Override
	public void addBookCopy(Book book, String isbn) {
		DataAccess da = new DataAccessFacade();
		try {
			Book readBook = da.findBookByIsbn(isbn);
			readBook.addCopy();

			da.updateBook(readBook);
		} catch (BookNotFoundException e) {
			Util.showAlertMessage(AlertType.ERROR, "Error", "Book not found");
		}

	}

	@Override
	public Boolean findAvailableBookCopy(String isbn) {
		DataAccess da = new DataAccessFacade();
		try {
			Book book = da.findBookByIsbn(isbn);
			BookCopy copy = this.findAvailableBook(book);
			if (copy != null)
				return true;
		} catch (BookNotFoundException e) {
			return false;
		}
		return false;
	}

	@Override
	public Book findBookByIsbn(String isbn) throws BookNotFoundException {
		DataAccess da = new DataAccessFacade();
		return da.findBookByIsbn(isbn);
	}
	
	@Override
	public LibraryMember findMemberById(String memberId) throws MemberNotFoundException {
		DataAccess da = new DataAccessFacade();
		return da.findLibraryMemberById(memberId);
	}

	@Override
	public int countAvailableBooks(Book book) {
		int count  = 0;
		for(BookCopy copy : book.getCopies())
			if(copy.isAvailable())
				count ++;
		return count;
	}

}
