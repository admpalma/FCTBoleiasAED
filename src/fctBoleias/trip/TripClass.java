package fctBoleias.trip;

import java.time.LocalDateTime;

import dataStructures.Array;
import dataStructures.List;
import dataStructures.Map;
import dataStructures.Queue;
import fctBoleias.user.User;

public class TripClass implements Trip {

	// Instance variables containing the ride's details and information
	private String origin, destiny;
	private LocalDateTime date;
	private List<User> usersInRide;
	private int duration;
	private User driver; // Driver
	private Queue<User> usersWaitingRide; // Users waiting for Ride

	/**
	 * Ride object constructor Creates an object holding details and information
	 * about a ride
	 * 
	 * @param origin   - origin of the ride
	 * @param destiny  - destiny of the ride
	 * @param date     - date of the ride
	 * @param capacity - initial number of seats available for other users
	 * @param duration - duration of ride
	 * @param driver   - email of who gives the ride
	 */
	public TripClass(String origin, String destiny, LocalDateTime date, int capacity, int duration, User driver) {
		this.origin = origin;
		this.destiny = destiny;
		this.date = date;
		this.duration = duration;
		this.usersInRide = new Array<User>(capacity);
		this.driver = driver;
	}

}
