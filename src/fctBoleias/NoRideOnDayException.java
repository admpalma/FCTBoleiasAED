package fctBoleias;

import fctBoleias.user.User;

public class NoRideOnDayException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoRideOnDayException(User user) {
		super(String.format("%s nesta data nao tem registo de boleia.", user.getName()));
	}

}
