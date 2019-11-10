package fctBoleias;

import fctBoleias.user.User;

/**
 * A {@link NotLoggedInException} is thrown when a command is run in not logged
 * in mode that is only supposed to be ran in logged in mode When there's not
 * {@link User} logged in
 */
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
