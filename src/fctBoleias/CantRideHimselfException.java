package fctBoleias;

public class CantRideHimselfException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CantRideHimselfException() {
		super("%s nao pode dar boleia a si proprio.%n");
	}

	public CantRideHimselfException(String message) {
		super(message);
	}

}
