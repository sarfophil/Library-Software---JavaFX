/**
 * 
 */
package Library.app.exception;

import java.io.Serializable;

/**
 * @author Owner
 *
 */
public class MemberNotFoundException extends Exception implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MemberNotFoundException() {
		super();
	}
	
	public MemberNotFoundException(String message) {
		super(message);
	}

}
