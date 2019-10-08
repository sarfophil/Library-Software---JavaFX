package Library.app.dataaccess;

import java.util.HashMap;

import Library.app.business.Book;
import Library.app.business.LibraryMember;
import Library.app.exception.BookNotFoundException;



public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public void saveNewMember(LibraryMember member); 
	public void saveNewBook(Book book);
	public Book searchBook(String isbn) throws BookNotFoundException;
	public void updateBook(Book book);
}
