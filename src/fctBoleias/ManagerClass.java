package fctBoleias;

import basicDateTime.BasicDateTime;
import basicDateTime.BasicDateTimeClass;
import basicDateTime.InvalidDateException;
import dataStructures.List;
import dataStructures.Map;
import dataStructures.SepChainHashTable;
import dataStructures.SortedMap;
import dataStructures.SortedMapWithJavaClass;
import fctBoleias.trip.CantRideSelfException;
import fctBoleias.trip.InvalidTripDataException;
import fctBoleias.trip.Trip;
import fctBoleias.trip.TripClass;
import fctBoleias.trip.TripHasRidesException;
import fctBoleias.user.IncorrectPasswordException;
import fctBoleias.user.User;
import fctBoleias.user.UserClass;

public class ManagerClass implements Manager {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Regex format defining a new {@link User User's} valid password
	 */
	private static final String PASSWORD_FORMAT = "^[A-Za-z0-9]{4,6}$";
	
	private User currentUser;
	private Map<String, User> usersByEmail; // Key: user email
	private SortedMap<BasicDateTime, SortedMap<String, Trip>> tripsByDate; // Rides by date
	private List<Trip> allTrips;

	public ManagerClass() {
		this.currentUser = null;
		usersByEmail = new SepChainHashTable<String, User>();
		tripsByDate = new SortedMapWithJavaClass<BasicDateTime, SortedMap<String,Trip>>();
	}

	@Override
	public boolean isLoggedIn() {
		return currentUser != null;
	}

	@Override
	public void addTrip(String origin, String destiny, String date, String hourMinute, int duration, int numberSeats)
			throws NotLoggedInException, InvalidTripDataException, DateOccupiedException {
		if (currentUser == null) {
			throw new NotLoggedInException();
		} else if (duration <= 0) {
			throw new InvalidTripDataException();
		}
		try {
			BasicDateTime dateTime = new BasicDateTimeClass(date + " " + hourMinute);
			SortedMap<String, Trip> tripsInDay = tripsByDate.find(dateTime);
			if (tripsInDay == null) {
				tripsInDay = new SortedMapWithJavaClass<String, Trip>();
			}
			Trip newTrip = new TripClass(origin, destiny, dateTime, numberSeats, duration, currentUser);
			tripsInDay.insert(currentUser.getEmail(), newTrip);
			currentUser.addTrip(newTrip);
		} catch (InvalidDateException e) {
			throw new InvalidTripDataException();
		}
		

	}

	@Override
	public String getCurrentUserName() {
		try {
			return currentUser.getName();
		} catch (NullPointerException e) {
			return null;
		}
	}

	@Override
	public void remove(String date) throws NotLoggedInException, NoTripOnDayException, TripHasRidesException {
		// TODO Auto-generated method stub
		if (currentUser == null) {
			throw new NotLoggedInException();
		}
		// TODO THROW INVALID DATE EXCEPTION WITH PROPER MESSAGE
	}

	@Override
	public void addNewRide(String name, String date) throws NotLoggedInException, CantRideSelfException, DateOccupiedException,
			NonExistentUserException, InvalidDateException, NonExistentTripException {
		// TODO Auto-generated method stub
		if (currentUser == null) {
			throw new NotLoggedInException();
		}
		// TODO THROW INVALID DATE EXCEPTION WITH PROPER MESSAGE
	}

	@Override
	public boolean isUserRegistered(String email) {
		return usersByEmail.find(email) != null;
	}

	@Override
	public int registerUser(String email, String name, String password) throws InvalidPasswordFormatException, IllegalArgumentException {
		if (!password.matches(PASSWORD_FORMAT)) {
			throw new InvalidPasswordFormatException();
		} else if (isUserRegistered(email)) {
			//TODO
			throw new IllegalArgumentException();
		}
		usersByEmail.insert(email, new UserClass(email, name, password));
		return usersByEmail.size();
	}

	@Override
	public String logoutCurrentUser() throws NotLoggedInException {
		String currentUserName = getCurrentUserName();
		currentUser = null;
		return currentUserName;
	}

	@Override
	public String getCurrentUserEmail() {
		try {
			return currentUser.getEmail();
		} catch (NullPointerException e) {
			return null;
		}
	}

	@Override
	public int userLogin(String email, String password) throws NonExistentUserException, IncorrectPasswordException {
		if (isLoggedIn()) {
			throw new LoggedInException();
		}
		User user = usersByEmail.find(email);
		if (user == null) {
			throw new NonExistentUserException();
		} else if (!user.checkPassword(password)) {
			throw new IncorrectPasswordException();
		} else {
			user.addLogin();
			currentUser = user;
		}
		return user.getNumberLogins();
	}

	@Override
	public int getCurrentUserTripNumber() {
		return currentUser.getNumberTrips();
	}

}
