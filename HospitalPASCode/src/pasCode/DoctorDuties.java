package pasCode;

/**
 * Class containing the duties that the Doctor in the Treatment Rooms can carry out 
 *
 */

public class DoctorDuties implements IDoctor, ICategorise {
	
	/**
	 * Method to allow the Doctor to allocate extra time to a patient in the
	 * Treatment Room
	 */
	@Override
	public void allocateExtraTime() {

	}

	/**
	 * method to initially categorise the patient when they arrive to the A and
	 * E - once the patient is categorised they are automatically added to the
	 * queue - only to be used by triage Nurse
	 * 
	 * @return
	 */
	@Override
	public boolean categorisePatient(Patient patient, Triage category)
			throws UserException {

		return false;
	}

	/**
	 * method to change the category of the patient and automatically change
	 * their position in the queue
	 * 
	 * @return
	 */
	@Override
	public boolean recategorisePatient(Patient patient, Triage triage)
			throws UserException {

		return false;
	}

}


