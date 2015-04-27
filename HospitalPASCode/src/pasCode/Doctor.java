package pasCode;

/**
 * Class to contain the details of the Doctor
 *
 */

public class Doctor extends Staff implements ILogin {

	/**
	 * Default constructor for Doctor Class
	 */
	public Doctor() {

	}
	

	/**
	 * constructor with arguments for doctor Class
	 * 
	 * @param title
	 * @param firstName
	 * @param lastName
	 * @param gender
	 * @param staffID
	 * @param password
	 */
	public Doctor(String title, String firstName, String lastName, char gender,
			int staffID, String password) {
		super(title, firstName, lastName, gender, staffID, password);

	}

}