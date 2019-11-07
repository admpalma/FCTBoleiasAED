package fctBoleias.user;

public interface User {

	/**
	 * Gives the {@link User}'s email
	 * @return the email
	 */
	public String getEmail();

	/**
	 * Gives the {@link User}'s name
	 * @return the name
	 */
	public String getName();

	/**
	 * Gives the {@link User}'s password
	 * @return the password
	 */
	public String getPassword();

	/**
	 * Gives the number of system visits from this {@link User} 
	 * @return the nVisits
	 */
	public int getNumberLogins();
	
	/**
	 * Matches the given {@link String} with the {@link User User's} password
	 * @param password given {@link String} to match
	 * @return <code>true</code> if the given {@link String} and the {@link User User's} password match,
	 * <code>false</code> otherwise
	 */
	public boolean checkPassword(String password);

	/**
	 * Registers a new login in the system by the {@link User}
	 */
	public void addLogin();

}
