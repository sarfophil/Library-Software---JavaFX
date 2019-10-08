package app;

import java.util.UUID;

import org.junit.Test;

import Library.app.business.Address;
import Library.app.business.ControllerInterface;
import Library.app.business.LibraryMember;
import Library.app.business.SystemController;

public class AppTest {
	@Test
	public void run() {}
	
	@Test
	public void createNewLibraryMember() {
		ControllerInterface controller = new SystemController();
		
		LibraryMember libraryMember = new LibraryMember(UUID.randomUUID().toString(), 
				"Godfred", "Koma", "090999", new Address("1000 North","Fairfield","Iowa","zip"));
		
		controller.createNewLibraryMember(libraryMember);
		
	}
}
