package cst8284.asgmt4.roomScheduler;
/**
 * Class BadRoomBookingException is inherited from java.lang.RuntimeException. 
 * It is used to define the specified exception of room booking system.
 * Built up in assignment 3.
 * @author Ye Zhang
 * @version 1.03
 */

public class BadRoomBookingException extends java.lang.RuntimeException {

	private static final long serialVersionUID = 1L;
	private String header;

	/**
	 * No-arg constructor for class BadRoomBookingException with default value.
	 * It chains to the constructor with 2 parameters. 
	 */
	public BadRoomBookingException() {
		this("Bad room booking entered", "Please try again.");
	}
	
	/**
	 * Constructor for class BadRoomBookingException with 2 parameters. 
	 * Use super() for inheritance field, use setter to set the value for private field.
	 * @param header the header of exception
	 * @param message the message of exception
	 */
	public BadRoomBookingException(String header, String message) {
		super(message);
		setHeader(header);
	}
	
	/**
	 * Setter for header
	 * @param header a String of exception
	 */
	public void setHeader(String header) {
		this.header = header;
	}
	
	/**
	 * Getter for header
	 * @return return a String of header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * Override toString method with specified format. Use super getMessage().
	 * @return a String with header and message
	 */
	@Override
	public String toString() {
		return getHeader() + ". " + getMessage();
	}
	
	
}
