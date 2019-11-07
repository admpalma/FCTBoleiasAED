package fctBoleias;

import basicDateTime.BasicDateTime;
import basicDateTime.InvalidDateException;
import dataStructures.List;
import dataStructures.Map;
import dataStructures.SepChainHashTable;
import dataStructures.SortedMap;
import fctBoleias.trip.CantRideSelfException;
import fctBoleias.trip.InvalidDataException;
import fctBoleias.trip.Trip;
import fctBoleias.trip.TripHasRidesException;
import fctBoleias.user.IncorrectPasswordException;
import fctBoleias.user.User;
import fctBoleias.user.UserClass;

public class ManagerClass implements Manager {
	
	/**
	 * Regex format defining a new {@link User User's} valid password
	 */
	private static final String PASSWORD_FORMAT = "^[A-Za-z0-9]{4,6}$";
	
	private User currentUser;
	private Map<String, User> usersByEmail; // Key: user email
	private SortedMap<BasicDateTime, SortedMap<String, Trip>> ridesByDate; // Rides by date
	private List<Trip> allTrips;

	public ManagerClass() {
		this.currentUser = null;
		usersByEmail = new SepChainHashTable<String, User>();
	}

	@Override
	public boolean isLoggedIn() {
		return currentUser != null;
	}

	@Override
	public void addNewTrip(String origin, String destiny, String date, String hourMinute, int duration, int numberSeats)
			throws NotLoggedInException, InvalidDataException, DateOccupiedException {
		if (currentUser == null) {
			throw new NotLoggedInException();
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
		}
		if (isUserRegistered(email)) {
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
		User user = usersByEmail.find(email);
		if (user == null) {
			throw new NonExistentUserException();
		}
		if (!user.checkPassword(password)) {
			throw new IncorrectPasswordException();
		} else {
			user.addLogin();
		}
		return user.getNumberLogins();
	}

}
