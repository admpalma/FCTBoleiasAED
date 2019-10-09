package fctBoleias;

import java.time.LocalDateTime;

import dataStructures.*;

public class ManagerClass implements Manager {

	private User currentUser;
	private Map<String, User> usersByEmail; // Key: user email
	private Map<LocalDateTime, List<Trip>> ridesByDate; // Rides by date
	private Map<Trip, Queue<User>> usersWaitingRide; // Users waiting for Ride
	private List<Trip> allTrips;

	public ManagerClass() {
		this.currentUser = null;
	}

	@Override
	public boolean isLoggedIn() {
		return currentUser != null;
	}

	@Override
	public void addNewTrip(String origin, String destiny, String date, String hourMinute, int duration, int numberSeats)
			throws NotLoggedInException, InvalidDataException, DateOccupiedException {
		if (currentUser == null) {
			throw new NotLoggedInException();
		}

	}

	@Override
	public String getCurrentUserName() throws NotLoggedInException {
		if (currentUser == null) {
			throw new NotLoggedInException();
		}
		// TODO
		return currentUser.getName();
	}

	@Override
	public void remove(String date) throws NotLoggedInException, NoTripOnDayException, TripHasRidesException {
		// TODO Auto-generated method stub
		if (currentUser == null) {
			throw new NotLoggedInException();
		}
		// TODO THROW INVALID DATE EXCEPTION WITH PROPER MESSAGE
	}

	@Override
	public void addNewRide(String name, String date) throws NotLoggedInException, CantRideHimselfException, DateOccupiedException,
			UserNotExistException, InvalidDateException, TripNotExistsException {
		// TODO Auto-generated method stub
		if (currentUser == null) {
			throw new NotLoggedInException();
		}
		// TODO THROW INVALID DATE EXCEPTION WITH PROPER MESSAGE
	}

}
