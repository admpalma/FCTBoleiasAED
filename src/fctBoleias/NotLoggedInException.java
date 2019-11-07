package fctBoleias;

public class NotLoggedInException extends IllegalStateException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotLoggedInException() {
		super("Comando inexistente.");
	}

	public NotLoggedInException(String message) {
		super(message);
	}


}
