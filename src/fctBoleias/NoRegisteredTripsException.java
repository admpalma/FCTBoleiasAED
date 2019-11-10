package fctBoleias;

public class NoRegisteredTripsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoRegisteredTripsException() {
		super("Sem deslocacoes.");
	}

	public NoRegisteredTripsException(String message) {
		super(message);
	}

}
