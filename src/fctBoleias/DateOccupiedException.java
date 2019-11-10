package fctBoleias;

import fctBoleias.user.User;

public class DateOccupiedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DateOccupiedException(User user) {
		super(String.format("%s ja registou uma boleia ou deslocacao nesta data.", user.getName()));

		
	}

	public DateOccupiedException(String message) {
		super(message);
	}

}
