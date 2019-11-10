package fctBoleias.user;

import java.io.Serializable;

import basicDateTime.BasicDateTime;
import dataStructures.Iterator;
import fctBoleias.DateOccupiedException;
import fctBoleias.NoRegisteredTripsException;
import fctBoleias.NoRideOnDayException;
import fctBoleias.NoTripOnDayException;
import fctBoleias.NonExistentTripException;
import fctBoleias.trip.Trip;
import fctBoleias.trip.TripHasRidesException;
import fctBoleias.trip.TripIsFullException;

public interface User extends Serializable {

	/**
	 * Gives the {@link User}'s email
	 * 
	 * @return the {@link String email}
	 */
	String getEmail();

	/**
	 * Gives the {@link User}'s name
	 * 
	 * @return the {@link String name}
	 */
	String getName();

	/**
	 * Gives the {@link User}'s password
	 * 
	 * @return the {@link String password}
	 */
	String getPassword();

	/**
	 * Gives the number of system visits from this {@link User}
	 * 
	 * @return the nVisits
	 */
	int getNumberLogins();

	/**
	 * Matches the given {@link String} with the {@link User User's} password
	 * 
	 * @param password given {@link String} to match
	 * @return <code>true</code> if the given {@link String} and the {@link User
	 *         User's} password match, <code>false</code> otherwise
	 */
	boolean checkPassword(String password);

	/**
	 * Registers a new login in the system by the {@link User}
	 */
	void addLogin();

	/**
	 * Registers a new trip on this {@link User}
	 * 
	 * @param newTrip {@link Trip} to be registered
	 * @throws DateOccupiedException if this {@link User} already has a trip or ride
	 *                               on the given {@link Trip Trip's} date
	 */
	void addTrip(Trip newTrip) throws DateOccupiedException;

	/**
	 * Gives the number of registered {@link Trip trips} on {@link User this user}
	 * 
	 * @return {@link User User's} number of {@link Trip Trips}
	 */
	int getNumberTrips();

	/**
	 * Removes a {@link Trip trip} from this {@link User} on the given
	 * {@link BasicDateTime date}
	 * 
	 * @param date {@link BasicDateTime Date} of the {@link Trip} to be removed
	 * @throws NoTripOnDayException  if there's no {@link Trip} registers on the
	 *                               given {@link BasicDateTime date}
	 * @throws TripHasRidesException if the {@link Trip} already has rides and can
	 *                               no longer be removed
	 */
	void removeTrip(BasicDateTime date) throws NoTripOnDayException, TripHasRidesException;

	/**
	 * Adds a {@link User} as a ride to this {@link User user's} {@link Trip} on the
	 * given {@link BasicDateTime date}
	 * 
	 * @param user {@link User} to add to add as a ride
	 * @param date {@link BasicDateTime date} of the ride
	 * @return {@link Trip} which the given {@link User} is going to be taking as a
	 *         ride
	 * @throws NonExistentTripException if this {@link User} doesn't have a
	 *                                  {@link Trip} on the given
	 *                                  {@link BasicDateTime date}
	 * @throws TripIsFullException      if the {@link Trip} is full and the
	 *                                  {@link User} is left on the waiting queue
	 */
	Trip addUserToTrip(User user, BasicDateTime date) throws NonExistentTripException, TripIsFullException;

	/**
	 * Adds a {@link Trip} to the set of {@link Trip Trips} this {@link User} is
	 * taking ride on
	 * 
	 * @param ride {@link Trip} this {@link User} is going to take ride on
	 * @throws DateOccupiedException if this {@link User} already has a trip or ride
	 *                               on the given {@link Trip Trip's} date
	 */
	void addRide(Trip ride) throws DateOccupiedException;

	/**
	 * Gets the {@link Trip} from this {@link User} at the given
	 * {@link BasicDateTime date}
	 * 
	 * @param date {@link BasicDateTime date} of the {@link Trip} to get
	 * @return {@link Trip} registered at given {@link BasicDateTime date}
	 * @throws NonExistentTripException if no {@link Trip} is registered at given
	 *                                  {@link BasicDateTime date}
	 */
	Trip getTrip(BasicDateTime date) throws NonExistentTripException;

	/**
	 * Checks if {@link User this user} has a {@link Trip} registered on the given
	 * {@link BasicDateTime date}
	 * 
	 * @param date {@link BasicDateTime date} to check if the {@link User} has a
	 *             {@link Trip} on
	 * @return <code>true</code> if the user has a {@link Trip} on the given date,
	 *         <code>false</code> otherwise
	 */
	boolean hasTripOnDate(BasicDateTime date);

	/**
	 * Gives an {@link Iterator} of all the {@link Trip trips} of this {@link User}
	 * 
	 * @return {@link Iterator} <{@link Trip}>
	 * @throws NoRegisteredTripsException if there are no registered {@link Trip
	 *                                    trips} on this {@link User}
	 */
	Iterator<Trip> getTripsIterator() throws NoRegisteredTripsException;

	/**
	 * Gives an {@link Iterator} of all the {@link Trip rides} of this {@link User}
	 * 
	 * @return {@link Iterator} <{@link Trip}>
	 * @throws NoRegisteredTripsException if there are no registered {@link Trip
	 *                                    rides} on this {@link User}
	 */
	Iterator<Trip> getRidesIterator() throws NoRegisteredTripsException;

	/**
	 * Cancels this {@link User User's} taken ride on a given date
	 * 
	 * @param processedDate given date
	 * @return {@link Trip} which this {@link User} is no longer taking as a ride
	 * @throws NoRideOnDayException if there's no {@link Trip ride} registered on
	 *                              the given date for this {@link User}
	 */
	Trip cancelRide(BasicDateTime processedDate) throws NoRideOnDayException;

	/**
	 * Checks if {@link User this user} has a {@link ride} registered on the given
	 * {@link BasicDateTime date}
	 * 
	 * @param date {@link BasicDateTime date} to check if the {@link User} is taking
	 *             a ride on
	 * @return @return <code>true</code> if the user is taking a ride on the given
	 *         date, <code>false</code> otherwise
	 */
	boolean hasRideOnDate(BasicDateTime date);

}
