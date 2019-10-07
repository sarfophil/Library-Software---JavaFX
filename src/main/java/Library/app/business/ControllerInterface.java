package Library.app.business;

import java.util.List;

import Library.app.exception.LoginException;



public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	
}
