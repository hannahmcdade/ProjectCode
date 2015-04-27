package pasCode;

/**
 * Class for Treatment rooms
 */

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TreatmentRoom {

	//this can all be passed along in patient object 
	private int number;
	private Patient patient;
	private boolean free;
	Date admitted;
	Date discharge;
	final int treatmentTime = 10;
	final int extension = 5;

	/**
	 * constructor for treatment rooms
	 * @param n
	 */
	public TreatmentRoom(int n) {
		number = n;
		free = true;
		patient = null;
	}

	/**
	 * get discharge time of patient
	 * @return
	 */
	public Date getDischargeTime() {
		// TODO Auto-generated method stub
		return discharge;
	}

	/**
	 * get patient in the treatment room
	 * @return
	 */
	public Patient getPatient() {
		if (patient == null)
			//needs to be retuned as the object
			return new Patient();
		return patient;
	}

	/**
	 * set patient in treatment room
	 * @param p
	 */
	public void setPatient(Patient p) {
		patient = p;
		free = false;
		admitted = new Date();
		discharge = new Date(admitted.getTime()
				+ TimeUnit.MINUTES.toMillis(treatmentTime));
	}

	/**
	 * method to allow the doctor to allocate extra time in treatment room
	 */
	public void increaseDischarge() {
		discharge.setTime(discharge.getTime()
				+ TimeUnit.MINUTES.toMillis(extension));
	}

	/**
	 * method to set the room as free when the patient is treated
	 */
	public void clearRoom() {
		free = true;
		patient = null;
	}

	/**
	 * returns the room as free
	 * @return
	 */
	public boolean roomFree() {
		return free;
	}

	/**
	 * method to get the number of the room that the patient is to be sent to 
	 * @return
	 */
	public int getRoomNumber() {
		return number;
	}

	/**
	 * method to set the room number 
	 * @param n
	 */
	public void setRoomNumber(int n) {
		number = n;
	}

	/**
	 * method to get the patients NHS category so an non emergency patient can be kicked out and an emergency patient put in 
	 * @return
	 */
	public int getPatientCategory() {
		return patient.getCategoryAsInt();
	}
}