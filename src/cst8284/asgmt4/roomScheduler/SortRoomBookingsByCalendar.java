package cst8284.asgmt4.roomScheduler;
import java.util.*;
/**
 * Class SortRoomBookingsByCalendar is inherited from Comparator interface. 
 * It is used to implement sort() and binarySearch() method for RoomBooking ArrayList, using start time and end time of RoomBooking to sort the ArrayList.
 * Built up in assignment 3.
 * @author Ye Zhang
 * @version 1.03
 */

public class SortRoomBookingsByCalendar implements Comparator<RoomBooking> {

	List<RoomBooking> list = new ArrayList<>();
	
	/**
	 * Override compare method in Comparator interface. Use start time and end time of RoomBooking to sort the ArrayList.
	 * @param book1 a RoomBooking for compare
	 * @param book2 another RoomBooking for compare
	 * @return return 0 if there is a overlap, return 1 if book1 start time is later or equal to book2 end time, return -1 if book2 start time is later than or equal to book1 end time
	 */
	@Override
	public int compare(RoomBooking book1, RoomBooking book2) {
		
		//GeeksForGeeks, Comparator Interface in Java with Examples. [Webpage]
		//Retrieved from https://www.geeksforgeeks.org/comparator-interface-java/
		int start1end2Compare = book1.getTimeBlock().getStartTime().compareTo(book2.getTimeBlock().getEndTime());
		int start2end1Compare = book2.getTimeBlock().getStartTime().compareTo(book1.getTimeBlock().getEndTime());
		
		
		if(start1end2Compare >= 0)
			return 1;		//book1 start time is later or equal to book2 end time
		else if(start2end1Compare >= 0)
			return -1;		//book2 start time is later than or equal to book1 end time
		else
			return 0;		//book2 overlap book1
	}
}
