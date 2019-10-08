/**
 * 
 */
package Library.app.business;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author GROUP 9
 *
 */
final public class CheckoutRecord implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String checkoutId;
	private BookCopy book;
	private LibraryMember libraryMember;
	private CheckoutRecordEntry checkoutRecordEntry;
	
	public CheckoutRecord(BookCopy book,LibraryMember libraryMember,CheckoutRecordEntry checkoutRecordEntry) {
		this.checkoutId = UUID.randomUUID().toString();
		this.book = book;
		this.libraryMember = libraryMember;
		this.checkoutRecordEntry = checkoutRecordEntry;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BookCopy getBook() {
		return book;
	}
	public String getCheckoutId() {
		return checkoutId;
	}

	public LibraryMember getLibraryMember() {
		return libraryMember;
	}

	public CheckoutRecordEntry getCheckoutRecordEntry() {
		return checkoutRecordEntry;
	}
	
	
	
}
