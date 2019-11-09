package fctBoleias;

public class NotLoggedInException extends IllegalStateException {

	private static final String COMANDO_INEXISTENTE = "Comando inexistente.";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotLoggedInException() {
		super(COMANDO_INEXISTENTE);
	}
	
	public NotLoggedInException(Throwable e) {
		super(COMANDO_INEXISTENTE, e);
	}


	public NotLoggedInException(String message) {
		super(message);
	}


}
