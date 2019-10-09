package Library.app.business;

import java.time.LocalDate;
import java.util.List;

import Library.app.exception.BookNotFoundException;
import Library.app.exception.LoginException;
import Library.app.exception.MemberNotFoundException;



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
	 * @return a boolean
	 */
	public Boolean findAvailableBookCopy(String isbn);
	
	/**
	 * Retrieves a book
	 * @param isbn
	 * @return a Book object
	 * @throws BookNotFoundException
	 */
	public Book findBookByIsbn(String isbn) throws BookNotFoundException;
	
	/**
	 * Finds a member by the param: memberId
	 * @param memberId
	 * @return LibraryMember object
	 * @throws MemberNotFoundException
	 */
	public LibraryMember findMemberById(String memberId)  throws MemberNotFoundException;
	
	/**
	 * Count available books copies
	 * @param book
	 * @return number of available copies for a book
	 */
	public int countAvailableBooks(Book book);
	
}
