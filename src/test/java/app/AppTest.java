package app;

import java.util.UUID;

import org.junit.Test;

<<<<<<< HEAD
import Library.app.business.ControllerInterface;
=======
import Library.app.business.Address;
import Library.app.business.ControllerInterface;
import Library.app.business.LibraryMember;
>>>>>>> 7a96c30145a77d5c116ef1a1c01effcf30b2d779
import Library.app.business.SystemController;

public class AppTest {
	@Test
	public void run() {}
	
	@Test
	public void createNewLibraryMember() {
<<<<<<< HEAD
		 ControllerInterface controller = new SystemController();
		 controller.allBookIds();
=======
		ControllerInterface controller = new SystemController();
		
		LibraryMember libraryMember = new LibraryMember(UUID.randomUUID().toString(), 
				"Godfred", "Koma", "090999", new Address("1000 North","Fairfield","Iowa","zip"));
		
		controller.createNewLibraryMember(libraryMember);
		
>>>>>>> 7a96c30145a77d5c116ef1a1c01effcf30b2d779
	}
}
