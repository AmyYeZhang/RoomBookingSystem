package cst8284.asgmt4.roomScheduler;
import java.io.Serializable;
/**
 * Class ContactInfo is used to save Contact name, phone number, and organization of room booking.
 * Built up in assignment 1, implements Serializable in assignment 2 for File IO function.
 * @author Ye Zhang
 * @version 1.02
 */

public class ContactInfo implements Serializable {
	public static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String organization;
	
	/**
	 * Constructor of class ContactInfo with 4 parameters, use setter to set the value for private fields.
	 * @param firstName the first name of contact
	 * @param lastName the last name of contact
	 * @param phoneNumber the phone number of contact, format is 'xxx-xxx-xxxx', the value is checked when input is done
	 * @param organization the organization of contact, it could be ""
	 */
	public ContactInfo(String firstName, String lastName, String phoneNumber, String organization) {
		setFirstName(firstName);
		setLastName(lastName);
		setPhoneNumber(phoneNumber);
		setOrganization(organization);
	}
	
	/**
	 * Constructor of class ContactInfo with 3 parameters, chain to constructor with 4 parameters, the default value for organization is "Algonquin College".
	 * @param firstName the first name of contact
	 * @param lastName the last name of contact
	 * @param phoneNumber the phone number of contact, format is 'xxx-xxx-xxxx', the value is checked when input is done
	 */
	public ContactInfo(String firstName, String lastName, String phoneNumber) {
		this(firstName, lastName, phoneNumber, "Algonquin College");	
	}
	
	/**
	 * Getter for firstName
	 * @return return the first name of contact
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Setter for firstName
	 * @param firstName the actual parameter will be checked before setter
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Getter for lastName
	 * @return return the last name of contact
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Setter for lastName
	 * @param lastName the actual parameter will be checked before setter
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Getter for phoneNumber
	 * @return return the phone number of contact
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * Setter for phoneNumber
	 * @param phoneNumber the actual parameter will be checked before setter, it should match the format as 'xxx-xxx-xxxx'
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * Getter for organization
	 * @return return the organization of contact
	 */
	public String getOrganization() {
		return organization;
	}
	
	/**
	 * Setter for organization
	 * @param organization it is optional, it could be ""
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * Override toString method with specified format.
	 * @return a String with first name, last name, phone number, does not include organization part if organization has no value
	 */
	@Override
	public String toString() {
		if ( getOrganization().equals("") )
			return "Contact Infomation: " + getFirstName() + " " + getLastName() 
					+ "\nPhone: " + getPhoneNumber();
		else
			return "Contact Infomation: " + getFirstName() + " " + getLastName() 
			+ "\nPhone: " + getPhoneNumber()
			+ "\n" + getOrganization();
			
	}
	
	

}
