package cst8284.asgmt4.roomScheduler;
import cst8284.asgmt4.room.ComputerLab;
import cst8284.asgmt4.roomScheduler.RoomScheduler;
/**
 * Class RoomSchedulerLauncher is the main entrance for Room Booking system.
 * Built up in assignment 1, add room part in assignment 2, changed in assignment 4.
 * @author Ye Zhang
 * @version 1.04
 */
public class RoomSchedulerLauncher {

	/**
	 * Main entrance of Room Booking system.
	 * @param args a String array
	 */
	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new RoomScheduler(new ComputerLab("B119")).launch();
			}
		});
	}

}
