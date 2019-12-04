package fctBoleias.trip;

import basicDateTime.BasicDateTime;
import dataStructures.Wrapper;
import fctBoleias.user.User;

/**
 * A {@link TripWrapper} acts like {@link Wrapper} around a {@link Trip}
 * 
 * @see Wrapper
 */
public interface TripWrapper extends Wrapper {

	/**
	 * Gives the {@link BasicDateTime date} of this {@link Trip}
	 * 
	 * @return a {@link BasicDateTime} holding this {@link Trip Trip's} date
	 */
	BasicDateTime getBasicDateTime();

	/**
	 * Checks if {@link Trip this} has {@link Trip rides} registered
	 * 
	 * @return boolean true if this {@link Trip} has {@link User users} registered
	 *         for rides, false otherwise
	 */
	boolean hasRides();

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
	 * Gives the {@link String destination} of this {@link Trip}
	 * 
	 * @return {@link String destination}
	 */
	String getDestination();

	/**
	 * Gives the duration of this {@link Trip}
	 * 
	 * @return int duration
	 */
	int getDuration();

	/**
	 * @return <code>true</code> if this {@link Trip} has free slots,
	 *         <code>false</code> otherwise
	 */
	boolean hasFreeSlots();

	/**
	 * Returns a {@link String} with the following format:
	 * <p>
	 * {@link #getDriverEmail() driver email}<br>
	 * {@link #getOrigin() origin}-{@link #getDestination() destination}<br>
	 * {@link BasicDateTime#toString() dateTime} {@link #getDuration() duration}<br>
	 * <code>Lugares vagos: </code>{@link #freeSlots() free slots}<br>
	 * <code>"Users in trip emails'"</code><br>
	 * <code>Em espera: "number of users in queue"</code><br>
	 * 
	 * @return {@link String} describing the {@link Trip}
	 */
	@Override
	String toString();

	/**
	 * Returns a {@link String} with the following format:
	 * <p>
	 * {@link #getDriverEmail() driver email}<br>
	 * {@link #getOrigin() origin}-{@link #getDestination() destination}<br>
	 * {@link BasicDateTime#toString() dateTime} {@link #getDuration() duration}<br>
	 * 
	 * @return {@link String} describing the {@link Trip}
	 */
	String toMediumDetailString();

	/**
	 * Returns a {@link String} with the following format:
	 * <p>
	 * {@link BasicDateTime#toString() date} {@link #getDriverEmail() driver
	 * email}<br>
	 * 
	 * @return {@link String} describing the {@link Trip}
	 */
	String toDateAndDriverString();

	/**
	 * @return the number of free slots in this {@link Trip}
	 */
	int freeSlots();

}
