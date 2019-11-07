package fctBoleias;

public class LoggedInException extends IllegalStateException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LoggedInException() {
		super("Comando inexistente.");
	}
	
	public LoggedInException(String message) {
		super(message);
	}


}
