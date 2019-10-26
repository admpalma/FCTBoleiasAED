package basicDateTime;

/**
 * An {@link InvalidDateException} is thrown when there is
 * an attempt to create a new {@link BasicDateTime} using impossible
 * values (e.g.: 00-01-2010)
 */
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
