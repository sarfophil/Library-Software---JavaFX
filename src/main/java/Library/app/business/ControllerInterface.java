package Library.app.business;

import java.util.List;

import Library.app.exception.LibrarySystemException;
import Library.app.exception.LoginException;
import Library.app.exception.MemberNotFoundException;



public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public void createNewLibraryMember(LibraryMember libraryMember);
	public void addNewBook(Book book);
	public void checkoutBook(int studentId,String ISBN) throws MemberNotFoundException,LibrarySystemException;
	public void addBookCopy(Book book,String isbn);
	
}
