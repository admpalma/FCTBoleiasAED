package fctBoleias.user;

import basicDateTime.BasicDateTime;
import dataStructures.AVL;
import dataStructures.Iterator;
import dataStructures.NoElementException;
import dataStructures.SortedMap;
import fctBoleias.DateOccupiedException;
import fctBoleias.NoRegisteredTripsException;
import fctBoleias.NoRideOnDayException;
import fctBoleias.NoTripOnDayException;
import fctBoleias.NonExistentTripException;
import fctBoleias.trip.Trip;
import fctBoleias.trip.TripHasRidesException;
import fctBoleias.trip.TripIsFullException;

public class UserClass implements User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Instance variables containing the users's details and information
	private String email;
	private String name;
	private String password;
	private int nLogins;
	private SortedMap<BasicDateTime, Trip> rides; // Rides in which he takes part but isn't the driver
	private SortedMap<BasicDateTime, Trip> trips; // User's trips (this User is the 'driver')

	/**
	 * {@link User} object constructor. Creates an object holding details and
	 * information about a {@link User}
	 * 
	 * @param email    {@link String email} of the user
	 * @param name     {@link String name} of the user
	 * @param password {@link String password} of the user
	 */
	public UserClass(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.nLogins = 0;
		trips = new AVL<BasicDateTime, Trip>();
		rides = new AVL<BasicDateTime, Trip>();
	}
	
	/**
	 * O(1)
	 */
	@Override
	public String getEmail() {
		return email;
	}

	/**
	 * O(1)
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * O(1)
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * O(1)
	 */
	@Override
	public int getNumberLogins() {
		return nLogins;
	}

	/**
	 * O(1)
	 */
	@Override
	public boolean checkPassword(String password) {
		return this.password.matches(password);
	}

	/**
	 * O(1)
	 */
	@Override
	public void addLogin() {
		nLogins++;
	}

	/**
	 * Best case: O(1) if it's in the root
	 * Average case: O(log n)
	 * Worst case: O(log n)
	 */
	@Override
	public void addTrip(Trip newTrip) throws DateOccupiedException {
		BasicDateTime date = newTrip.getBasicDateTime();
		if (hasRideOnDate(date) || hasTripOnDate(date)) {
			throw new DateOccupiedException(this);
		}
		trips.insert(date, newTrip);
	}

	/**
	 * O(1)
	 */
	@Override
	public int getNumberTrips() {
		return trips.size();
	}

	/**
	 * Best case: O(1) if it's in the root
	 * Average case: O(2*log n)
	 * Worst case: O(2 * log n)
	 */
	@Override
	public void removeTrip(BasicDateTime date) throws NoTripOnDayException, TripHasRidesException {
		Trip trip = trips.get(date);
		if (trip == null) { // If we don't find the trip (doesn't exist)
			throw new NoTripOnDayException(this);
		} else if (trip.hasRides()) {
			throw new TripHasRidesException(this);
		}
		trips.remove(date);
	}

	/**
	 * Best case: O(1) if it's in the root
	 * Average case: O(log n)
	 * Worst case: O(log n)
	 */
	@Override
	public Trip addUserToTrip(User user, BasicDateTime date) throws NonExistentTripException, TripIsFullException {
		Trip trip = trips.get(date);
		if (trip == null) {
			throw new NonExistentTripException();
		}
		trip.addUserAsRide(user);
		return trip;
	}

	/**
	 * Best case: O(1) if it's in the root
	 * Average case: O(2 * log n)
	 * Worst case: O(2 * log n)
	 */
	@Override
	public void addRide(Trip ride) throws DateOccupiedException {
		BasicDateTime rideDate = ride.getBasicDateTime();
		if (rides.get(rideDate) != null) {
			throw new DateOccupiedException(this);
		}
		rides.insert(rideDate, ride);
	}

	/**
	 * Best case: O(1) if it's in the root
	 * Average case: O(log n)
	 * Worst case: O(log n)
	 */
	@Override
	public Trip getTrip(BasicDateTime date) throws NonExistentTripException {
		Trip trip = trips.get(date);
		if (trip == null) {
			throw new NonExistentTripException();
		}
		return trip;
	}

	/**
	 * Best case: O(1) if it's in the root
	 * Average case: O(log n)
	 * Worst case: O(log n)
	 */
	@Override
	public boolean hasTripOnDate(BasicDateTime date) {
		return trips.get(date) != null;
	}

	/**
	 * O(1)
	 */
	@Override
	public Iterator<Trip> getTripsIterator() throws NoRegisteredTripsException {
		try {
			return trips.values();
		} catch (NoElementException e) {
			throw new NoRegisteredTripsException(e);
		}
	}

	/**
	 * O(1)
	 */
	@Override
	public Iterator<Trip> getRidesIterator() throws NoRegisteredTripsException {
		try {
			return rides.values();
		} catch (NoElementException e) {
			throw new NoRegisteredTripsException(e);
		}
	}

	/**
	 * Best case: O(1) if it's in the root
	 * Average case: O(log n)
	 * Worst case: O(log n)
	 */
	@Override
	public Trip cancelRide(BasicDateTime processedDate) throws NoRideOnDayException {
		if (!hasRideOnDate(processedDate)) {
			throw new NoRideOnDayException(this);
		}
		return rides.remove(processedDate);
	}

	/**
	 * Best case: O(1) if it's in the root
	 * Average case: O(log n)
	 * Worst case: O(log n)
	 */
	@Override
	public boolean hasRideOnDate(BasicDateTime date) {
		return rides.get(date) != null;
	}

}
