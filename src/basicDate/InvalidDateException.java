package basicDate;

/**
 * An {@link InvalidDateException} is thrown when there is
 * an attempt to create a new {@link BasicDate} using impossible
 * specifications (e.g.: 00-01-2010)
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
