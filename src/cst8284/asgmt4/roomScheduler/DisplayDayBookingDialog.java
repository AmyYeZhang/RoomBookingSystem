package cst8284.asgmt4.roomScheduler;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import cst8284.asgmt4.room.Room;
/**
 * Class DisplayDayBookingDialog is used to show a whole day room booking information.
 * Customer could input the date, and search the whole day booking information.
 * Built up in assignment 4.
 * @author Ye Zhang
 * @version 1.04
 */
public class DisplayDayBookingDialog {
//DisplayDayBookingDialog referred code from Professor Dave Houtman
	private static final GridBagConstraints labelConstraints2 = new GridBagConstraints(
	    	0, GridBagConstraints.RELATIVE, 1, 1, 1, 1, 
	    	GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 20), 0, 0);
	private static final GridBagConstraints textConstraints2 = new GridBagConstraints(
		    1, GridBagConstraints.RELATIVE, 1, 1, 1, 1,  // gridx, gridy, gridwidth, gridheight, weightx, weighty
		    GridBagConstraints.EAST, 0, new Insets(5, 5, 5, 10), 20, 20); // anchor, fill, insets, ipadx, ipady
	private static final GridBagConstraints btnConstraints2 = new GridBagConstraints(
		    0, GridBagConstraints.RELATIVE, 2, 1, 0.5, 0.5, 
		    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 20), 0, 0);
	private static final GridBagConstraints textAreaConstraints2 = new GridBagConstraints(
		    0, GridBagConstraints.RELATIVE, 120, 150, 1, 1,  // gridx, gridy, gridwidth, gridheight, weightx, weighty
		    GridBagConstraints.CENTER, 0, new Insets(5, 5, 5, 10), 120, 150); // anchor, fill, insets, ipadx, ipady
	private static Panel cp2 = new Panel();
	private static final int labelWidth2 = 24;
	private static final FlowLayout btnRow2 = new FlowLayout();
	private static final JPanel btnPanel2 = new JPanel();

	/**
	 * Load and show the dialog for daily searching.
	 * @param room Room information
	 */
	public static void showDayBookingDialog(Room room) {
		cp2.removeAll();
		cp2.repaint();
	    JFrame f = new JFrame("");  
	    cp2.setLayout(new GridBagLayout());
		f.setTitle(room.getRoomNumber() + " ( " + room.getRoomType() +" / " + room.getSeats() + " seats / " + room.getDetails() + " )");
	    
	    // Set the first seven rows with Label/TextField
	    JTextField date = setRow2("Booking Date (DDMMYYYY):", 'e');

		//cp2.add(bookingText, textAreaConstraints2);
	    JTextArea bookingText = new JTextArea();
	    bookingText.setFont(RoomSchedulerDialog.defaultMainFont);
	    JScrollPane centerPane = new JScrollPane(bookingText);
		centerPane.setPreferredSize(new Dimension(400, 500));
		JPanel jp = new JPanel();
		jp.add(centerPane);
	    cp2.add(centerPane, textAreaConstraints2);
	    btnPanel2.removeAll();
	    btnPanel2.repaint();
	    btnPanel2.setLayout(btnRow2);

	    setBtnRow2("   Search   ", 's', e->{ String booking = RoomScheduler.displayDayBooking(date.getText());
	    										if(booking!=null) bookingText.setText(booking);
	    										date.requestFocus();});
	    setBtnRow2("    Exit    ", 'x', (e)->f.dispose());
       
	    cp2.add(btnPanel2, btnConstraints2);
	    f.add(cp2); f.pack();
	    cp2.revalidate();
	    
	    // Close dialog
	    f.addWindowListener(new WindowAdapter() {
	       public void windowClosing(WindowEvent evt) {
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
	private static JTextField setRow2(String label, char keyboardShortcut) {
		JLabel l; JTextField t;
		cp2.add(l = new JLabel(label, SwingConstants.RIGHT), labelConstraints2);
		l.setFont(RoomBookingDialog.defaultFont);
		l.setDisplayedMnemonic(keyboardShortcut);
	    cp2.add(t = new JTextField(labelWidth2), textConstraints2);
	    t.setFont(RoomBookingDialog.defaultFont);
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
	private static JButton setBtnRow2(String label, char keyboardShortcut, ActionListener act) {
		JButton btn = new JButton(label);
		btn.setFont(RoomBookingDialog.defaultFont);
		btn.setMnemonic(keyboardShortcut);
		btnPanel2.add(btn);
		btn.addActionListener(act);
		return btn;
	}
}
	
