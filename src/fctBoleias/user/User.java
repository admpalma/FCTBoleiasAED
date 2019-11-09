package fctBoleias.user;

import java.io.Serializable;

import org.omg.CORBA.UserException;

import basicDateTime.BasicDateTime;
import basicDateTime.BasicDateTimeClass;
import fctBoleias.DateOccupiedException;
import fctBoleias.NoTripOnDayException;
import fctBoleias.NonExistentTripException;
import fctBoleias.trip.Trip;
import fctBoleias.trip.TripHasRidesException;

public interface User extends Serializable {

	/**
	 * Gives the {@link User}'s email
	 * @return the email
	 */
	String getEmail();

	/**
	 * Gives the {@link User}'s name
	 * @return the name
	 */
	String getName();

	/**
	 * Gives the {@link User}'s password
	 * @return the password
	 */
	String getPassword();

	/**
	 * Gives the number of system visits from this {@link User} 
	 * @return the nVisits
	 */
	int getNumberLogins();
	
	/**
	 * Matches the given {@link String} with the {@link User User's} password
	 * @param password given {@link String} to match
	 * @return <code>true</code> if the given {@link String} and the {@link User User's} password match,
	 * <code>false</code> otherwise
	 */
	boolean checkPassword(String password);

	/**
	 * Registers a new login in the system by the {@link User}
	 */
	void addLogin();

	/**
	 * Registers a new trip on this {@link User}
	 * @param newTrip {@link Trip} to be registered
	 */
	void addTrip(Trip newTrip);

	/**
	 * @return {@link User User's} number of {@link Trip Trips}
	 */
	int getNumberTrips();

	/**
	 * Removes a {@link Trip trip} from this {@link User} on the given {@link BasicDateTime date}
	 * @param date {@link BasicDateTime Date} of the {@link Trip} to be removed
	 * @throws NoTripOnDayException if there's no {@link Trip} registers on the given {@link BasicDateTime date}
	 * @throws TripHasRidesException if the {@link Trip} already has rides and can no longer be removed TODO
	 */
	void removeTrip(BasicDateTime date) throws NoTripOnDayException, TripHasRidesException;

	/**
	 * Checks if this {@link User} has a ride on the given {@link BasicDateTime date}
	 * @param date {@link BasicDateTime date} to check if the {@link User} has a ride on
	 * @return true if the user has a ride on the given date, false otherwise
	 */
	boolean hasRideOrTripOnDate(BasicDateTime date);

	/**
	 * Adds a {@link User} as a ride to this {@link User user's} {@link Trip} on the given {@link BasicDateTime date}
	 * @param user {@link User} to add to add as a ride
	 * @param date {@link BasicDateTime date} of the ride 
	 * @return {@link Trip} which the given {@link User} is going to be taking as a ride
	 * @throws NonExistentTripException if this {@link User} doesn't have a {@link Trip} on the given {@link BasicDateTime date}
	 */
	Trip addUserToTrip(User user, BasicDateTime date) throws NonExistentTripException;

	/**
	 * Adds a {@link Trip} to the set of {@link Trip Trips} this {@link User} is taking ride on
	 * @param ride {@link Trip} this {@link User} is going to take ride on
	 * @throws DateOccupiedException if this {@link User} already has a trip or ride on the given {@link Trip Trip's} date
	 */
	void addRide(Trip ride) throws DateOccupiedException;

	/**
	 * Gets the {@link Trip} from this {@link User} at the given {@link BasicDateTime date}
	 * @param date {@link BasicDateTime date} of the {@link Trip} to get
	 * @return {@link Trip} registered at given {@link BasicDateTime date}
	 * @throws NonExistentTripException if no {@link Trip} is registered at given {@link BasicDateTime date}
	 */
	Trip getTrip(BasicDateTime date) throws NonExistentTripException;

}
