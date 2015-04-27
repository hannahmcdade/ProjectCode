package pasCode;

/**
 * interface to be implemented by staff members to allow them to login to the system
 *
 */

public interface ILogin {
	
	/**
	 * method to allow staff members to log into the system
	 * @param staffID
	 * @param password
	 * @return
	 */
	public Staff login(String staffID, String password);

}
