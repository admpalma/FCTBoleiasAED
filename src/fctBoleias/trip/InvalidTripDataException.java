package fctBoleias.trip;

/**
 * An {@link InvalidTripDataException} is thrown when there is 
 * an attempt to create a new {@link Trip} using invalid data
 */
public class InvalidTripDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTripDataException() {
		super("Dados invalidos.");
	}

	public InvalidTripDataException(String message) {
		super(message);
	}

}
