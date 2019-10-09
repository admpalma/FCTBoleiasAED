package fctBoleias;

public class TripNotExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TripNotExistsException() {
		super("Deslocacao nao existe.");
	}

	public TripNotExistsException(String message) {
		super(message);
	}
	
	
	
}
