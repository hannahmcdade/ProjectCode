package pasCode;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PASMain implements Runnable {

	/**
	 * create an instance of class room
	 */
	private TreatmentRoom treatmentRoom;

	/**
	 * Array list of treatment rooms
	 */
	ArrayList<TreatmentRoom> rooms;

	/**
	 * linked list of patients within the a and e queue
	 */
	LinkedList<Patient> queue;

	/**
	 * boolean to establish when the patient has finished treatment
	 */
	boolean finished = false;

	/**
	 * main method to launch the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("hello world");

		PASMain pas = new PASMain();
		pas.initialise();
		// pas.test2();
		pas.testRun();
		pas.start();

	}

	/**
	 * method to start the thread for the whole system
	 */
	private void start() {
		System.out.println("Thread Starting..");
		Thread t = new Thread(this);
		t.start();
	}

	/**
	 * what does this do?
	 */
	public void test2() {
		ThreadTest t1 = new ThreadTest("ONE");
		ThreadTest t2 = new ThreadTest("TWO");

		t1.start();
		t2.start();
	}

	/**
	 * method to test the thread for the treatment rooms and return the patient
	 * in the room
	 */
	public void testRun() {

		System.out.println("ROOMS");

		TreatmentRoom temp = (TreatmentRoom) rooms.get(1);
		temp.setPatient(new Patient());
		rooms.remove(1);
		rooms.add(1, temp);

		// setting the date and time - not 100% sold on this way yet
		try {
			int testTime = 1;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			String date = "24-04-2015 19:16:12";
			Date test1 = sdf.parse(date);

			printRooms();
			printQueue();

			addPatientToTreatmentRoom(new Patient());
			date = "24-04-2015 19:17:12";
			test1 = sdf.parse(date);
			addPatientToTreatmentRoom(new Patient());
			date = "24-04-2015 19:52:12";
			test1 = sdf.parse(date);
			addPatientToTreatmentRoom(new Patient());
			date = "24-04-2015 19:18:12";
			test1 = sdf.parse(date);
			addPatientToTreatmentRoom(new Patient());
			date = "24-04-2015 19:20:12";
			test1 = sdf.parse(date);
			addPatientToTreatmentRoom(new Patient());

			printRooms();

			date = "25-04-2015 19:07:12";
			test1 = sdf.parse(date);
			addPatientToTreatmentRoom(new Patient());

			for (int i = 0; i < 5; i++) {

				date = "25-04-2015 19:57:12";
				test1 = sdf.parse(date);
				addPatientToTreatmentRoom(new Patient());
				// test1=LocalDateTime.of(2015,4,25,18,testTime);
				testTime++;
				System.out.println(treatmentRoom.getRoomNumber() + "Free: "
						+ treatmentRoom.roomFree());
			}

			date = "25-04-2015 19:07:12";
			test1 = sdf.parse(date);
			addPatientToTreatmentRoom(new Patient());

		}

		catch (Exception e) {
		}
		printQueue();

		Patient p = new Patient();
		p.setTriageCategory("Emergency");
		System.out.println(p.getTimePatientJoinsQueue());
		addPatientToTreatmentRoom(p);
		printQueue();
		movePatientToTopOfQueue();
		printRooms();
		printQueue();
	}

	public void printQueue() {
		System.out.println("PRINTING QUEUE");
		System.out.println("--------------------");
		for (int i = 0; i < queue.size(); i++) {
			System.out.println(queue.get(i).getFirstName());
		}
		System.out.println("--------------------");
	}

	public void printRooms() {

		System.out.println("PRINTING ROOMS");
		System.out.println("--------------------");
		System.out.println(rooms.size());
		for (int i = 0; i < rooms.size(); i++) {
			System.out.println("Room: " + i + " Name : "
					+ rooms.get(i).getPatient().getFirstName());
		}
	}

	/**
	 * method to initialise variables
	 */
	public void initialise() {
		// this need to be populated from database
		queue = new LinkedList<Patient>();

		// populating array list of treatment rooms
		rooms = new ArrayList<TreatmentRoom>();
		rooms.add(new TreatmentRoom(0));
		rooms.add(new TreatmentRoom(1));
		rooms.add(new TreatmentRoom(2));
		rooms.add(new TreatmentRoom(3));
		rooms.add(new TreatmentRoom(4));
	}

	/**
	 * method to allocate patient to a room
	 * 
	 * @param patient
	 */
	public void addPatientToTreatmentRoom(Patient patient) {
		boolean roomFound = false;
		// patient = getPatientDetails(patient.getNHS());

		String category = getPatientCategory();
		int freeRoom = freeTreatmentRoom();

		if (freeRoom != -1)
			sendPatientToTreatmentRoom(patient, freeRoom);
		else if (patient.getTriageCategory().equals("Emergency")) {
			redirectEmergencyPatient(patient);
		} else
			sendPatientToAnotherHospital(patient);
	}

	/**
	 * method to send an emergency patient to another hospital if you cannot
	 * place them into a treatment room i.e. all treatment rooms are already
	 * filled with emergency patients
	 * 
	 * @param patient
	 */
	public void redirectEmergencyPatient(Patient patient) {
		if (!forceEmergencyPatientIntoTreatmentRoom(patient))
			if (!alertOnCallTeam()) {
				sendToNearestHospital(patient);
				alertHospitalManager();
			}
	}

	/**
	 * method to allocate an emergency patient to a room and remove the non
	 * emergency patient in the room
	 * 
	 * @param patient
	 * @return
	 */
	private boolean forceEmergencyPatientIntoTreatmentRoom(Patient patient) {
		// TODO Auto-generated method stub
		System.out.println("FORCE PATIENT INTO ROOM");
		boolean roomFound = false;

		int room = -1;
		int currentCategory = -1;
		Date currentTime = new Date();

		for (int i = 0; i < rooms.size(); i++) {
			if (!(rooms.get(i).getPatientCategory() == 1)) {
				if (rooms.get(i).getPatient().getCategoryAsInt() > currentCategory) {
					room = i;
					currentCategory = rooms.get(i).getPatientCategory();
					currentTime = rooms.get(i).getPatient().getTimePatientJoinsQueue();
				} else if (rooms.get(i).getPatient().getCategoryAsInt() == currentCategory
						&& rooms.get(i).getPatient().getTimePatientJoinsQueue()
								.compareTo(currentTime) > 0) {
					room = i;
					currentCategory = rooms.get(i).getPatientCategory();
					currentTime = rooms.get(i).getPatient().getTimePatientJoinsQueue();
				}
			}
		}

		if (room != -1) {
			queue.addFirst(rooms.get(room).getPatient());
			sendPatientToTreatmentRoom(patient, room);
			roomFound = true;
		}
		// try and get a room
		// if room found set roomFound to true

		return roomFound;
	}

	/**
	 * if the patient cannot be place in the queue they are sent to the nearest
	 * hospital
	 * 
	 * @param patient
	 */
	public void sendPatientToAnotherHospital(Patient patient) {
		if (!putIntoQueue(patient))
			// alert on GUI to be here
			sendToNearestHospital(patient);
	}

	/**
	 * method to send patient to nearest hospital
	 * 
	 * @param patient
	 */
	private void sendToNearestHospital(Patient patient) {

		System.out.println("SEND TO HOSPITAL");
	}

	/**
	 * method to send alerts to manager
	 */
	private void alertHospitalManager() {
		// TODO Auto-generated method stub
		System.out.println("MANAGER ALERT");
	}

	/**
	 * method to alert OnCall team on call are alerted when - queue reaches 10
	 * and if treatment rooms are full of emergency patients and a new emergency
	 * patient arrives
	 * 
	 * @return
	 */
	private boolean alertOnCallTeam() {

		boolean alerted = false;
		System.out.println("ALERT THE ON CALL");
		return alerted;
	}

	/**
	 * method to allow a patient who has been waiting 25 minutes to be moved to
	 * top of queue
	 */
	public void movePatientToTopOfQueue() {
		System.out.println("UPDATE QUEUE");
		long t = 0;
		long t2 = new Date().getTime();
		long minutes = 0;
		Patient p;

		for (int i = 0; i < queue.size(); i++) {
			t = queue.get(i).getTimePatientJoinsQueue().getTime();
			minutes = TimeUnit.MILLISECONDS.toMinutes(t2 - t);
			if (minutes > 24) {
				p = queue.get(i);
				queue.remove(i);
				queue.addFirst(p);
			}
		}
	}

	/**
	 * method to clear treatment room
	 */
	public void updateTreatmentRooms() {
		Date now = new Date();
		for (int i = 0; i < rooms.size(); i++) {
			if (rooms.get(i).getDischargeTime().compareTo(now) > 0)
				rooms.get(i).clearRoom();
		}

	}

	/**
	 * method to add patient to queue
	 * 
	 * @param patient
	 * @return
	 */
	private boolean putIntoQueue(Patient patient) {

		boolean onQueue = false;

		if (queue.size() < 10) {
			queue.add(patient);
			onQueue = true;
			System.out.println("PUT ON QUEUE AT POSITION " + queue.size());
		}

		return onQueue;
	}

	/**
	 * method to allocate a patient to a treatment room and treat them
	 * 
	 * @param p
	 * @param room
	 */
	public void sendPatientToTreatmentRoom(Patient p, int room) {
		rooms.get(room).setPatient(p);
		System.out.println("PATIENT TREATED");
	}

	
	/**
	 * method to return which treatment room is free
	 * @return
	 */
	public int freeTreatmentRoom() {
		
		//array lists start from an index of 0, therefore -1 must be used
		int free = -1;
		
		for (int i = 0; i < rooms.size(); i++) {
			if (rooms.get(i).roomFree()) {
				free = i;
				break;
			}
		}

		return free;
	}

	/**
	 * method to get patient details DATABASE
	 * 
	 * @param NHS
	 * @return
	 */
	public Patient getPatientDetails(String NHS) {
		Patient p = new Patient();
		p.setTriageCategory("Emergency");
		// perform DB lookup to get patient details
		// with data, set properties of p e.g. setName() etc
		// return p
		return p;
	}

	// GUI
	/**
	 * method to get the category from the triage nurse and pass it to method to
	 * convert to an integer value to sort the queue
	 * 
	 * @return
	 */
	public String getPatientCategory() {
		// ask user for category
		String category = "";
		return category;
	}

	/**
	 * final thread
	 */
	@Override
	public void run() {

		int i = 0;
		while (!finished) {

			try {
				Thread.sleep(5000);
				updateTreatmentRooms();
				movePatientToTopOfQueue();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
			if (i > 10)
				finished = true;
		}

	}

}
