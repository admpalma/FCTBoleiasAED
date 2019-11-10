package fctBoleias;

import fctBoleias.user.User;

public class BookedDateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BookedDateException(User user) {
		super(String.format("%s ja tem uma deslocacao ou boleia registada nesta data.", user.getName()));
	}

}
