package fctBoleias;

public class NoTripOnDayException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoTripOnDayException() {
		super("%s nesta data nao tem registo de deslocacao.%n");
	}

	public NoTripOnDayException(String message) {
		super(message);
	}

}
