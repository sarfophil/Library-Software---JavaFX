package Library.app.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *
 */
final public class Book implements Serializable {

	private static final long serialVersionUID = 6110690276685962829L;
	private BookCopy[] copies;
	private List<Author> authors;
	private String isbn;
	private String title;
	private int maxCheckoutLength;
	private int numberOfCopies;
	private boolean isAvailable;

	public Book(String isbn, String title, int maxCheckoutLength, List<Author> authors, int numberOfCopies) {
		this.isbn = isbn;
		this.title = title;
		this.maxCheckoutLength = maxCheckoutLength;
		this.authors = Collections.unmodifiableList(authors);
		this.isAvailable = true;
		copies = new BookCopy[numberOfCopies];
		createCopies(numberOfCopies);

	}

	public void createCopies(int numberOfCopies) {
		if (numberOfCopies > 0)
			for (int i = 0, id = 1; i < copies.length; i++, id++) {
				copies[i] = new BookCopy(this, id, true);
			}
		else {
			copies = new BookCopy[] { new BookCopy(this, 1, true) };
		}
	}

	public void updateCopies(BookCopy copy) {

	}

	public List<Integer> getCopyNums() {
		List<Integer> copyNums = new ArrayList<Integer>();
		for (BookCopy bookCopy : copies) {
			copyNums.add(bookCopy.getCopyNum());
		}
		return copyNums;

	}

	public int getNumberOfCopies() {
		return copies.length;
	}

	public void addCopy() {
		BookCopy[] newArr = new BookCopy[copies.length + 1];
		System.arraycopy(copies, 0, newArr, 0, copies.length);
		newArr[copies.length] = new BookCopy(this, copies.length + 1, true);
		copies = newArr;
	}

	@Override
	public boolean equals(Object ob) {
		if (ob == null)
			return false;
		if (ob.getClass() != getClass())
			return false;
		Book b = (Book) ob;
		return b.isbn.equals(isbn);
	}
	
	@Override
		public int hashCode() {
			return Objects.hash(copies,authors,isbn,title,maxCheckoutLength,numberOfCopies,isAvailable);
		}

	public boolean isAvailable() {
		return isAvailable;
	}

	@Override
	public String toString() {
		return "isbn: " + isbn + ", maxLength: " + maxCheckoutLength + ", available: " + isAvailable();
	}

	public int getNumCopies() {
		return copies.length;
	}

	public String getTitle() {
		return title;
	}

	public BookCopy[] getCopies() {
		return copies;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public String getIsbn() {
		return isbn;
	}

	public BookCopy getNextAvailableCopy() {
		for (BookCopy copy : copies) {
			if (copy.isAvailable())
				return copy;
		}
		return null;
	}

	public BookCopy getCopy(int copyNum) {
		for (BookCopy copy : copies) {
			if (copy.getCopyNum() == copyNum)
				return copy;
		}
		return null;
	}

	public int getMaxCheckoutLength() {
		return maxCheckoutLength;
	}

	public int countAvailableCopies() {
		int count = 0;
		for (BookCopy copy : copies) {
			if (copy.isAvailable())
				count++;
		}
		return count;
	}
	


}
