package Library.app.business;

import java.time.LocalDate;
import java.util.List;

import Library.app.exception.LoginException;



public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	
	/**
	 * Creates new library Member
	 * @param libraryMember
	 */
	public void createNewLibraryMember(LibraryMember libraryMember);
	/**
	 * Add new Book to the file database
	 * @param book
	 */
	public void addNewBook(Book book);
	/**
	 * Checkouts a book for student
	 * @param memberId
	 * @param ISBN
	 * @param dueDate
	 * @param fine
	 */
	public void checkoutBook(String memberId,String ISBN,LocalDate dueDate, Double fine);
	
	/**
	 * Adds a copy of a book to the collection
	 * @param book
	 * @param isbn
	 */
	public void addBookCopy(Book book,String isbn);
	
	/**
	 * Checks if a book has available copies.
	 * @param isbn
	 * @return
	 */
	public Boolean findAvailableBookCopy(String isbn);
	
}
