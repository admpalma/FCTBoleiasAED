package fctBoleias;

import fctBoleias.user.User;

/**
 * A {@link NonExistentUserException} is thrown when there is an attempt to
 * login a {@link User} that doesn't exist
 */
public class NonExistentUserException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NonExistentUserException() {
		super();
	}

	public NonExistentUserException(String message) {
		super(message);
	}

	public NonExistentUserException(Throwable e) {
		super(e);
	}

}
