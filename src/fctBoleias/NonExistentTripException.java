package fctBoleias;

import fctBoleias.trip.Trip;

/**
 * A {@link NoExistentTripException} is thrown when there is an attempt to
 * consult information about a {@link Trip trip} or take a {@link Trip ride} but
 * the {@link Trip} doesn't exist
 */
public class NonExistentTripException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NonExistentTripException() {
		super("Deslocacao nao existe.");
	}

	public NonExistentTripException(String message) {
		super(message);
	}

}
