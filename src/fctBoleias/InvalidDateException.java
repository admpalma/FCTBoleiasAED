package fctBoleias;

public class InvalidDateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidDateException() {
		super("Data invalida.");
	}

	public InvalidDateException(String message) {
		super(message);
	}

}
