package fctBoleias;

import fctBoleias.user.User;

/**
 * An {@link InvalidPasswordFormatException} is thrown when there is an attempt
 * to register a {@link User} with a <code>password</code> that doesn't match
 * the format defined by {@link Manager}
 */
public class InvalidPasswordFormatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPasswordFormatException() {
		super("Registo nao realizado.");
	}

}
