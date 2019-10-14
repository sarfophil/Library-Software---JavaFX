package Library.app.business;

import java.io.Serializable;
import java.util.Objects;

/**
 * Immutable class
 */
final public class BookCopy implements Serializable {
	
	private static final long serialVersionUID = -63976228084869815L;
	private Book book;
	private int copyNum;
	private boolean isAvailable;
	
	BookCopy(Book book, int copyNum, boolean isAvailable) {
		this.book = book;
		this.copyNum = copyNum;
		this.isAvailable = isAvailable;
	}
	
	BookCopy(Book book, int copyNum) {
		this.book = book;
		this.copyNum = copyNum;
		this.isAvailable = true;
	}
	
	
	public boolean isAvailable() {
		return isAvailable;
	}

	
	public int getCopyNum() {
		return copyNum;
	}
	
	public Book getBook() {
		return book;
	}
	
	public void changeAvailability() {
		isAvailable = false;
	}
	
	@Override
	public boolean equals(Object ob) {
		if(ob == null) return false;
		if(!(ob instanceof BookCopy)) return false;
		BookCopy copy = (BookCopy)ob;
		return copy.book.getIsbn().equals(book.getIsbn()) && copy.copyNum == copyNum;
	}
	
	@Override
		public int hashCode() {
			return Objects.hash(book,copyNum,isAvailable);
		}
	
	@Override
		public String toString() {
			return "Copy Id: "+copyNum+" Availability: "+isAvailable;
		}
	
}
