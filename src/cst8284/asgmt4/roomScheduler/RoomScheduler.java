package cst8284.asgmt4.roomScheduler;
import java.util.*;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cst8284.asgmt4.room.Room;
import java.io.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
/**
 * Class RoomScheduler is used to implement functions for room booking system.
 * Built up in assignment 1, add more functions in assignment 2 and assignment 3.
 * Changed in assignment 4 to match Swing methods.
 * @author Ye Zhang
 * @version 1.04
 */


public class RoomScheduler {
	private static ArrayList<RoomBooking> roomBookings = new ArrayList<>();
	private Room room;

	/**
	 * Constructor of RoomScheduler with 1 parameter, set the room information.
	 * @param room room information
	 */
	public RoomScheduler(Room room) {
		setRoom(room);
	}
	
	/**
	 * Method launch is the beginning of the RoomScheduler, it loads CurrentRoomBookings.book if the file exists, 
	 * calls RoomSchedulerDialog loadAndShowScheduler method.
	 */
	public void launch() {
		
		if (new File("CurrentRoomBookings.book").exists()) loadBookingsFromFile();	// reference from Professor Dave Houtman assignment 2 instructor code
		RoomSchedulerDialog.loadAndShowScheduler(getRoom());
	}
	
	/**
	 * Method saveRoomBooking save a new booking to the RoomBooking ArrayList,
	 * check the overlap, and sort the ArrayList.
	 * @param booking a RoomBooking object
	 */
	protected static void saveRoomBooking(RoomBooking booking) {
		if ( booking != null ) {
			//check overlap
			RoomBooking rb = findBooking(booking);
			if(rb != null) {
				JOptionPane.showMessageDialog( new JFrame(),  "Booking time and date conflict!\n" + rb.toString(), 
												"Add failed", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(getRoomBookings().add(booking)) {
				Collections.sort(getRoomBookings(), new SortRoomBookingsByCalendar());	//sort the ArrayList after save the new booking
				JOptionPane.showMessageDialog( new JFrame(),  "Booking saved successfully.");
			}
		}
	}
	
	/**
	 * Method deleteBooking will remove a booking from ArrayList.
	 * @param booking a RoomBooking object
	 * @return return a String to the dialog shows the deletion result.
	 */
	protected static boolean deleteBooking(RoomBooking booking) {
		
			return getRoomBookings().remove(booking); 
			
	}
	
	/**
	  * Method changeBooking find the room booking information from the input date and time, 
	  * check the overlap, change the ArrayList, and sort the ArrayList.
	 * @param booking the RoomBooking object will be changed
	 * @param originalDate the original date will be changed
	 * @param newDate the new date will be changed to
	 * @param newStartTime the new start time will be changed to
	 * @param newEndTime the new end time will be changed to
	 * @throws catch BadRoomBookingException for input exception
	 */
	protected static void changeBooking(RoomBooking booking, String originalDate, String newDate, String newStartTime, String newEndTime) {
		try {
			//check input date and time format
			isGeneralInputCorrect(newDate);
			isInputDateCorrect(newDate);

			Calendar newStart = makeCalendarFromUserInput(newDate, processTimeString(newStartTime), true);
			Calendar newEnd = makeCalendarFromUserInput(newDate, processTimeString(newEndTime), false);
			isInputTimeDurationCorrect(newStart, newEnd);

			//method JOptionPane.showConfirmDialog referred from [Webpage] How to Make Dialogs
			//retrieved from: https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
			int choice = JOptionPane.showConfirmDialog(new JFrame(),
				    "Are you sure to change the booking\non date: " + originalDate
				    + "\n" + booking.toString() 
				    + "\nTo the new time\n-------------------\ndate: " + newDate 
				    + "\nstart time: " + processTimeString(newStartTime)
				    + ":00\nend time: " + processTimeString(newEndTime)
				    + ":00\n-------------------\nChoose Yes to confirm deletion,\nchoose No to abort.",
				    "Delete Confirmation",
				    JOptionPane.YES_NO_OPTION);
			if(choice == 0) {
				//check overlap
				getRoomBookings().remove(booking);
				RoomBooking rb = findBooking(new RoomBooking(new TimeBlock(newStart, newEnd), new ContactInfo("1", "1", "1"), new Activity("1", "1")));
				if(rb != null) {
					getRoomBookings().add(booking);
					Collections.sort(getRoomBookings(), new SortRoomBookingsByCalendar());	//sort ArrayList after changing
					JOptionPane.showMessageDialog( new JFrame(),  "Changing failed. Booking time and date conflict!\ndate: " + newDate + "\n" + rb.toString(),
							"Change failed", JOptionPane.ERROR_MESSAGE);
					return;
				}
					
				//save changing if not overlap
				getRoomBookings().add(booking);
				booking.getTimeBlock().setStartTime(newStart);
				booking.getTimeBlock().setEndTime(newEnd);
				Collections.sort(getRoomBookings(), new SortRoomBookingsByCalendar());	//sort ArrayList after changing
				//getRoomBookings().sort(new SortRoomBookingsByCalendar());
				JOptionPane.showMessageDialog( new JFrame(),  "Booking has been changed to: \ndate: " + newDate + "\n" + booking.toString());
			}
		}
		catch(BadRoomBookingException ex) {
			JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), ex.getHeader(), JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "General exception", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Method displayBooking find and display the room booking information of input day and time.
	 * @param date the date to be searched
	 * @param startTime the start time to be searched
	 * @return return the RoomBooking if exist of the input day and time
	 */
	protected static RoomBooking displayBooking(String date, String startTime) {
		
		try {
			isGeneralInputCorrect(date);
			isInputDateCorrect(date);
			Calendar start = makeCalendarFromUserInput(date, processTimeString(startTime), true);
			RoomBooking book = findBooking(start);
			if(book != null)
				return book;
			else {
				JOptionPane.showMessageDialog( new JFrame(), "There is no booking between " 
						+ RoomScheduler.processTimeString(startTime) + ":00 and "
						+ (RoomScheduler.processTimeString(startTime)+1) +":00 on "
						+ date + ".");
				return null;
			}
		}
		catch(BadRoomBookingException ex) {
			JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), ex.getHeader(), JOptionPane.ERROR_MESSAGE);
			return null;
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "General exception", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	/**
	 * Method displayDayBooking find and display the room booking information of the input day.
	 * @param cal a Calendar include the date information
	 * @return return a String to dialog to show the result
	 */
	protected static String displayDayBooking(Calendar cal) {
		
		String strDayBooking = "";
		//ArrayList has been sorted when new booking saved or changed
		Calendar endTime = Calendar.getInstance();
		endTime.clear();
		endTime = (Calendar)cal.clone();
		endTime.set(Calendar.HOUR_OF_DAY, 9);
		cal.set(Calendar.HOUR_OF_DAY, 8);

		//find the first booking in this day, checking time block from 8:00 to 9:00
		int index = Collections.binarySearch(getRoomBookings(), new RoomBooking(new TimeBlock(cal, endTime), new ContactInfo("1", "1", "1"), new Activity("1", "1")), new SortRoomBookingsByCalendar());
		if(index < 0 ) index = Math.abs(index)-1;
		for(int i = 8; i <= 23; ) {
			if((index < getRoomBookings().size())
					&& (i == getRoomBookings().get(index).getTimeBlock().getStartTime().get(Calendar.HOUR_OF_DAY))
					&& (getRoomBookings().get(index).getTimeBlock().getStartTime().get(Calendar.DATE) == cal.get(Calendar.DATE))
					&& (getRoomBookings().get(index).getTimeBlock().getStartTime().get(Calendar.MONTH) == cal.get(Calendar.MONTH))
					&& (getRoomBookings().get(index).getTimeBlock().getStartTime().get(Calendar.YEAR) == cal.get(Calendar.YEAR))) {
				strDayBooking += getRoomBookings().get(index).toString() + "\n";
				i += getRoomBookings().get(index++).getTimeBlock().duration();
			}
			else {
				strDayBooking += "No booking scheduled between " + i + ":00 and " + (i+1) + ":00\n";
				i++;					
			}
		}
		
		return strDayBooking;
	}
	
	/**
	 * Method displayDayBooking find and display the room booking information of the input day.
	 * @param date the date to be searched
	 * @return return a String to dialog to show the result
	 */
	protected static String displayDayBooking(String date) {
		try {
			isGeneralInputCorrect(date);
			isInputDateCorrect(date);
			
			Calendar calendar = Calendar.getInstance(); 
			calendar.clear();
			calendar.set( 	Integer.parseInt(date.substring(4,8)),  		//year
							Integer.parseInt(date.substring(2,4))-1, 		//month
							Integer.parseInt(date.substring(0,2)),  		//day
							8, 0, 0);
			return displayDayBooking(calendar);
		}
		catch(BadRoomBookingException ex) {
			JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), ex.getHeader(), JOptionPane.ERROR_MESSAGE);
			return null;
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "General exception", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	/**
	 * Method saveBookingsToFile save the ArrayList of RoomBooking to file CurrentRoomBookings.book.
	 * @return return a String to dialog to show the result
	 * @throws FileNotFoundException throw and catch File IO exception
	 */
	protected static String saveBookingsToFile() {
		//reference from Professor Dave Houtman's Hybrid lecture - File IO Part2
		try(
			FileOutputStream fout = new FileOutputStream("CurrentRoomBookings.book");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
		){
			//delete file referenced from 'How to delete a file in Java' (2019) [Webpage]
			//retrieved from: https://jingyan.baidu.com/article/ca41422f92e4f75eae99edd8.html
			File file = new File("CurrentRoomBookings.book");
			if(file.exists()) file.delete();

			for(RoomBooking rb : getRoomBookings()) {
				oos.writeObject(rb);
			}
			return "Current room bookings backed up to file successfully.";
		}
		catch(FileNotFoundException ex) {
			return "File not found; save failed!";
		}
		catch(IOException ex) {
			return "Unkown Error; save failed!";
		}
	}
	
	/**
	 * Method loadBookingsFromFile load the ArrayList of RoomBooking from the file CurrentRoomBookings.book.
	 * Use EOFException to judge the file has been loaded successfully.
	 * @return return a String to dialog to show the result
	 * @throws FileNotFoundException if CurrentRoomBookings.book is not exist 
	 */
	protected static String loadBookingsFromFile() {
		//reference from Professor Dave Houtman's Hybrid lecture - File IO Part2
		RoomBooking rb;
		
		try(
			FileInputStream fin = new FileInputStream("CurrentRoomBookings.book");
			ObjectInputStream ois = new ObjectInputStream(fin);
		){
			getRoomBookings().clear();
			for(;;) {
				rb = (RoomBooking)(ois.readObject());
				getRoomBookings().add(rb);
			}
			
		}
		catch(EOFException ex) {
			return "Room bookings loaded from file successfully.";
		}
		catch(FileNotFoundException ex) {
			return "File not found, no data loaded from file.";
		}
		catch(ClassNotFoundException ex) {
			return "Unkown Error, no data loaded from file.";
		}
		catch(IOException ex) {
			return "Unkown Error, no data loaded from file.";
		}

	}
	
	/**
	 * Method makeBookingFromUserInput get the RoomBooking information from the add dialog, 
	 * check the input correction, catch and print BadRoomBookingException.
	 * @param date the date of the booking
	 * @param startTime the start time of the booking
	 * @param endTime the end time of the booking
	 * @param name the name of the booking
	 * @param phoneNumber the phone number of the booking
	 * @param org the organization of the booking
	 * @param category the category of the booking
	 * @param desc the description of the booking
	 * @return return a RoomBooking object created with user input
	 */
	protected static RoomBooking makeBookingFromUserInput(String date, String startTime, String endTime, String name, String phoneNumber, String org, String category, String desc) {
		ContactInfo contactInfo = null;
		Activity activity = null;
		TimeBlock timeBlock = null;
		try {
				//get the ContactInfo and check input correction
				isGeneralInputCorrect(name);
				String[] nameArr = name.split("\\s+");
				String firstName = nameArr[0];
				String lastName = nameArr[1];
				isInputNameCorrect(firstName);
				isInputNameCorrect(lastName);

				isGeneralInputCorrect(phoneNumber);
				isInputPhoneNumberCorrect(phoneNumber);

				//get the Activity and check input correction
				isGeneralInputCorrect(category);
				isGeneralInputCorrect(desc);
				
				//get the TimeBlock and check input correction
				isGeneralInputCorrect(date);
				isInputDateCorrect(date);
				Calendar start = makeCalendarFromUserInput(date, processTimeString(startTime), true);
				Calendar end = makeCalendarFromUserInput(date, processTimeString(endTime), false);
				isInputTimeDurationCorrect(start, end);
				
				contactInfo = new ContactInfo(firstName, lastName, phoneNumber, org);
				activity = new Activity(category, desc);
				timeBlock = new TimeBlock(start, end);
				return new RoomBooking( timeBlock, contactInfo, activity);
			}
			catch(BadRoomBookingException ex) {
				JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), ex.getHeader(), JOptionPane.ERROR_MESSAGE);
				return null;
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "General exception", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		
	}
	
	/**
	 * Method makeCalendarFromUserInput prompt the user for the date and time of a booking,
	 * check the input correction.
	 * @param cal if cal is null, prompt the user for the date first
	 * @param requestHour if cal is null and requestHour is true, prompt the user for the start time. If cal is not null and requestHour is false, prompt the user for the end time
	 * @return return a Calendar as start time or end time
	 */
	/**
	 * Method makeCalendarFromUserInput prompt the user for the date and time of a booking,
	 * check the input correction.
	 * @param date input date from dialog
	 * @param time input time from dialog
	 * @param startTime if startTime is true, it's a start time. if startTime is false, it's a end time.
	 * @return return a Calendar as start time or end time
	 */
	private static Calendar makeCalendarFromUserInput(String date, int time, boolean startTime) {
		
		if(startTime) {
			//start time
			if ( time < 8 )
				throw new BadRoomBookingException("Wrong time", "Start time begins from 8:00, please re-enter.");
			if ( time > 23 )
				throw new BadRoomBookingException("Wrong time", "Start time ends at 23:00, please re-enter.");
		}
		else {
			//end time
			if( time > 24 )
				throw new BadRoomBookingException("Wrong time", "End time ends at 24:00, please re-enter.");
		}

		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set( 	Integer.parseInt(date.substring(4,8)),  		//year
						Integer.parseInt(date.substring(2,4))-1, 		//month
						Integer.parseInt(date.substring(0,2)),  		//day
						time, 0, 0);
		return calendar;
	}
	
	/**
	 * Method processTimeString is responsible for taking the time String and returning an integer value, 
	 * corresponding to the 24-hour format expected by the Calendar’s set() method.
	 * Check the input correction, catch and throw BadRoomBookingException.
	 * @param t a String that is input from user
	 * @return return the time of 24-hour format
	 * @throws BadRoomBookingException throw exception with header of "Wrong time" if input is not start with a number, throw exception with header of "Bad time format" if input is not formatted correctly
	 */
	protected static int processTimeString(String t) {
		isGeneralInputCorrect(t);
		int time = 0;
		
		try {
			String[] tArray = t.split("\\s+|:");
			if ( tArray[1].equals("00") || tArray[1].toLowerCase().equals("am") || tArray[1].toLowerCase().equals("a.m.") ) 
				time = Integer.parseInt(tArray[0]);
			else 
				time = Integer.parseInt(tArray[0])+12;
			return time;
		}
		catch(NumberFormatException ex) {
			throw new BadRoomBookingException("Wrong time", "Wrong time was entered. Time should be a number.");
		}
		catch(Exception ex) {
			throw new BadRoomBookingException("Bad time format", "Bad time was entered. The correct format is '8:00' or '8 am' or '4 p.m.'.");
		}
	}
	
	/**
	 * Method findBooking with a parameter Calendar check 1 hour overlap exist or not.
	 * Return null if overlap is not exist, otherwise return the overlapped RoomBooking object.
	 * @param cal the Calendar parameter will be the start time
	 * @return return a RoomBooking in the ArrayList if there is a overlap, return null if there is no overlap
	 */
	private static RoomBooking findBooking(Calendar cal) {
		Calendar oneHourLater = Calendar.getInstance();
		oneHourLater.clear();
		oneHourLater.set(	cal.get(Calendar.YEAR), 
							cal.get(Calendar.MONTH), 
							cal.get(Calendar.DATE), 
							cal.get(Calendar.HOUR_OF_DAY) + 1, 0, 0);
		RoomBooking booking =  new RoomBooking(new TimeBlock(cal, oneHourLater), new ContactInfo("1", "1", "1"), new Activity("1", "1"));
		
		//findBooking(booking);
		int index = Collections.binarySearch(getRoomBookings(), booking, new SortRoomBookingsByCalendar());
		if(index >= 0) 
			return getRoomBookings().get(index);
		else
			return null;		
	}
	
	/**
	 * Method findBooking with a parameter RoomBooking check a time block overlap.
	 * Return null if overlap is not exist, otherwise return the overlapped RoomBooking object.
	 * @param booking the RoomBooking will be used to check the time block overlap
	 * @return a RoomBooking in the ArrayList if there is a overlap, return null if there is no overlap
	 */
	private static RoomBooking findBooking(RoomBooking booking) {
		
		int index = Collections.binarySearch(getRoomBookings(), booking, new SortRoomBookingsByCalendar());
		if(index >= 0) 
			return getRoomBookings().get(index);
		else
			return null;		
	
	}
	
	/**
	 * Method getRoomBookings access the private field roomBookings ArrayList and return it.
	 * @return return ArrayList of RoomBooking class
	 */
	protected static ArrayList<RoomBooking> getRoomBookings() {
		return roomBookings;
	}
	
	/**
	 * Setter for room
	 * @param room a Room class parameter
	 */
	private void setRoom(Room room) {
		this.room = room;
	}
	
	/**
	 * Getter for room
	 * @return return the room of Room class
	 */
	private Room getRoom() {
		return room;
	}
	
	/**
	 * Method isGeneralInputCorrect check the input is not null or "".
	 * Return true if the input is not null or "", otherwise return false and throw the BadRoomBookingException.
	 * @param input a String that is input from user
	 * @return true if the input is not null or ""
	 * @throws BadRoomBookingException throw exception with header of "Null value entered" if the input is null, throw exception with header of "Missing value" if the input is only space or ""
	 */
	protected static boolean isGeneralInputCorrect(String input)  {
		
		if(input == null) 
			throw new BadRoomBookingException("Null value entered", "An attempt was made to pass a null value to a variable.");
		if(input.trim().contentEquals("")) 
			throw new BadRoomBookingException("Missing value", "Missing an input value.");
		
		return true;		
	}

	/**
	 * Method isInputNameCorrect check the input first name and last name both is less than or equal to 30 characters, 
	 * and both of them only contains alphabetic characters, the dash (-), the period(.), and the apostrophe(').
	 * Return true if the input name is correct, otherwise return false and throw the BadRoomBookingException.
	 * @param name a String that is the name of contact, first name or last name
	 * @return true if the name is match the requirement
	 * @throws BadRoomBookingException throw exception with header of "Name contains illegal characters" if the input name is not match the requirement, throw exception with header of "Name length exceeded" if the input name is exceed 30 characters
	 */
	protected static boolean isInputNameCorrect(String name) {
		// look-up-at-the-starlit-sky.(2019) What's the rule of regex in Java. [Webpage]
		// Retrieved from: https://www.cnblogs.com/look-up-at-the-starlit-sky/p/11602401.html
		Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z-.']*");
		boolean match = pattern.matcher(name).matches();
		if(!match) 
			throw new BadRoomBookingException("Name contains illegal characters","A name cannot include characters other than alphabetic characters, the dash(-), the period(.), and the apostrophe(').");
		
		if(name.length() > 30)
			throw new BadRoomBookingException("Name length exceeded","The first or last name exceeds the 30 character maximum allowed.");
		
		return true;
	}
	
	/**
	 * Method isInputPhoneNumberCorrect check the input phone number is formatted as 'xxx-xxx-xxxx'
	 * Return true if the input phone number is correct, otherwise return false and throw the BadRoomBookingException.
	 * @param phoneNumber a String that is a phone number
	 * @return return true is the input phone number is formatted as 'xxx-xxx-xxxx'
	 * @throws BadRoomBookingException throw exception with header of "Bad telephone number" if the input phone number is not formatted as 'xxx-xxx-xxxx'
	 */
	protected static boolean isInputPhoneNumberCorrect(String phoneNumber) {
		// look-up-at-the-starlit-sky.(2019) What's the rule of regex in Java. [Webpage]
		// Retrieved from: https://www.cnblogs.com/look-up-at-the-starlit-sky/p/11602401.html
		Pattern pattern = Pattern.compile("^[2-9]\\d{2}(\\-)\\d{3}\\1\\d{4}$");	
		boolean match = pattern.matcher(phoneNumber).matches();
		if(!match)  
			throw new BadRoomBookingException("Bad telephone number", "The telephone number must be a 10-digit number, separated by '-' in the form, e.g. 613-555-1212.");
		
		return true;
	}

	/**
	 * Method isInputDateCorrect check the input date is valid when formatted as 'DDMMYYYY'
	 * Return true if the input date is correct, otherwise return false and throw the BadRoomBookingException.
	 * @param input a String that is a date
	 * @return return true if the input date is valid
	 * @throws BadRoomBookingException throw exception with header of "Bad Calendar format" if the input date is not valid
	 */
	protected static boolean isInputDateCorrect(String input) {
		
		if(input.length() > 8)
			throw new BadRoomBookingException("Bad Calendar format", "Bad Calendar date was entered. The correct format is DDMMYYYY");
			
		
		//Date check method reference from the code in Lab7 provided by Professor Dave Houtman [2020]
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
		format.setLenient(false);
		try {
			format.parse(input);
		}
		catch(ParseException ex) {
			throw new BadRoomBookingException("Bad Calendar format", "Bad Calendar date was entered. The correct format is DDMMYYYY");
		}
		
		return true;
	}
	
	/**
	 * Method isInputTimeDurationCorrect check the time block, start time should earlier than end time. 
	 * Return true if the input time block is correct, otherwise return false and throw the BadRoomBookingException.
	 * @param start a Calendar that is a start time
	 * @param end a Calendar that is a end time
	 * @return return true if end time is later than start time
	 * @throws BadRoomBookingException throw exception with header of "End time precedes start time" if end time precede start time, throw exception with header of "Zero time room booking" if end time is equal to start time
	 */
	protected static boolean isInputTimeDurationCorrect(Calendar start, Calendar end) {
		
		int startTime = start.get(Calendar.HOUR_OF_DAY);
		int endTime = end.get(Calendar.HOUR_OF_DAY);
		
		if(startTime > endTime)
			throw new BadRoomBookingException("End time precedes start time", "The room booking start time must occur before the end time of the room booking.");
		if(startTime == endTime)
			throw new BadRoomBookingException("Zero time room booking", "Start and end time of the room booking are the same. The minimum time for a room booking is one hour.");
		
		return true;
	}
}
