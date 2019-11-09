package fctBoleias;

public class InexistentUserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InexistentUserException() {
		super("Utilizador inexistente.");
	}

}
