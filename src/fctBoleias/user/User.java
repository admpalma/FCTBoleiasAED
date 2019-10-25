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
	public int getnVisits();

}
