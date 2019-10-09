package fctBoleias;

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
	 * @throws CantRideHimselfException if the ride owner is the current User
	 * @throws DateOccupiedException if user already has a ride or a trip on that date
	 * @throws UserNotExistException if the user doesn't exist
	 * @throws InvalidDateException if the given date is invalid
	 * @throws TripNotExistsException if the trip doesn't exist
	 */
	void addNewRide(String name, String date) throws NotLoggedInException, CantRideHimselfException, DateOccupiedException, UserNotExistException, InvalidDateException, TripNotExistsException;
	
}
