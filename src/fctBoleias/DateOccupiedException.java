package fctBoleias;

import basicDateTime.BasicDateTime;
import fctBoleias.trip.Trip;
import fctBoleias.user.User;

/**
 * A {@link DateOccupiedException} is thrown when there is an attempt to create
 * a new {@link Trip trip} or {@link Trip ride} on a given {@link BasicDateTime
 * date} but the {@link User user} already has a {@link Trip trip} or
 * {@link Trip ride} on that {@link BasicDateTime date}
 */
public class DateOccupiedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DateOccupiedException(User user) {
		super(user.getName());
	}

	public DateOccupiedException(String message) {
		super(message);
	}

}
