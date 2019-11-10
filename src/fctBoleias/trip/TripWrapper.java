package fctBoleias.trip;

import basicDateTime.BasicDateTime;
import fctBoleias.Wrapper;
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
	 * @return <code>true</code> if this {@link Trip} has free slots,
	 *         <code>false</code> otherwise
	 */
	boolean hasFreeSlots();

	String toString();

}
