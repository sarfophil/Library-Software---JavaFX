/**
 * 
 */
package Library.app.business;

import java.io.Serializable;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import Library.app.dataaccess.TestData;
import Library.app.exception.LoginException;
import Library.app.util.Util;

/**
 * @author SARFO PHILIP
 *
 */
public class ControllerTest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Before
	public void member() {
		ControllerInterface controller = new SystemController();
		
		Address address = new Address("100 North","Fairled","Iowa","5255");
		LibraryMember library = new LibraryMember("1001", "Sarfo", "Phil", "0545155", address);
		controller.createNewLibraryMember(library);
		
		
	}
	
	
	@Test
	public void loginTest() throws LoginException {
		TestData testData = new TestData();
		testData.userData();
		
		ControllerInterface controller = new SystemController();
		controller.login("101", "xyz");
		
		System.out.println(Util.getCurrentPerson());
	}
	
	
	
	public void test() {
		ControllerInterface controller = new SystemController();
		controller.checkoutBook("1001", "23-11451", LocalDate.now(), 9.0);
		//Book 
		//Book book = new Book("23-11451", "How to get Away with murder", 2, allAuthors);
		//controller.addNewBook(book);
		
		//controller.addBookCopy(book, "23-11451");
		
		//System.out.println(controller.allBookIds());
	}
	
	
	
	

}
