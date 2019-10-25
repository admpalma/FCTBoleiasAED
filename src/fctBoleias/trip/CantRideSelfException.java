package fctBoleias.trip;

import fctBoleias.user.User;

/**
 * A {@link CantRideSelfException} is thrown when a {@link User}
 * who is also the driver of a {@link Trip} attempts to 
 * register as someone taking a ride on such {@link Trip}
 */
public class CantRideSelfException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CantRideSelfException() {
		super("%s nao pode dar boleia a si proprio.%n");
	}

	public CantRideSelfException(String message) {
		super(message);
	}

}
