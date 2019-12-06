package fctBoleias;

import basicDateTime.BasicDateTime;
import basicDateTime.BasicDateTimeClass;
import basicDateTime.InvalidDateException;
import dataStructures.BasicDateSortedMap;
import dataStructures.DateIndexedMap;
import dataStructures.Iterator;
import dataStructures.IteratorWrappable;
import dataStructures.LinearProbingHashTable;
import dataStructures.Map;
import dataStructures.NestedMapValuesIterator;
import dataStructures.NoElementException;
import dataStructures.RB;
import dataStructures.SortedMap;
import fctBoleias.trip.CantRideSelfException;
import fctBoleias.trip.InvalidTripDataException;
import fctBoleias.trip.Trip;
import fctBoleias.trip.TripClass;
import fctBoleias.trip.TripHasRidesException;
import fctBoleias.trip.TripIsFullException;
import fctBoleias.trip.TripWrapper;
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
	private DateIndexedMap<BasicDateTime, SortedMap<String, Trip>> tripsByDate; // Rides by date

	public ManagerClass() {
		this.currentUser = null;
		usersByEmail = new LinearProbingHashTable<String, User>(200);
		tripsByDate = new BasicDateSortedMap<BasicDateTime, SortedMap<String, Trip>>();
	}

	@Override
	/**
	 * O(1) in all cases
	 */
	public boolean isLoggedIn() {
		return currentUser != null;
	}

	/**
	 * Best case: O(1) if insert on first node
	 * Average case: O(2*log n)
	 * Worst case: O(2 * log n)
	 */
	@Override
	public void addTrip(String origin, String destination, String date, String hourMinute, int duration,
			int numberSeats) throws NotLoggedInException, InvalidTripDataException, DateOccupiedException {
		if (currentUser == null) {
			throw new NotLoggedInException();
		} else if (!(duration > 0)) {
			throw new InvalidTripDataException();
		}
		try {
			BasicDateTime dateTime = new BasicDateTimeClass(date, hourMinute);
			Trip newTrip = new TripClass(origin, destination, dateTime, numberSeats, duration, currentUser);

			currentUser.addTrip(newTrip); // log n

			assert (currentUser.hasTripOnDate(dateTime));

			SortedMap<String, Trip> tripsInDay = tripsByDate.get(dateTime); // O(1)

			if (tripsInDay == null) {
				tripsInDay = new RB<String, Trip>();
				tripsByDate.insert(dateTime, tripsInDay); //log n
			}

			tripsInDay.insert(currentUser.getEmail(), newTrip);
		} catch (InvalidDateException e) {
			throw new InvalidTripDataException(e);
		}

	}

	/**
	 * O(1) in all cases
	 */
	@Override
	public String getCurrentUserName() {
		try {
			return currentUser.getName();
		} catch (NullPointerException e) {
			throw new NotLoggedInException(e);
		}
	}

	/**
	 * Best case: O(1) 
	 * Average case: O(3 * log n)
	 * Worst case: O(3 * log n)
	 */
	@Override
	public void remove(String date)
			throws NotLoggedInException, NoTripOnDayException, TripHasRidesException, InvalidDateException {
		if (currentUser == null) {
			throw new NotLoggedInException();
		}
		BasicDateTime newDate = new BasicDateTimeClass(date);

		currentUser.removeTrip(newDate);

		assert (!currentUser.hasTripOnDate(newDate));
		tripsByDate.get(newDate).remove(currentUser.getEmail());

	}

	/**
	 * Best case: O(1)
	 * Average case: O(5log n)
	 * Worst case: O(5*log n) === O(log n)
	 */
	@Override
	public void addNewRide(String email, String date)
			throws NotLoggedInException, CantRideSelfException, DateOccupiedException, NonExistentUserException,
			InvalidDateException, NonExistentTripException, TripIsFullException {
		User tripDriver = usersByEmail.get(email);
		BasicDateTime newDate;

		if (currentUser == null) {
			throw new NotLoggedInException();
		} else if (tripDriver == null) {
			throw new NonExistentUserException();
		} else {
			newDate = new BasicDateTimeClass(date);
		}//TODO
		if (!tripDriver.hasTripOnDate(newDate)) { // log n
			throw new NonExistentTripException();
		} else if (email.equals(currentUser.getEmail())) {
			throw new CantRideSelfException(tripDriver);
		} else if (currentUser.hasTripOnDate(newDate) || currentUser.hasRideOnDate(newDate)) { // 2*log n
			throw new DateOccupiedException(currentUser);
		}
		Trip ride = tripDriver.addUserToTrip(currentUser, newDate); // log n
		currentUser.addRide(ride); // 2log n
	}

	/**
	 * O(1)
	 */
	@Override
	public boolean isUserRegistered(String email) {
		return usersByEmail.get(email) != null;
	}

	/**
	 * Best case: O(1)
	 * Average case: O(1+y), y occupation factor
	 * Worst case: O(n) if table deteriorates
	 */
	@Override
	public int registerUser(String email, String name, String password)
			throws InvalidPasswordFormatException, UserAlreadyRegisteredException {
		if (!password.matches(PASSWORD_FORMAT)) {
			throw new InvalidPasswordFormatException();
		} else if (isUserRegistered(email)) {
			throw new UserAlreadyRegisteredException();
		}
		usersByEmail.insert(email, new UserClass(email, name, password));
		return usersByEmail.size();
	}

	/**
	 * O(1)
	 */
	@Override
	public String logoutCurrentUser() throws NotLoggedInException {
		String currentUserName = getCurrentUserName();
		currentUser = null;
		return currentUserName;
	}

	/**
	 * O(1)
	 */
	@Override
	public String getCurrentUserEmail() {
		try {
			return currentUser.getEmail();
		} catch (NullPointerException e) {
			throw new NotLoggedInException(e);
		}
	}

	/**
	 * Best case: O(1)
	 * Average case: O(1+y), y occupation factor
	 * Worst case: O(n) if table deteriorates
	 */
	@Override
	public int userLogin(String email, String password) throws NonExistentUserException, IncorrectPasswordException {
		if (isLoggedIn()) {
			throw new LoggedInException();
		}
		User user = usersByEmail.get(email);
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

	/**
	 * O(1)
	 */
	@Override
	public int getCurrentUserTripNumber() {
		try {
			return currentUser.getNumberTrips();
		} catch (NullPointerException e) {
			throw new NotLoggedInException(e);
		}
	}

	/**
	 * Best case: O(1)
	 * Average case: O(log n)
	 * Worst case: O(n + log n)
	 */
	@Override
	public TripWrapper consult(String email, String date)
			throws NotLoggedInException, NonExistentTripException, NonExistentUserException, InvalidDateException {
		User tripDriver = usersByEmail.get(email);

		if (currentUser == null) {
			throw new NotLoggedInException();
		} else if (tripDriver == null) {
			throw new NonExistentUserException();
		}

		BasicDateTime newDate = new BasicDateTimeClass(date);

		return tripDriver.getTrip(newDate).wrap();
	}

	/**
	 * Best case: O(1)
	 * Average case: O(n+2*log n)
	 * Worst case: O(n+2*log n)
	 */
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


	/**
	 * Best case: O(1)
	 * Average case: O(1+y), y occupation factor
	 * Worst case: O(n) if table deteriorates
	 */
	@Override
	public Iterator<TripWrapper> getUserTrips(String email)
			throws NotLoggedInException, NoRegisteredTripsException, NonExistentUserException {
		if (currentUser == null) {
			throw new NotLoggedInException();
		}

		try {
			return new IteratorWrappable<TripWrapper, Trip>(usersByEmail.get(email).getTripsIterator());
		} catch (NullPointerException e) {
			throw new NonExistentUserException(e);
		}
	}

	/**
	 * O(1)
	 */
	@Override
	public Iterator<TripWrapper> getCurrentUserRides() throws NotLoggedInException, NoRegisteredTripsException {
		if (currentUser == null) {
			throw new NotLoggedInException();
		}
		return new IteratorWrappable<TripWrapper, Trip>(currentUser.getRidesIterator());
	}

	/**
	 * O(1), get is O(1) on this structure
	 */
	@Override
	public Iterator<TripWrapper> getTripsOnDate(String date) throws NotLoggedInException, InvalidDateException {
		if (currentUser == null) {
			throw new NotLoggedInException();
		}
		BasicDateTime newDate = new BasicDateTimeClass(date);
		try {
			return new IteratorWrappable<TripWrapper, Trip>(tripsByDate.get(newDate).values());
		} catch (NoElementException e) {
			throw new InvalidDateException(e);
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * O(1)
	 */
	@Override
	public Iterator<TripWrapper> getAllTrips() throws NotLoggedInException {
		if (currentUser == null) {
			throw new NotLoggedInException();
		}
		return new IteratorWrappable<TripWrapper, Trip>(
				new NestedMapValuesIterator<Trip, SortedMap<String, Trip>>(tripsByDate));
	}

}
