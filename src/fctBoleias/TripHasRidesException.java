package fctBoleias;

public class TripHasRidesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TripHasRidesException(User user) {
		super(String.format("%s ja nao pode eliminar esta deslocacao." , user.getName()));
	}

	public TripHasRidesException(String message) {
		super(message);
	}

}
