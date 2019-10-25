package fctBoleias.trip;

/**
 * An {@link InvalidDataException} is thrown when there is 
 * an attempt to create a new {@link Trip} using invalid data
 */
public class InvalidDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidDataException() {
		super("Dados invalidos.");
	}

	public InvalidDataException(String message) {
		super(message);
	}

}
