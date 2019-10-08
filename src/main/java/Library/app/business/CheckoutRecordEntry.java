/**
 * 
 */
package Library.app.business;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author GROUP 9
 *
 */
public class CheckoutRecordEntry implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LocalDate checkoutDate;
	private LocalDate dueDate;
	private Double    fine;
	private LocalDate datePaid;
	
	public CheckoutRecordEntry(LocalDate checkoutDate,LocalDate dueDate,Double fine) {
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.fine = fine;
	}
	
	public void setDatePaid(LocalDate datePaid) {
		this.datePaid = datePaid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public Double getFine() {
		return fine;
	}

	public LocalDate getDatePaid() {
		return datePaid;
	}
	
	

}
