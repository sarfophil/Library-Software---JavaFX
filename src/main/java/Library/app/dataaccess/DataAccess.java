package Library.app.dataaccess;

import java.util.HashMap;
import java.util.List;

import Library.app.business.Book;
import Library.app.business.BookCopy;
import Library.app.business.CheckoutRecord;
import Library.app.business.LibraryMember;
import Library.app.exception.BookNotFoundException;
import Library.app.exception.MemberNotFoundException;



public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	
	/**
	 * Save a new member to the file database
	 * @param member
	 */
	public void saveNewMember(LibraryMember member); 

	
	/**
	 * Save a new a book to the file database
	 * @param book
	 */
	public void saveNewBook(Book book);
	
	/**
	 * Searches for a book by the isbn
	 * @param isbn
	 * @return
	 * @throws BookNotFoundException
	 */
	public Book findBookByIsbn(String isbn) throws BookNotFoundException;
	
	/**
	 * Performs a book update in the file system
	 * @param book
	 */
	public void updateBook(Book book);
	
	/**
	 * Persist a new checkout Activity Record
	 * @param checkoutRecord
	 */
	public void saveNewActivityRecord(CheckoutRecord checkoutRecord);
	
	/**
	 * Searches for a  library Member By the Id
	 * @param Isbn
	 * @return LibraryMember 
	 * @throws MemberNotFoundException 
	 */
	public LibraryMember findLibraryMemberById(String memberId) throws MemberNotFoundException;
	
	/**
	 * Finds checkout records by member Id
	 * @param memberId
	 * @return
	 * @throws MemberNotFoundException
	 */
	public List<CheckoutRecord> findCheckoutRecordByMemberId(String memberId) throws MemberNotFoundException;
	
	/**
	 * Find checkout record by book copy id and isbn
	 * @param bookId
	 * @param isbn
	 * @return
	 */
	CheckoutRecord findCheckoutRecordByBookCopyIdAndIsbn(int bookId,String isbn);
	
	/**
	 * Check library Member Status
	 * @param libraryMember
	 * @param book
	 * @return boolean value
	 */
	public Boolean checkLibraryMemberStatus(String libraryMember,Book book);

}
