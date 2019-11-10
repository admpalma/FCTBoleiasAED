package fctBoleias;

import fctBoleias.user.User;

/**
 * TODO A {@link NonExistentUserException} is thrown when there is an attempt to
 * login a {@link User} but the {@link User} doesn't exist
 */
public class NonExistentUserException extends Exception {

	private static final String USER_NON_EXISTENT = "Utilizador nao existente.";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NonExistentUserException() {
		super(USER_NON_EXISTENT);
	}

	public NonExistentUserException(String message) {
		super(message);
	}

	public NonExistentUserException(Throwable e) {
		super(USER_NON_EXISTENT, e);
	}

}
