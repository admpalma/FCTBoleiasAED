package fctBoleias;

import basicDateTime.InvalidDateException;
import fctBoleias.trip.CantRideSelfException;
import fctBoleias.trip.InvalidDataException;
import fctBoleias.trip.TripHasRidesException;
import fctBoleias.user.User;

public interface Manager {

	/**
	 * Returns true if there's a {@link User} logged in
	 * @return true if there's a user logged in, false otherwise
	 */
	boolean isLoggedIn();

	/**
	 * Registers a new trip on the current user
	 * @param origin
	 * @param destiny
	 * @param date
	 * @param hourMinute
	 * @param duration
	 * @param numberSeats
	 * @throws NotLoggedInException if no user is logged in
	 * @throws InvalidDataException if any of the given data is invalid
	 * @throws DateOccupiedException if current user already has a trip or ride on that date
	 */
	void addNewTrip(String origin, String destiny, String date, String hourMinute, int duration, int numberSeats) throws NotLoggedInException, InvalidDataException, DateOccupiedException;

	/**
	 * Gives the currently logged in {@link User}'s name
	 * @return String name
	 * @throws NotLoggedInException if no user is logged in
	 */
	String getCurrentUserName() throws NotLoggedInException;

	/**
	 * Removes a ride
	 * @param date - date of the ride
	 * @throws NotLoggedInException if no user is logged in
	 * @throws InvalidDateException if the given date is invalid
	 * @throws NoTripOnDayException if there's no trip registered on that day
	 * @throws TripHasRidesException if the trip already has rides
	 */
	void remove(String date) throws NotLoggedInException, InvalidDateException, NoTripOnDayException, TripHasRidesException;

	/**
	 * Adds a new ride
	 * @param name - name of the ride owner
	 * @param date - date of the ride
	 * @throws NotLoggedInException if no user is logged in
	 * @throws CantRideSelfException if the ride owner is the current User
	 * @throws DateOccupiedException if user already has a ride or a trip on that date
	 * @throws NonExistentUserException if the user doesn't exist
	 * @throws InvalidDateException if the given date is invalid
	 * @throws NonExistentTripException if the trip doesn't exist
	 */
	void addNewRide(String name, String date) throws NotLoggedInException, CantRideSelfException, DateOccupiedException, NonExistentUserException, InvalidDateException, NonExistentTripException;

	/**
	 * Checks if there is a {@link User} registered with the given <code>email</code>
	 * @param email email to check a {@link User} with
	 * @return <code>true</code> if there is a {@link User} registered with the given <code>email</code>,
	 * <code>false</code> otherwise
	 */
	boolean isUserRegistered(String email);

	/**
	 * Registers a new {@link User} in the system using the given information
	 * @param email new {@link User User's} email
	 * @param name new {@link User User's} name
	 * @param password new {@link User User's} password
	 * @return the number of this registration
	 * @throws InvalidPasswordFormatException if the password's format is wrong
	 */
	int registerUser(String email, String name, String password) throws InvalidPasswordFormatException;
	
}
