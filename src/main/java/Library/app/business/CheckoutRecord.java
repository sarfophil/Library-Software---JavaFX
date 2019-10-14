/**
 * 
 */
package Library.app.business;

import java.io.Serializable;
import java.time.LocalDate;
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
	
	@Override
	public String toString() {
		String isDue = isDue()?"Not Overdue":"Overdue";
		String result = String.format("%16s%32s%32s%16s%16s%16s%16s",checkoutId,book.getCopyNum(),book.getBook().getTitle(),book.getBook().getIsbn(),
				checkoutRecordEntry.getCheckoutDate(),checkoutRecordEntry.getDueDate(),isDue);
		return result;
		
	}
	
	public Boolean isDue() {
		if(checkoutRecordEntry.getDueDate().compareTo(LocalDate.now()) < 0) {
			return false;
		}
		return true;
	}
	
	
	
	
}
