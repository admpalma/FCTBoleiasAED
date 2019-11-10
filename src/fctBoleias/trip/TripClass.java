package fctBoleias.trip;

import basicDateTime.BasicDateTime;
import dataStructures.Array;
import dataStructures.Iterator;
import dataStructures.List;
import dataStructures.NoElementException;
import dataStructures.Queue;
import dataStructures.QueueInList;
import fctBoleias.user.User;

public class TripClass implements Trip {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Instance variables containing the ride's details and information
	private String origin, destiny;
	private BasicDateTime date;
	private List<User> usersInRide;
	private int duration, capacity;
	private User driver; // Driver
	private Queue<User> usersWaitingRide; // Users waiting for Ride

	/**
	 * Ride object constructor Creates an object holding details and information
	 * about a ride TODO
	 * 
	 * @param origin   - origin of the ride
	 * @param destiny  - destiny of the ride
	 * @param date     - date of the ride
	 * @param capacity - initial number of seats available for other users
	 * @param duration - duration of ride
	 * @param driver   - email of who gives the ride
	 */
	public TripClass(String origin, String destiny, BasicDateTime date, int capacity, int duration, User driver) {
		this.origin = origin;
		this.destiny = destiny;
		this.date = date;
		this.duration = duration;
		this.usersInRide = new Array<User>(capacity);
		this.usersWaitingRide = new QueueInList<User>();
		this.driver = driver;
		this.capacity = capacity;
	}

	@Override
	public BasicDateTime getBasicDateTime() {
		return date;
	}

	@Override
	public String getOrigin() {
		return origin;
	}

	@Override
	public String getDestiny() {
		return destiny;
	}

	@Override
	public int getDuration() {
		return duration;
	}

	@Override
	public boolean hasRides() {
		return usersInRide.size() > 0;
	}

	@Override
	public void addUserAsRide(User user) throws TripIsFullException {
		if (usersInRide.size() == capacity) {
			usersWaitingRide.enqueue(user);
			throw new TripIsFullException(usersWaitingRide.size());
		} else {
			usersInRide.addLast(user);
		}
	}

	@Override
	public String toString() {
		return String.format("%s%n%s-%s%n%s %d%nLugares vagos: %d%n%s%nEm espera: %d%n", driver.getEmail(), origin,
				destiny, date.toString(), duration, capacity - usersInRide.size(), getUsersInRideList(),
				usersWaitingRide.size());
	}

	private String getUsersInRideList() {
		try {
			Iterator<User> iter = usersInRide.iterator();
			StringBuilder result = new StringBuilder(2 * usersInRide.size());
			result.append("Boleias: ");
			String toAdd = "; ";
			while (iter.hasNext()) {
				result.append(iter.next().getEmail());
				if (iter.hasNext()) {
					result.append(toAdd);
				}
			}
			return result.toString();
		} catch (NoElementException e) {
			return "Sem boleias registadas.";
		}
	}

	@Override
	public String getDriverEmail() {
		return driver.getEmail();
	}

	/**
	 * Checks if there are free slots on this {@link Trip} and fills them with
	 * {@link User Users} in queue, if there are any
	 */
	private void updateQueue() {
		if (hasFreeSlots() && !usersWaitingRide.isEmpty()) {
			try {
				addUserAsRide(usersWaitingRide.dequeue());
			} catch (TripIsFullException e) {
				throw new AssertionError("This code should be unreachable!");
			}
		}
	}

	@Override
	public void removeUserRide(User user) {
		usersInRide.remove(usersInRide.find(user));
		updateQueue();
	}

	@Override
	public boolean hasFreeSlots() {
		return capacity - usersInRide.size() > 0;
	}

}
