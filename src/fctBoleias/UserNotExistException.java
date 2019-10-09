package fctBoleias;

public class UserNotExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotExistException() {
		super("Utilizador inexistente.");
	}

	public UserNotExistException(String message) {
		super(message);
	}

}
