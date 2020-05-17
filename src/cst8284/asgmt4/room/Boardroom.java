package cst8284.asgmt4.room;
/**
 * Class Boardroom is the subclss of Room. Boardroom is a final class.
 * Built up in assignment 2.
 * @author Ye Zhang
 * @version 1.02
 */

public final class Boardroom extends Room {
	private int seats;
	
	/**
	 * Default constructor for class Boardroom, use setter to set the default value for private field.
	 */
	public Boardroom() {
		seats = 16;
	}
	
	/**
	 * Getter for seats
	 * @return return a integer number of seats
	 */
	public int getSeats() {
		return seats;
	}
	
	/**
	 * Getter for Room Type
	 * @return return "board room"
	 */
	public String getRoomType() {
		return "board room";
	}
	
	/**
	 * Getter for details
	 * @return return "conference call enabled"
	 */
	public String getDetails() {
		return "conference call enabled";
	}
	
}
