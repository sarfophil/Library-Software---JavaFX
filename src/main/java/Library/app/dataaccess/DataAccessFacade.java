package Library.app.dataaccess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import Library.app.business.Book;
import Library.app.business.CheckoutRecord;
import Library.app.business.LibraryMember;
import Library.app.exception.BookNotFoundException;
import Library.app.exception.MemberNotFoundException;

public class DataAccessFacade implements DataAccess {

	enum StorageType {
		BOOKS, MEMBERS, USERS, CHECKOUT;
	}

	public static final String OUTPUT_DIR = System.getProperty("user.dir")
			+ "\\src\\main\\java\\Library\\app\\dataaccess\\storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";

	// implement: other save operations
	public void saveNewMember(LibraryMember member) {
		HashMap<String, LibraryMember> mems = readMemberMap();
		if(mems == null)
			mems =  new HashMap<String, LibraryMember>();
		String memberId = member.getMemberId();
		mems.put(memberId, member);
		saveToStorage(StorageType.MEMBERS, mems);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Book> readBooksMap() {
		// Returns a Map with name/value pairs being
		// isbn -> Book
		return (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMemberMap() {
		// Returns a Map with name/value pairs being
		// memberId -> LibraryMember
		return (HashMap<String, LibraryMember>) readFromStorage(StorageType.MEMBERS);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUserMap() {
		// Returns a Map with name/value pairs being
		// userId -> User
		return (HashMap<String, User>) readFromStorage(StorageType.USERS);
	}

	@Override
	public void saveNewBook(Book book) {
		HashMap<String, Book> bookMap = (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
		if (bookMap == null)
			bookMap = new HashMap<String, Book>();
		bookMap.put(book.getIsbn(), book);
		saveToStorage(StorageType.BOOKS, bookMap);
	}

	/**
	 * 
	 */
	@Override
	public void updateBook(Book book) {
		@SuppressWarnings("unchecked")
		HashMap<String, Book> books = (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
		if(books == null)
			books = new HashMap<String, Book>();
		books.put(book.getIsbn(), book);
		saveToStorage(StorageType.BOOKS, books);
	}

	@Override
	public void saveNewActivityRecord(CheckoutRecord checkoutRecord) {
		HashMap<String, CheckoutRecord> activityRecord = (HashMap<String, CheckoutRecord>) readFromStorage(
				StorageType.CHECKOUT);
		if (activityRecord == null)
			activityRecord = new HashMap<String, CheckoutRecord>();
		activityRecord.put(checkoutRecord.getCheckoutId(), checkoutRecord);
		saveToStorage(StorageType.CHECKOUT, activityRecord);

	}

	@Override
	public LibraryMember findLibraryMemberById(String memberId) throws MemberNotFoundException {
		HashMap<String, LibraryMember> libraryMembers = (HashMap<String, LibraryMember>) readFromStorage(
				StorageType.MEMBERS);
		if (libraryMembers.get(memberId) == null)
			throw new MemberNotFoundException("Member not Available");
		return libraryMembers.get(memberId);
	}

	@Override
	public Book findBookByIsbn(String isbn) throws BookNotFoundException {

		@SuppressWarnings("unchecked")
		HashMap<String, Book> books = (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);

		if (books != null) {
			if (books.get(isbn) == null)
				throw new BookNotFoundException("Book not found");
			return books.get(isbn);
		}

		throw new BookNotFoundException("Book not found");
	}

	@Override
	public List<CheckoutRecord> findCheckoutRecordByMemberId(String memberId) throws MemberNotFoundException {
		@SuppressWarnings("unchecked")
		HashMap<String, CheckoutRecord> checkout = (HashMap<String, CheckoutRecord>) readFromStorage(
				StorageType.CHECKOUT);
		if (checkout != null) {
			List<CheckoutRecord> checkoutRecordList = new ArrayList<CheckoutRecord>();
			for (Entry<String, CheckoutRecord> checkoutRecord : checkout.entrySet()) {
				if (checkoutRecord.getValue().getLibraryMember().getMemberId().equals(memberId))
					checkoutRecordList.add(checkoutRecord.getValue());
			}
			
			return checkoutRecordList;
		}
		throw new MemberNotFoundException("Member Not Found");
	}

	@Override
	public CheckoutRecord findCheckoutRecordByBookCopyIdAndIsbn(int bookId, String isbn) {
		@SuppressWarnings("unchecked")
		HashMap<String, CheckoutRecord> checkout = (HashMap<String, CheckoutRecord>) readFromStorage(
				StorageType.CHECKOUT);
		if(checkout != null) {
			for (Entry<String, CheckoutRecord> checkoutRecord : checkout.entrySet()) {
				if (checkoutRecord.getValue().getBook().getCopyNum() == bookId
						&& checkoutRecord.getValue().getBook().getBook().getIsbn().equals(isbn))
					return checkoutRecord.getValue();
			}
		}
		return null;
	}

	@Override
	public Boolean checkLibraryMemberStatus(String libraryMemberId, Book book) {
		HashMap<String, CheckoutRecord> checkoutMap = (HashMap<String, CheckoutRecord>) readFromStorage(
				StorageType.CHECKOUT);
		if (checkoutMap != null) {
			for (Entry<String, CheckoutRecord> checkoutRecord : checkoutMap.entrySet()) {
				if (checkoutRecord.getValue().getLibraryMember().getMemberId().equals(libraryMemberId)
						&& checkoutRecord.getValue().getBook().getBook().equals(book)) {
					return true;
				}

			}
		}
		return false;
	}

	///// load methods - these place test data into the storage area
	///// - used just once at startup
	// static void loadMemberMap(List<LibraryMember> memberList) {

	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
	}

	static void loadUserMap(List<User> userList) {
		HashMap<String, User> users = new HashMap<String, User>();
		userList.forEach(user -> users.put(user.getId(), user));
		saveToStorage(StorageType.USERS, users);
	}

	static void loadMemberMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}

	static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
	}

	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		return retVal;
	}

	final static class Pair<S, T> implements Serializable {

		S first;
		T second;

		Pair(S s, T t) {
			first = s;
			second = t;
		}

		@Override
		public boolean equals(Object ob) {
			if (ob == null)
				return false;
			if (this == ob)
				return true;
			if (ob.getClass() != getClass())
				return false;
			@SuppressWarnings("unchecked")
			Pair<S, T> p = (Pair<S, T>) ob;
			return p.first.equals(first) && p.second.equals(second);
		}

		@Override
		public int hashCode() {
			return first.hashCode() + 5 * second.hashCode();
		}

		@Override
		public String toString() {
			return "(" + first.toString() + ", " + second.toString() + ")";
		}

		private static final long serialVersionUID = 5399827794066637059L;
	}

}
