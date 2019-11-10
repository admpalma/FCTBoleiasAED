package fctBoleias;

import basicDateTime.BasicDateTime;
import fctBoleias.trip.Trip;
import fctBoleias.user.User;

/**
 * A {@link NoRideOnDayException} is thrown when there is an attempt to cancel a
 * {@link Trip ride} on a certain {@link BasicDateTime date} but the {@link Trip
 * ride} doesn't exist (no {@link Trip ride} registered on that
 * {@link BasicDateTime date})
 */
public class NoRideOnDayException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoRideOnDayException(User user) {
		super(String.format("%s nesta data nao tem registo de boleia.", user.getName()));
	}

	public NoRideOnDayException(String message) {
		super(message);
	}

}
