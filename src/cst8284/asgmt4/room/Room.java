package cst8284.asgmt4.room;
/**
 * Class Room is the superclass of Classroom, ComputerLab, and Boardroom.
 * Built up in assignment 2.
 * @author Prof. Dave Houtman
 * @version 1.02
 */

public abstract class Room {
	
	private static final String DEFAULT_ROOM_NUMBER = "unknown room number";
	private String roomNumber;
	
	/**
	 * Default constructor for class Room. It chains to 1-parameter constructor.
	 */
	protected Room() {this(DEFAULT_ROOM_NUMBER);}
	
	/**
	 * Constructor for class Room with 1 parameter, use setter to set the value for private fields.
	 * @param roomNum a String for room number
	 */
	protected Room(String roomNum) { setRoomNumber(roomNum); }
	
	/**
	 * Setter for roomNumber
	 * @param roomNum the number of the room
	 */
	public void setRoomNumber(String roomNum) {roomNumber = roomNum;}
	
	/**
	 * Getter for roomNumber
	 * @return return roomNumber of Class Room and its subclasses
	 */
	public String getRoomNumber() {return roomNumber;}
	
	/**
	 * Abstract method to get room type
	 * @return return room type
	 */
	public abstract String getRoomType() ;
    
	/**
	 * Abstract method to get seats
	 * @return return seats
	 */
	public abstract int getSeats();
	
	/**
	 * Abstract method to get details
	 * @return return details
	 */
	public abstract String getDetails();
	
	/**
	 * Override toString method with specified format.
	 * @return a String with Room information, includes room number, room type, seats, details
	 */
	public String toString( ) { return getRoomNumber() + " is a " +
		getRoomType() + " with " + getSeats() + " seats; " + getDetails() +"\n" ;}
}
