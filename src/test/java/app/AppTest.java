package app;

import org.junit.Test;

import Library.app.business.ControllerInterface;
import Library.app.business.SystemController;



public class AppTest {
	@Test
	public void run() {}
	
	@Test
	public void createNewLibraryMember() {
		 ControllerInterface controller = new SystemController();
		 controller.allBookIds();
	}
}
