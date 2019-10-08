package Library.app.dataaccess;

import java.util.HashMap;
import java.util.List;

import Library.app.business.Book;
import Library.app.business.LibraryMember;



public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public void saveNewMember(LibraryMember member); 
	
	
}
