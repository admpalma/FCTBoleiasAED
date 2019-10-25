package fctBoleias;

public class NonExistentTripException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NonExistentTripException() {
		super("Deslocacao nao existe.");
	}

	public NonExistentTripException(String message) {
		super(message);
	}
	
	
	
}
