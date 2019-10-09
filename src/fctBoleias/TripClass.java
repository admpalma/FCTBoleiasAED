package fctBoleias;

import java.time.LocalDateTime;

import dataStructures.Array;
import dataStructures.List;

public class TripClass implements Trip {

	// Instance variables containing the ride's details and information
	private String origin, destiny;
	private LocalDateTime date;
	private List<User> usersInRide;
	private int duration;
	private User owner; // Owner

	/**
	 * Ride object constructor Creates an object holding details and information
	 * about a ride
	 * 
	 * @param origin   - origin of the ride
	 * @param destiny  - destiny of the ride
	 * @param date     - date of the ride
	 * @param capacity - initial number of seats available for other users
	 * @param duration - duration of ride
	 * @param owner    - email of who gives the ride
	 */
	public TripClass(String origin, String destiny, LocalDateTime date, int capacity, int duration, User owner) {
		this.origin = origin;
		this.destiny = destiny;
		this.date = date;
		this.duration = duration;
		this.usersInRide = new Array<User>(capacity);
		this.owner = owner;
	}

}
