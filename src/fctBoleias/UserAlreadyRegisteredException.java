package fctBoleias;

import fctBoleias.user.User;

/**
 * A {@link UserAlreadyRegisteredException} is thrown when there is an attempt to
 * register a {@link User} with an <code>email</code> that has already been used
 */
public class UserAlreadyRegisteredException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserAlreadyRegisteredException() {
		super();
	}

}
