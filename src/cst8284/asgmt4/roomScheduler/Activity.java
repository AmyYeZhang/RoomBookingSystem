package cst8284.asgmt4.roomScheduler;
import java.io.Serializable;

/**
 * Class Activity is used to save description and category of room booking.
 * Built up in assignment 1, implements Serializable in assignment 2 for File IO function.
 * @author Ye Zhang
 * @version 1.02
 */

public class Activity implements Serializable {
	public static final long serialVersionUID = 1L;
	private String description;
	private String category;
	
	/**
	 * Constructor of class Activity with 2 parameters, use setter to set the value for private fields
	 * @param category the actual parameter has been check before setter, it could not be null or ""
	 * @param description the actual parameter has been check before setter, it could not be null or ""
	 */
	public Activity(String category, String description) {
		setCategory(category);
		setDescription(description);
	}
	
	/**
	 * Setter for description, the actual parameter has been check before setter, it could not be null or "".
	 * @param description the actual parameter will be checked before setter, it could not be null or ""
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Getter for description
	 * @return a String of the value of description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Setter for category, the actual parameter has been check before setter, it could not be null or "".
	 * @param category the actual parameter will be checked before setter, it could not be null or ""
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
	/**
	 * Getter for category
	 * @return a String of the value of category
	 */
	public String getCategory() {
		return category;
	}

	
	/**
	 * Override toString method with specified format.
	 * @return a String with the category and description
	 */
	@Override
	public String toString() {
		return "Event: " + getCategory() + "\nDescription: " + getDescription();
	}
}
