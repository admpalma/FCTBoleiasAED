package fctBoleias;

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
