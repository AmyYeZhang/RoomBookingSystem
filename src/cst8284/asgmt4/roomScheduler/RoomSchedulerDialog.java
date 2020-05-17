package cst8284.asgmt4.roomScheduler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import cst8284.asgmt4.room.Room;
/**
 * Class RoomSchedulerDialog is the main dialog of the Room Booking system.
 * Left side buttons are the main entrance for add / change / delete room booking functions.
 * Also provide backup to file, and load from file functions.
 * Right side scroll panel shows the current day's room booking information.
 * Built up in assignment 4.
 * @author Ye Zhang
 * @version 1.04
 */
public class RoomSchedulerDialog extends JFrame {
//RoomSchedulerDialog referred code from Professor Dave Houtman's Lab 9 code
	private static final long serialVersionUID = 1L;
	private static final Toolkit tk = Toolkit.getDefaultToolkit();
	private static final Dimension screenSize = tk.getScreenSize();
	private static final JTextArea scrollText = new JTextArea();
	private static JFrame frame = new JFrame();
	protected static final Font defaultMainFont = new Font("SansSerif", Font.PLAIN, 15);	
	private static Room roomInfo;

	/**
	 * Load and show the main dialog, set the title with room information and current date.
	 * @param room Room information
	 */
	public static void loadAndShowScheduler(Room room) {
		frame.setTitle("Room Bookings for " + room.getRoomNumber() + " for " + new SimpleDateFormat("MMM. dd, yyyy").format(new Date()) );
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		roomInfo = room;

		int screenX = (int) screenSize.getWidth() / 2;
		int screenY = (int) (7 * screenSize.getHeight() / 8);
		
		scrollText.setFont(defaultMainFont);
		reloadJTextArea();
		frame.add(getWestPanel(), BorderLayout.WEST);
		frame.add(getCenterPanel(scrollText, screenY), BorderLayout.CENTER);
		frame.setPreferredSize(new Dimension(screenX, screenY));

		frame.pack();
		frame.setVisible(true);
		
	}

	/**
	 * Set the center panel of the main dialog.
	 * @param jta A JTextArea set on the center panel.
	 * @param height The height of the center panel.
	 * @return return a JPanel object.
	 */
	public static JPanel getCenterPanel(JTextArea jta, int height) {
		JScrollPane centerPane = new JScrollPane(jta);
		centerPane.setPreferredSize(new Dimension(400, 7 * height / 8));
		JPanel jp = new JPanel();
		jp.add(centerPane);
		return jp;
	}

	/**
	 * Set the west panel of the main dialog, includes buttons.
	 * @return return a JPanel object.
	 */
	public static JPanel getWestPanel() {
		JPanel controlPanel = new JPanel(new GridLayout(6, 1));
		JPanel westPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.weighty = 1;

		controlPanel.add(setWestPanelBtns("      Add Booking          ", e->RoomBookingDialog.showAppointmentDialog(roomInfo, 1)));
		controlPanel.add(setWestPanelBtns(" Search & Change a Booking ", (e)->RoomBookingDialog.showAppointmentDialog(roomInfo, 2)));
		controlPanel.add(setWestPanelBtns("   Display Day Bookings    ", (e)->DisplayDayBookingDialog.showDayBookingDialog(roomInfo)));
		controlPanel.add(setWestPanelBtns(" Backup Bookings to File   ", (e)->JOptionPane.showMessageDialog( new JFrame(), RoomScheduler.saveBookingsToFile())));
		controlPanel.add(setWestPanelBtns(" Load Bookings from File   ", (e)->JOptionPane.showMessageDialog( new JFrame(), RoomScheduler.loadBookingsFromFile())));
		controlPanel.add(setWestPanelBtns("           Exit            ", (ActionEvent e)->{RoomScheduler.saveBookingsToFile();frame.dispose();}));
		
		westPanel.add(controlPanel, gbc);
		return westPanel;
	}

	/**
	 * Set the button on the west panel.
	 * @param btnLabel button label
	 * @param act ActionListener for the button
	 * @return return a JButton object.
	 */
	private static JButton setWestPanelBtns(String btnLabel, ActionListener act) {
		final Font font = new Font("SansSerif", Font.PLAIN, 20);
		JButton btn = new JButton(btnLabel);
		btn.setFont(font);
		btn.addActionListener(act);
		return btn;
	}

	/**
	 * Reload information in the scroll panel.
	 */
	public static void reloadJTextArea() {
		scrollText.setText(RoomScheduler.displayDayBooking(Calendar.getInstance()));
	}

}
