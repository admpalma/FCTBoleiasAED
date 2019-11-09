package fctBoleias.trip;

import basicDateTime.InvalidDateException;

/**
 * An {@link InvalidTripDataException} is thrown when there is 
 * an attempt to create a new {@link Trip} using invalid data
 */
public class InvalidTripDataException extends Exception {

	private static final String DADOS_INVALIDOS = "Dados invalidos.";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTripDataException() {
		super(DADOS_INVALIDOS);
	}

	public InvalidTripDataException(String message) {
		super(message);
	}

	public InvalidTripDataException(InvalidDateException e) {
		super(DADOS_INVALIDOS, e);
	}

}
