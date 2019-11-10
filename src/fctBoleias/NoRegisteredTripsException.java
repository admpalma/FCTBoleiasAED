package fctBoleias;

import fctBoleias.trip.Trip;

/**
 * A {@link NoRegisteredTripsException} is thrown when there is an attempt to
 * consult information about a collection of {@link Trip trips} but the
 * collection is empty i.e. has no registered {@link Trip trips}
 */
public class NoRegisteredTripsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoRegisteredTripsException() {
		super("Sem deslocacoes.");
	}

	public NoRegisteredTripsException(String message) {
		super(message);
	}

}
