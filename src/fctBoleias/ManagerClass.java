package fctBoleias;

import basicDateTime.BasicDateTime;
import basicDateTime.BasicDateTimeClass;
import basicDateTime.InvalidDateException;
import dataStructures.Iterator;
import dataStructures.Map;
import dataStructures.NoElementException;
import dataStructures.SepChainHashTable;
import dataStructures.SortedMap;
import dataStructures.SortedMapWithJavaClass;
import fctBoleias.trip.CantRideSelfException;
import fctBoleias.trip.InvalidTripDataException;
import fctBoleias.trip.Trip;
import fctBoleias.trip.TripClass;
import fctBoleias.trip.TripHasRidesException;
import fctBoleias.trip.TripIsFullException;
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

	public ManagerClass() {
		this.currentUser = null;
		// TODO capacity
		usersByEmail = new SepChainHashTable<String, User>(10000);
		tripsByDate = new SortedMapWithJavaClass<BasicDateTime, SortedMap<String, Trip>>();
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
		} else if (!(duration > 0)) {
			throw new InvalidTripDataException();
		}
		try {
			BasicDateTime dateTime = new BasicDateTimeClass(date, hourMinute);
			Trip newTrip = new TripClass(origin, destiny, dateTime, numberSeats, duration, currentUser);
			currentUser.addTrip(newTrip);
			SortedMap<String, Trip> tripsInDay = tripsByDate.find(dateTime);
			if (tripsInDay == null) {
				tripsInDay = new SortedMapWithJavaClass<String, Trip>();
			}
			tripsInDay.insert(currentUser.getEmail(), newTrip);
			tripsByDate.insert(dateTime, tripsInDay);
		} catch (InvalidDateException e) {
			throw new InvalidTripDataException(e);
		}

	}

	@Override
	public String getCurrentUserName() {
		try {
			return currentUser.getName();
		} catch (NullPointerException e) {
			throw new NotLoggedInException(e);
		}
	}

	@Override
	public void remove(String date)
			throws NotLoggedInException, NoTripOnDayException, TripHasRidesException, InvalidDateException {
		if (currentUser == null) {
			throw new NotLoggedInException();
		}
		BasicDateTime newDate = new BasicDateTimeClass(date);

		currentUser.removeTrip(newDate);

		// We know it was removed from the user if it gets to here cause no exception
		tripsByDate.find(newDate).remove(currentUser.getEmail());

	}

	@Override
	public void addNewRide(String email, String date)
			throws NotLoggedInException, CantRideSelfException, DateOccupiedException, NonExistentUserException,
			InvalidDateException, NonExistentTripException, TripIsFullException {
		User tripDriver = usersByEmail.find(email);
		BasicDateTime newDate;

		if (currentUser == null) {
			throw new NotLoggedInException();
		} else if (tripDriver == null) {
			throw new NonExistentUserException();
		} else {
			newDate = new BasicDateTimeClass(date);
		}
		if (!tripDriver.hasTripOnDate(newDate)) {
			throw new NonExistentTripException();
		} else if (email.equals(currentUser.getEmail())) {
			throw new CantRideSelfException(tripDriver);
		} else if (currentUser.hasRideOnDate(newDate) || currentUser.hasTripOnDate(newDate)) {
			throw new DateOccupiedException(currentUser);
		}
		Trip ride = tripDriver.addUserToTrip(currentUser, newDate);
		currentUser.addRide(ride);
	}

	@Override
	public boolean isUserRegistered(String email) {
		return usersByEmail.find(email) != null;
	}

	@Override
	public int registerUser(String email, String name, String password)
			throws InvalidPasswordFormatException, UserAlreadyRegisteredException {
		if (!password.matches(PASSWORD_FORMAT)) {
			throw new InvalidPasswordFormatException();
		} else if (isUserRegistered(email)) {
			// TODO
			throw new UserAlreadyRegisteredException();
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
			throw new NotLoggedInException(e);
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
		try {
			return currentUser.getNumberTrips();
		} catch (NullPointerException e) {
			throw new NotLoggedInException(e);
		}
	}

	@Override
	public Trip consult(String email, String date)
			throws NotLoggedInException, NonExistentTripException, NonExistentUserException, InvalidDateException {
		User tripDriver = usersByEmail.find(email);

		if (currentUser == null) {
			throw new NotLoggedInException();
		} else if (tripDriver == null) {
			throw new NonExistentUserException();
		}

		BasicDateTime newDate = new BasicDateTimeClass(date);

		return tripDriver.getTrip(newDate);
	}

	@Override
	public void cancelCurrentUserRide(String date)
			throws NotLoggedInException, InvalidDateException, NoRideOnDayException {
		if (currentUser == null) {
			throw new NotLoggedInException();
		}
		BasicDateTime processedDate = new BasicDateTimeClass(date);
		Trip removedRide = currentUser.cancelRide(processedDate);
		removedRide.removeUserRide(currentUser);
	}

	@Override
	public Iterator<Trip> getUserTrips(String email) throws NotLoggedInException, NoRegisteredTripsException, NonExistentUserException {
		if (currentUser == null) {
			throw new NotLoggedInException();
		}

		try {
			return usersByEmail.find(email).getTripsIterator();
		} catch (NullPointerException e) {
			throw new NonExistentUserException(e);
		}
	}

	@Override
	public Iterator<Trip> getCurrentUserRides()
			throws NotLoggedInException, NoRegisteredTripsException {
		if (currentUser == null) {
			throw new NotLoggedInException();
		}
		return currentUser.getRidesIterator();
	}

	@Override
	public Iterator<Trip> getTripsOnDate(String date) throws NotLoggedInException, InvalidDateException {
		if (currentUser == null) {
			throw new NotLoggedInException();
		}
		BasicDateTime newDate = new BasicDateTimeClass(date);
		try {
			return tripsByDate.find(newDate).values();
		} catch (NoElementException e) {
			throw new InvalidDateException(e);
		} catch (NullPointerException e) {
			return null;
		}
	}

	@Override
	public Iterator<SortedMap<String, Trip>> getAllTrips() throws NotLoggedInException {
		if (currentUser == null) {
			throw new NotLoggedInException();
		}
		return tripsByDate.values();
	}

}
