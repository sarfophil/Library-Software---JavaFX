package Library.app.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Library.app.dataaccess.Auth;
import Library.app.dataaccess.DataAccess;
import Library.app.dataaccess.DataAccessFacade;
import Library.app.dataaccess.User;
import Library.app.exception.LibrarySystemException;
import Library.app.exception.LoginException;
import Library.app.exception.MemberNotFoundException;



public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		
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
	 * @param person
	 */
	@Override
	public void createNewLibraryMember(LibraryMember libraryMember) {
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(libraryMember);
	}
	
	
	/**
	 * Add a new book to the library collection
	 * @param book
	 */
	@Override
	public void addNewBook(Book book) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * This method checkouts a book for a member
	 * @param studentId
	 * @param ISBN
	 */
	@Override
	public void checkoutBook(int studentId, String ISBN) throws MemberNotFoundException, LibrarySystemException {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * This method adds a book copy to a book collection
	 * @param book
	 */
	@Override
	public void addBookCopy(Book book) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
