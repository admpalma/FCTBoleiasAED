package fctBoleias;

public class TripHasRidesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TripHasRidesException() {
		super("%s ja nao pode eliminar esta deslocacao.%n");
	}

	public TripHasRidesException(String message) {
		super(message);
	}

}
