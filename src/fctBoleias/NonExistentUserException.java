package fctBoleias;

public class NonExistentUserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NonExistentUserException() {
		super("Utilizador nao existente.");
	}

	public NonExistentUserException(String message) {
		super(message);
	}

}
