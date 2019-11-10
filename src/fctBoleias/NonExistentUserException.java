package fctBoleias;

import fctBoleias.user.User;

/**
 * TODO A {@link NonExistentUserException} is thrown when there is an attempt to
 * login a {@link User} but the {@link User} doesn't exist
 */
public class NonExistentUserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NonExistentUserException() {
		super("Utilizador nao existente.");
	}

	public NonExistentUserException(String message) {
		super(message);
	}

}
