package cst8284.asgmt4.roomScheduler;
import java.io.Serializable;
import java.util.*;
/**
 * Class TimeBlock is used to save startTime and endTime of room booking.
 * Built up in assignment 1, implements Serializable in assignment 2 for File IO function.
 * @author Ye Zhang
 * @version 1.02
 */

public class TimeBlock implements Serializable {
	public static final long serialVersionUID = 1L;
	private Calendar startTime = Calendar.getInstance();;
	private Calendar endTime = Calendar.getInstance();;
	
	/**
	 * No-arg constructor of class TimeBlock with default value, startTime is 8:00, endTime is 24:00 on the day of program is executed.
	 * It chains to constructor with 2 parameters to set the value for private fields
	 */
	public TimeBlock() {
		this( new Calendar.Builder().set(Calendar.HOUR_OF_DAY, 8).build(), new Calendar.Builder().set(Calendar.HOUR_OF_DAY, 24).build() );
	}
	
	/**
	 * Constructor of class TimeBlock with 2 parameters, use setter to set the value for private fields.
	 * @param start the start time of time block
	 * @param end the end time of time block
	 */
	public TimeBlock(Calendar start, Calendar end) {
		setStartTime(start);
		setEndTime(end);
	}
	
	/**
	 * Getter for startTime
	 * @return a Calendar of startTime in a timeBlock
	 */
	public Calendar getStartTime() {
		return startTime;
	}
	
	/**
	 * Setter for startTime
	 * @param start the actual parameter will be checked before setter
	 */
	public void setStartTime(Calendar start) {
		this.startTime.clear();
		this.startTime = start;
	}
	
	/**
	 * Getter for endTime
	 * @return a Calendar of endTime in a timeBlock
	 */
	public Calendar getEndTime() {
		return endTime;
	}
	
	/**
	 * Setter for endTime
	 * @param end the actual parameter will be checked before setter
	 */
	public void setEndTime(Calendar end) {
		this.endTime.clear();
		this.endTime = end;
	}
	
	/**
	 * Method overlaps allows the user to query whether a new TimeBlock will conflict with this TimeBlock. 
	 * It check the date first, then check the time.
	 * Method overlaps is not used from assignment 3, refactored using Colletions sort() and binarySearch() methods.
	 * @param newBlock the TimeBlock will be compared with current TimeBlock
	 * @return return true if overlap occur
	 */
	public boolean overlaps(TimeBlock newBlock) {
		if ( (newBlock.getStartTime().get(Calendar.YEAR) != getStartTime().get(Calendar.YEAR)) ||
			 (newBlock.getStartTime().get(Calendar.MONTH) != getStartTime().get(Calendar.MONTH)) ||	
			 (newBlock.getStartTime().get(Calendar.DATE) != getStartTime().get(Calendar.DATE)) )
			return false;
		return (! ( ((newBlock.getStartTime().get(Calendar.HOUR_OF_DAY) < getStartTime().get(Calendar.HOUR_OF_DAY)) 
				&& (newBlock.getEndTime().get(Calendar.HOUR_OF_DAY) <= getStartTime().get(Calendar.HOUR_OF_DAY))) 
			|| (newBlock.getStartTime().get(Calendar.HOUR_OF_DAY) >= getEndTime().get(Calendar.HOUR_OF_DAY)) ) );
	}

	/**
	 * Method duration is used to check the duration of a TimeBlock. 
	 * @return return the span of hours the room booking is for
	 */
	public int duration() {
		return getEndTime().get(Calendar.HOUR_OF_DAY) - getStartTime().get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Override toString method with specified format.
	 * @return a String with start time and end time
	 */
	@Override
	public String toString() {
		return getStartTime().get(Calendar.HOUR_OF_DAY) + ":00 - " 
				+ getEndTime().get(Calendar.HOUR_OF_DAY) + ":00";
	}
	
	
}
