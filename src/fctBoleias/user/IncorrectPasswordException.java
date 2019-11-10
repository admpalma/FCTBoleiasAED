package fctBoleias.user;

/**
 * An {@link incorrectPasswordException} is thrown when there is an attempt to
 * login as a {@link User} but the <code>password</code> is incorrect
 */
public class IncorrectPasswordException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IncorrectPasswordException() {
		super("Entrada nao realizada.");
	}

}
