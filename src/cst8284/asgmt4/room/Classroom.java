package cst8284.asgmt4.room;
/**
 * Class Classroom is the subclss of Room. Classroom is a final class.
 * Built up in assignment 2.
 * @author Ye Zhang
 * @version 1.02
 */

public final class Classroom extends Room {
	private int DEFAULT_SEATS = 120;
	private int seats;
	
	/**
	 * Default constructor for class Classroom, use setter to set the default value for private field.
	 */
	public Classroom() {
		seats = DEFAULT_SEATS;
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
	 * @return return "class room"
	 */
	public String getRoomType() {
		return "class room";
	}
	
	/**
	 * Getter for details
	 * @return return "contains overhead projector"
	 */
	public String getDetails() {
		return "contains overhead projector";
	}
	
}
