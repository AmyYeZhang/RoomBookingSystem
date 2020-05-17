package cst8284.asgmt4.room;
/**
 * Class ComputerLab is the subclss of Room. ComputerLab is a final class.
 * Built up in assignment 2.
 * @author Ye Zhang
 * @version 1.02
 */

public final class ComputerLab extends Room {
	private int DEFAULT_SEATS = 30;
	private int seats;
	
	/**
	 * Default constructor for class ComputerLab, use setter to set the default value for private field.
	 */
	public ComputerLab() {
		seats = DEFAULT_SEATS;
	}

	/**
	 * A parameter constructor for class ComputerLab.
	 * @param roomNum set room number for ComputerLab
	 */
	public ComputerLab(String roomNum) {
		super(roomNum);
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
	 * @return return "Computer lab"
	 */
	public String getRoomType() {
		return "Computer lab";
	}
	
	/**
	 * Getter for details
	 * @return return "contains outlets for 30 laptops"
	 */
	public String getDetails() {
		return "contains outlets for 30 laptops";
	}
}
