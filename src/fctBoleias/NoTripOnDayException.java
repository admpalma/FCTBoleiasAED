package fctBoleias;

import basicDateTime.BasicDateTime;
import fctBoleias.trip.Trip;
import fctBoleias.user.User;

/**
 * A {@link NoTripOnDayException} is thrown when a {@link User}
 * attempts to cancel a {@link Trip} on a given {@link BasicDateTime date} 
 * but has no {@link Trip} registered on that {@link BasicDateTime date}
 */
public class NoTripOnDayException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoTripOnDayException(User user) {
		super(String.format("%s nesta data nao tem registo de deslocacao.", user.getName()));
	}

	public NoTripOnDayException(String message) {
		super(message);
	}

}
