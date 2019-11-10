package fctBoleias.trip;

import java.io.Serializable;

import basicDateTime.BasicDateTime;
import fctBoleias.user.User;

public interface Trip extends Serializable {

	/**
	 * Gives the {@link BasicDateTime date} of this {@link Trip}
	 * 
	 * @return a {@link BasicDateTime} holding this {@link Trip Trip's} date
	 */
	BasicDateTime getBasicDateTime();

	/**
	 * Checks if {@link Trip this} has {@link Trip rides} registered
	 * @return boolean true if this {@link Trip} has {@link User users} registered
	 *         for rides, false otherwise
	 */
	boolean hasRides();

	/**
	 * Adds {@link User} to {@link Trip this} as a ride
	 * 
	 * @param user {@link User} to add as a ride
	 * @throws TripIsFullException if the {@link Trip} is full and the {@link User}
	 *                             is left on the waiting queue
	 */
	void addUserAsRide(User user) throws TripIsFullException;

	/**
	 * Gives the {@link String email} of the {@link User driver} of this
	 * {@link Trip}
	 * 
	 * @return {@link String driver}
	 */
	String getDriverEmail();

	/**
	 * Gives the {@link String origin} of this {@link Trip}
	 * 
	 * @return {@link String origin}
	 */
	String getOrigin();

	/**
	 * Gives the {@link String destiny} of this {@link Trip}
	 * 
	 * @return {@link String destiny}
	 */
	String getDestiny();

	/**
	 * Gives the duration of this {@link Trip}
	 * 
	 * @return int duration
	 */
	int getDuration();

	/**
	 * Remove the given {@link User} from the gruop of {@link User Users} taking
	 * ride on this {@link Trip}
	 * 
	 * @param currentUser the given {@link User}
	 */
	void removeUserRide(User user);

	/**
	 * @return <code>true</code> if this {@link Trip} has free slots, <code>false</code> otherwise
	 */
	boolean hasFreeSlots();

}
