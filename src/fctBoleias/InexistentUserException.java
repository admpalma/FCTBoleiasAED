package fctBoleias;

import fctBoleias.trip.Trip;
import fctBoleias.user.User;

/**
 * An {@link InexistentUserException} is thrown when there is an attempt to
 * consult information about a {@link User}'s {@link Trip trips} or take a
 * {@link Trip ride} but the {@link User} doesn't exist
 */
public class InexistentUserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InexistentUserException() {
		super("Utilizador inexistente.");
	}

}
