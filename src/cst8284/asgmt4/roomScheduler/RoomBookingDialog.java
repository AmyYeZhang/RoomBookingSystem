package cst8284.asgmt4.roomScheduler;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import cst8284.asgmt4.room.Room;

/* Adapted, with considerable modification, from 
 * http://www.java2s.com/Code/Java/Swing-JFC/TextAcceleratorExample.htm,
 * which is sloppy code and should not be emulated.
 */
/**
 * Class RoomBookingDialog is used to add / search / change / delete a room booking information.
 * Customer could input the date and time to search the booking information at first, then change the time, or delete the booking. 
 * Built up in assignment 4.
 * @author Ye Zhang
 * @version 1.04
 */
public class RoomBookingDialog {
	
	private static final GridBagConstraints labelConstraints = new GridBagConstraints(
    	0, GridBagConstraints.RELATIVE, 1, 1, 1, 1, 
    	GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 20), 0, 0);
	private static final GridBagConstraints textConstraints = new GridBagConstraints(
	    1, GridBagConstraints.RELATIVE, 1, 1, 1, 1,  // gridx, gridy, gridwidth, gridheight, weightx, weighty
	    GridBagConstraints.EAST, 0, new Insets(5, 5, 5, 10), 20, 20); // anchor, fill, insets, ipadx, ipady
	private static final GridBagConstraints btnConstraints = new GridBagConstraints(
	    0, GridBagConstraints.RELATIVE, 2, 1, 0.5, 0.5, 
	    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 20), 0, 0);

	private static Panel cp = new Panel();
	private static final int labelWidth = 24;
	protected static final Font defaultFont = new Font("SansSerif", Font.PLAIN, 20);	
	private static final FlowLayout btnRow = new FlowLayout();
	private static final JPanel btnPanel = new JPanel();
	
	//TextFields
	private static JTextField date;
	private static JTextField startTime;
	private static JTextField endTime;
	private static JTextField name;
	private static JTextField phoneNum;
	private static JTextField org;
	private static JTextField category;
	private static JTextField desc;
	
	//Buttons
	private static JButton btnChange;
	private static JButton btnDelete;
	
	private static RoomBooking book = null;
	private static String strDate = "";
	private static String strStartTime = "";
	private static String strEndTime = "";
	
	/**
	 * Load and show the editable dialog for add / search / change / delete functions.
	 * Set the dialog title with room information.
	 * @param room Room information
	 * @param flag flag == 1 shows the dialog for add function, flag == 2 shows the dialog for search / change / delete functions.
	 */
	public static void showAppointmentDialog(Room room, int flag){
		cp.removeAll();
		cp.repaint();
	    JFrame f = new JFrame("");  
		f.setTitle(room.getRoomNumber() + " ( " + room.getRoomType() +" / " + room.getSeats() + " seats / " + room.getDetails() + " )");
	    cp.setLayout(new GridBagLayout());
	    
	    // Set the first seven rows with Label/TextField
	    date = setRow("Booking Date (DDMMYYYY):", 'e');
	    startTime = setRow("Start Time (2 p.m. or 14:00):", 't');
	    endTime = setRow("End Time (2 p.m. or 14:00):", 'i');
	    name = setRow("Client Name (FirstName LastName):", 'n');
	    phoneNum = setRow("Phone Number (e.g. 613-555-1212):", 'p');
	    org = setRow("Organization (optional):", 'o');
	    category = setRow("Event Category:", 'c');
	    desc = setRow("Description:", 'd');
		
	    btnPanel.removeAll();
	    btnPanel.repaint();
	    btnPanel.setLayout(btnRow);

	    //flag: 1 - add, 2 - search first, then change or delete
	    if(flag == 1) {
			setBtnRow("    Add    ", 's', (e)->{RoomScheduler.saveRoomBooking(RoomScheduler.makeBookingFromUserInput(date.getText(), startTime.getText(), endTime.getText(), name.getText(), phoneNum.getText(), org.getText(), category.getText(), desc.getText()));});
			setBtnRow("    Exit    ", 'x', (e)->{RoomSchedulerDialog.reloadJTextArea(); f.dispose();});
	    }
	    else if(flag == 2) {
			endTime.setEditable(false);
			name.setEditable(false);
			phoneNum.setEditable(false);
			org.setEditable(false);
			category.setEditable(false);
			desc.setEditable(false);
			setBtnRow("   Search   ", 'r', (e)->searchBooking());
			btnChange = setBtnRow("   Change   ", 's', (e)->changeBooking()); 
			btnChange.setEnabled(false);
			btnDelete = setBtnRow("   Delete   ", 'l', (e)->deleteBooking()); 
			btnDelete.setEnabled(false);
		    setBtnRow("    Exit    ", 'x', (e)->{RoomSchedulerDialog.reloadJTextArea(); f.dispose();});
	    }
       
	    cp.add(btnPanel, btnConstraints);
	    f.add(cp); f.pack();
	    cp.revalidate();
	    
	    // Close dialog
	    f.addWindowListener(new WindowAdapter() {
	       public void windowClosing(WindowEvent evt) {
	    	   RoomSchedulerDialog.reloadJTextArea();
	    	   f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	       }
	    });
	    
	    f.setVisible(true);
	  }
	
	/**
	 * Set the JLabel and JTextField in a row.
	 * @param label show as the label for the JTextField
	 * @param keyboardShortcut set shortcut for the JTextField 
	 * @return return a JTextField object
	 */
	private static JTextField setRow(String label, char keyboardShortcut) {
		JLabel l; JTextField t;
		cp.add(l = new JLabel(label, SwingConstants.RIGHT), labelConstraints);
		l.setFont(defaultFont);
		l.setDisplayedMnemonic(keyboardShortcut);
	    cp.add(t = new JTextField(labelWidth), textConstraints);
	    t.setFont(defaultFont);
	    t.setFocusAccelerator(keyboardShortcut);
	    return t;
	}
	
	/**
	 * Set the button in a row.
	 * @param label shows the label for the button
	 * @param keyboardShortcut set the shortcut for the button
	 * @param act ActionListener for the button
	 * @return return a JButton object
	 */
	private static JButton setBtnRow(String label, char keyboardShortcut, ActionListener act) {
		JButton btn = new JButton(label);
		btn.setFont(defaultFont);
		btn.setMnemonic(keyboardShortcut);
		btnPanel.add(btn);
		btn.addActionListener(act);
		return btn;
	}
	
	/**
	 * ActionListener method for search button.
	 */
	private static void searchBooking() {
		endTime.setText("");
		name.setText("");
		phoneNum.setText("");
		org.setText("");
		category.setText("");
		desc.setText("");
		endTime.setEditable(false);
		btnChange.setEnabled(false);
		btnDelete.setEnabled(false);
		
		book = RoomScheduler.displayBooking(date.getText(), startTime.getText());
		if(book!=null) {
			startTime.setText(book.getTimeBlock().getStartTime().get(Calendar.HOUR_OF_DAY)+":00");
			endTime.setText(book.getTimeBlock().getEndTime().get(Calendar.HOUR_OF_DAY)+":00");
			name.setText(book.getContactInfo().getFirstName() + " " + book.getContactInfo().getLastName());
			phoneNum.setText(book.getContactInfo().getPhoneNumber());
			org.setText(book.getContactInfo().getOrganization());
			category.setText(book.getActivity().getCategory());
			desc.setText(book.getActivity().getDescription());
			endTime.setEditable(true);
			btnChange.setEnabled(true);
			btnDelete.setEnabled(true);
			strDate = date.getText();
			strStartTime = startTime.getText();
			strEndTime = endTime.getText();
		}
	}
	
	/**
	 * ActionListener method for delete button.
	 */
	private static void deleteBooking() {
		
		//method JOptionPane.showConfirmDialog referred from [Webpage] How to Make Dialogs
		//retrieved from: https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
		int choice = JOptionPane.showConfirmDialog(new JFrame(),
			    "Are you sure to delete the booking\non date: " + strDate + "\n" + book.toString() 
			    + "\nChoose Yes to confirm deletion,\nchoose No to abort.",
			    "Delete Confirmation",
			    JOptionPane.YES_NO_OPTION);
		if(choice == 0) {
			if(RoomScheduler.deleteBooking(book)) {
				JOptionPane.showMessageDialog( new JFrame(), "Booking deleted successfully!");
				date.setText("");
				startTime.setText("");
				endTime.setText("");
				name.setText("");
				phoneNum.setText("");
				org.setText("");
				category.setText("");
				desc.setText("");
				endTime.setEditable(false);
				btnChange.setEnabled(false);
				btnDelete.setEnabled(false);
			 }
			 else
				JOptionPane.showMessageDialog( new JFrame(), "No booking need to delete.");
		}
		
	}
	
	/**
	 * ActionListener method for change button.
	 */
	private static void changeBooking() {
		if(strDate.contentEquals(date.getText()) && strStartTime.contentEquals(startTime.getText()) && strEndTime.contentEquals(endTime.getText()))
			JOptionPane.showMessageDialog( new JFrame(), "No change need to do.");
		else
			RoomScheduler.changeBooking(book, strDate, date.getText(), startTime.getText(), endTime.getText());
	}
}
