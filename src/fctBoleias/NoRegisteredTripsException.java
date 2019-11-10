package fctBoleias;

import dataStructures.NoElementException;
import fctBoleias.trip.Trip;

/**
 * A {@link NoRegisteredTripsException} is thrown when there is an attempt to
 * consult information about a collection of {@link Trip trips} but the
 * collection is empty i.e. has no registered {@link Trip trips}
 */
public class NoRegisteredTripsException extends Exception {

	private static final String NO_TRIPS = "Sem deslocacoes.";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoRegisteredTripsException() {
		super(NO_TRIPS);
	}

	public NoRegisteredTripsException(String message) {
		super(message);
	}

	public NoRegisteredTripsException(Throwable e) {
		super(NO_TRIPS, e);
	}

}
