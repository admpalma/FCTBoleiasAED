package fctBoleias.user;

import fctBoleias.trip.Trip;

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

	/**
	 * Registers a new trip on this {@link User}
	 * @param newTrip {@link Trip} to be registered
	 */
	public void addTrip(Trip newTrip);

	/**
	 * @return {@link User User's} number of {@link Trip Trips}
	 */
	public int getNumberTrips();

}
