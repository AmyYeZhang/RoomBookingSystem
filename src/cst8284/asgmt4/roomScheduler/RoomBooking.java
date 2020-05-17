package cst8284.asgmt4.roomScheduler;
import java.io.Serializable;
/**
 * Class RoomBooking is used to save ContactInfo, Activity, and TimeBlock.
 * Built up in assignment 1, implements Serializable in assignment 2 for File IO function.
 * @author Ye Zhang
 * @version 1.02
 */

public class RoomBooking implements Serializable {
	
	public static final long serialVersionUID = 1L;
	private ContactInfo contactInfo;
	private Activity activity;
	private TimeBlock timeBlock;
	
	/**
	 * Constructor of class RoomBooking with 3 parameters, use setter to set the value for private fields.
	 * @param timeBlock time block includes start time and end time
	 * @param contactInfo contact information includes first name, last name, phone number, organization
	 * @param activity activity includes category and description
	 */
	public RoomBooking(TimeBlock timeBlock, ContactInfo contactInfo, Activity activity) {
		setTimeBlock(timeBlock);
		setContactInfo(contactInfo);
		setActivity(activity);
	}
	
	/**
	 * Setter for contactInfo
	 * @param contactInfo contact information includes first name, last name, phone number, organization
	 */
	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}
	
	/**
	 * Getter for contactInfo
	 * @return return the contactInfo
	 */
	public ContactInfo getContactInfo() {
		return contactInfo;
	}
	
	/**
	 * Setter for activity
	 * @param activity activity includes category and description
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	/**
	 * Getter for activity
	 * @return return the activity
	 */
	public Activity getActivity() {
		return activity;
	}
	
	/**
	 * Setter for timeBlock
	 * @param timeBlock time block includes start time and end time
	 */
	public void setTimeBlock(TimeBlock timeBlock) {
		this.timeBlock = timeBlock;
	}
	
	/**
	 * Getter for timeBlock
	 * @return return the timeBlock
	 */
	public TimeBlock getTimeBlock() {
		return timeBlock;
	}
	
	/**
	 * Override toString method with specified format, call fields toString method to fulfill the completed content of RoomBooking.
	 * @return a String with all information of RoomBooking, includes TimeBlock, Activity, ContactInfo
	 */
	@Override
	public String toString() {
		return "-------------------\n" 
				+ getTimeBlock().toString() + "\n" 
				+ getActivity().toString() + "\n" 
				+ getContactInfo().toString()
				+ "\n-------------------";
	}
	
	

}
