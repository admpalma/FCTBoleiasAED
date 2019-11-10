package fctBoleias;

import java.io.Serializable;

import basicDateTime.BasicDateTime;
import basicDateTime.InvalidDateException;
import fctBoleias.trip.CantRideSelfException;
import fctBoleias.trip.InvalidTripDataException;
import fctBoleias.trip.Trip;
import fctBoleias.trip.TripHasRidesException;
import fctBoleias.user.IncorrectPasswordException;
import fctBoleias.user.User;

public interface Manager extends Serializable {

	/**
	 * Returns true if there's a {@link User} logged in
	 * @return true if there's a user logged in, false otherwise
	 */
	boolean isLoggedIn();

	/**
	 * Registers a new trip on the current user
	 * Assumes there's a {@link User} logged in
	 * @param origin the new {@link Trip Trip's} origin
	 * @param destiny the new {@link Trip Trip's} destiny
	 * @param date the new {@link Trip Trip's} date
	 * @param hourMinute the new {@link Trip Trip's} hour and minutes
	 * @param duration the new {@link Trip Trip's} duration
	 * @param numberSeats the new {@link Trip Trip's} numberSeats
	 * @throws InvalidTripDataException if any of the given data is invalid
	 * @throws DateOccupiedException if current user already has a trip or ride on that date
	 * @throws NotLoggedInException if there's no {@link User} logged in
	 */
	void addTrip(String origin, String destiny, String date, String hourMinute, int duration, int numberSeats) throws InvalidTripDataException, BookedDateException, NotLoggedInException;

	/**
	 * TODO
	 * Gives the currently logged in {@link User}'s name
	 * @return {@link User User's} name or <code>null</code> if there's no {@link User} logged in
	 */
	String getCurrentUserName();

	/**
	 * Removes a {@link Trip trip} from the currentUser on the given date
	 * Assumes there's a {@link User} logged in
	 * @param date the date of the {@link Trip trip} to be removed
	 * @throws NotLoggedInException if no {@link User user} is logged in
	 * @throws InvalidDateException if the given date is invalid
	 * @throws NoTripOnDayException if there's no {@link Trip trip} registered on that date
	 * @throws TripHasRidesException if the {@link Trip trip} already has rides and can't be removed
	 */
	void remove(String date) throws NotLoggedInException, InvalidDateException, NoTripOnDayException, TripHasRidesException;

	/**
	 * Adds a new ride
	 * @param name - name of the ride owner
	 * @param date - date of the ride
	 * @throws NotLoggedInException if no {@link User} is logged in
	 * @throws CantRideSelfException if the {@link Trip} owner is the current {@link User}
	 * @throws DateOccupiedException if user already has a ride or a trip on that date
	 * @throws InexistentUserException if the user doesn't exist
	 * @throws InvalidDateException if the given date is invalid
	 * @throws NonExistentTripException if the trip doesn't exist
	 */
	void addNewRide(String name, String date) throws NotLoggedInException, CantRideSelfException, DateOccupiedException, InexistentUserException, InvalidDateException, NonExistentTripException;

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
	 * @throws UserAlreadyRegisteredException if there is already a {@link User} registered with the given <code>email</code>
	 */
	int registerUser(String email, String name, String password) throws InvalidPasswordFormatException, UserAlreadyRegisteredException;

	/**
	 * Performs the logout of the <code>Current User</code>
	 * @return the name of the {@link User} that logged out
	 * @throws NotLoggedInException if no user is logged in
	 */
	String logoutCurrentUser() throws NotLoggedInException;

	/**
	 * TODO
	 * Gives the currently logged in {@link User}'s email
	 * @return {@link User User's} email or <code>null</code> if there's no {@link User} logged in
	 */
	String getCurrentUserEmail();

	/**
	 * Logs in a {@link User} in the system ({@link Manager})
	 * Assumes there's no {@link User} logged in
	 * @param email {@link User User's} email
	 * @param password {@link User User's} password
	 * @return the ordinal number of this {@link User User's} login in the system
	 * @throws NonExistentUserException if the given <code>email</code> doesn't match any {@link User} in the system
	 * @throws IncorrectPasswordException if the given <code>password</code> doesn't match the {@link User User's} actual password
	 * @throws LoggedInException if there's a {@link User} logged in
	 */
	int userLogin(String email, String password) throws NonExistentUserException, IncorrectPasswordException, LoggedInException;

	/**
	 * TODO
	 * @return {@link User User's} number of {@link Trip Trips} or <code>null</code> if there's no {@link User} logged in
	 */
	int getCurrentUserTripNumber();

	/**
	 * Consults a {@link Trip} from the {@link User} with the given {@link String email} on the given {@link BasicDateTime date}
	 * @param email {@link String email} of the owner of the {@link Trip} to consult
	 * @param date {@link BasicDateTime date} of the {@link Trip} to check
	 * @return TODO
	 * @throws NotLoggedInException if no {@link User} is logged in
	 * @throws NonExistentTripException if the {@link Trip} we want to consult doesn't exist
	 * @throws NonExistentUserException if the {@link User} with the given email doesn't exist
	 * @throws InvalidDateException if the given {@link BasicDateTime date} is invalid
	 */
	Trip consult(String email, String date) throws NotLoggedInException, NonExistentTripException, NonExistentUserException, InvalidDateException;

	/**
	 * Cancels the {@link User current user's} taken ride on a given date
	 * @param date given date
	 * @throws NotLoggedInException if no {@link User} is logged in
	 * @throws InvalidDateException if the given date is invalid
	 * @throws NoRideOnDayException if there's no {@link Trip ride} registered on that date for the {@link User current user}
	 */
	void cancelCurrentUserRide(String date) throws NotLoggedInException, InvalidDateException, NoRideOnDayException;
	
}
