package fctBoleias;

import basicDateTime.BasicDateTime;
import fctBoleias.trip.Trip;
import fctBoleias.user.User;

/**
 * A {@link BookedDateException} is thrown when there is an attempt to create a
 * new {@link Trip trip} or {@link Trip ride} on a given {@link BasicDateTime
 * date} but the {@link User user} already has a {@link Trip trip} or
 * {@link Trip ride} on that {@link BasicDateTime date}
 */
public class BookedDateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookedDateException(User user) {
		super(String.format("%s ja tem uma deslocacao ou boleia registada nesta data.", user.getName()));
	}

	public BookedDateException(String message) {
		super(message);
	}

}
