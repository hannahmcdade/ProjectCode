package pasCode;

/**
 * interface to be implemented by the receptionist to allow the receptionist to be registered by a and e
 */

import java.util.List;

import pasCode.UserException;


public interface IReceptionist {

	/**
	 * method to allow the receptionist to register the patient to a and e by pulling their details from the database
	 * @param patientList
	 * @param patient
	 * @throws UserException
	 */
	public void registerPatientToAandE(List<Patient> patientList,Patient patient) throws UserException;

}

