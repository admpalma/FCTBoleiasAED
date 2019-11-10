package fctBoleias.user;

import basicDateTime.BasicDateTime;
import dataStructures.SortedMap;
import dataStructures.SortedMapWithJavaClass;
import fctBoleias.BookedDateException;
import fctBoleias.DateOccupiedException;
import fctBoleias.NoTripOnDayException;
import fctBoleias.NonExistentTripException;
import fctBoleias.trip.Trip;
import fctBoleias.trip.TripHasRidesException;

public class UserClass implements User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Instance variables containing the users's details and information
	private String email;
	private String name;
	private String password;
	// private Map<LocalDateTime, List<Trip>> tripsByDate; // User's trips by date
	private int nLogins;
	private SortedMap<BasicDateTime, Trip> rides; // BOLEIAS EM QUE PARTICIPA MAS ¬OWNER
	private SortedMap<BasicDateTime, Trip> trips; // USER'S (this) TRIPS

	/**
	 * User object constructor Creates an object holding details and information
	 * about a user
	 * 
	 * @param email    - email of the user
	 * @param name     - name of the user
	 * @param password - password of the user
	 */
	public UserClass(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.nLogins = 0;
		trips = new SortedMapWithJavaClass<BasicDateTime, Trip>();
		rides = new SortedMapWithJavaClass<BasicDateTime, Trip>();
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the nVisits
	 */
	public int getNumberLogins() {
		return nLogins;
	}

	@Override
	public boolean checkPassword(String password) {
		return this.password.matches(password);
	}

	@Override
	public void addLogin() {
		nLogins++;
	}

	@Override
	public void addTrip(Trip newTrip) throws BookedDateException {
		if (hasRideOrTripOnDate(newTrip.getBasicDateTime())) {
			throw new BookedDateException(this);
		}
		trips.insert(newTrip.getBasicDateTime(), newTrip);
	}

	@Override
	public int getNumberTrips() {
		return trips.size();
	}

	@Override
	public void removeTrip(BasicDateTime date) throws NoTripOnDayException, TripHasRidesException {
		Trip trip = trips.find(date);
		if (trip == null) { // If we don't find the trip (doesn't exist)
			throw new NoTripOnDayException(this);
		} else if (trip.hasRides()) {
			throw new TripHasRidesException(this);
		}
		trips.remove(date);
	}

	@Override
	public boolean hasRideOrTripOnDate(BasicDateTime date) {
		return rides.find(date) != null || hasTripOnDate(date);
	}

	@Override
	public Trip addUserToTrip(User user, BasicDateTime date) throws NonExistentTripException {
		Trip trip = trips.find(date);
		if (trip == null) {
			throw new NonExistentTripException();
		}
		trip.addUserAsRide(user);
		return trip;
	}

	@Override
	public void addRide(Trip ride) throws DateOccupiedException {
		BasicDateTime rideDate = ride.getBasicDateTime();
		if (rides.find(rideDate) != null) {
			throw new DateOccupiedException(this);
		}
		rides.insert(rideDate, ride);
	}

	@Override
	public Trip getTrip(BasicDateTime date) throws NonExistentTripException {
		Trip trip = trips.find(date);
		if (trip == null) {
			throw new NonExistentTripException();
		}
		return trip;
	}

	@Override
	public boolean hasTripOnDate(BasicDateTime date) {
		return trips.find(date) != null;
	}

}
