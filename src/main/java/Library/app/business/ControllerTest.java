/**
 * 
 */
package Library.app.business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

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
	public void test() {
		ControllerInterface controller = new SystemController();
		//controller.checkoutBook("1001", "23-11451", LocalDate.now(), 9.0);
		//Book 
		Book book = new Book("23-11451", "How to get Away with murder", 2, allAuthors);
		controller.addNewBook(book);
		
		controller.addBookCopy(book, "23-11451");
		
		System.out.println(controller.allBookIds());
	}
	
	
	
	List<Address> addresses = new ArrayList<Address>() {
		{
			add(new Address("101 S. Main", "Fairfield", "IA", "52556"));
			add(new Address("51 S. George", "Georgetown", "MI", "65434"));
			add(new Address("23 Headley Ave", "Seville", "Georgia", "41234"));
			add(new Address("1 N. Baton", "Baton Rouge", "LA", "33556"));
			add(new Address("5001 Venice Dr.", "Los Angeles", "CA", "93736"));
			add(new Address("1435 Channing Ave", "Palo Alto", "CA", "94301"));
			add(new Address("42 Dogwood Dr.", "Fairfield", "IA", "52556"));
			add(new Address("501 Central", "Mountain View", "CA", "94707"));
		}
	};
	
	public List<Author> allAuthors = new ArrayList<Author>() {
		{
			add(new Author("Joe", "Thomas", "641-445-2123", addresses.get(0), "A happy man is he."));
			add(new Author("Sandra", "Thomas", "641-445-2123", addresses.get(0), "A happy wife is she."));
			add(new Author("Nirmal", "Pugh", "641-919-3223", addresses.get(1), "Thinker of thoughts."));
			add(new Author("Andrew", "Cleveland", "976-445-2232", addresses.get(2), "Author of childrens' books."));
			add(new Author("Sarah", "Connor", "123-422-2663", addresses.get(3), "Known for her clever style."));
		}
	};

}
