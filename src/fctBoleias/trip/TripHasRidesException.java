package fctBoleias.trip;

import fctBoleias.user.User;

/**
 * A {@link TripHasRidesException} is thrown when a {@link User} attempts to
 * cancel a {@link Trip} that already has other {@link User users} taking it as
 * a ride
 */
public class TripHasRidesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TripHasRidesException(User user) {
		super(String.format("%s ja nao pode eliminar esta deslocacao.", user.getName()));
	}

	public TripHasRidesException(String message) {
		super(message);
	}

}
