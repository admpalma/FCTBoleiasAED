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
	private String origin, destination;
	private BasicDateTime date;
	private List<User> usersInRide;
	private int duration, capacity;
	private User driver; // Driver
	private Queue<User> usersWaitingRide; // Users waiting for Ride

	/**
	 * Ride object constructor. Creates an object holding details and information
	 * about a ride
	 * 
	 * @param origin      origin of the ride
	 * @param destination destiny of the ride
	 * @param date        {@link BasicDateTime date} of the ride
	 * @param capacity    initial number of seats available for other users
	 * @param duration    duration of ride
	 * @param driver      {@link User} who gives the ride
	 */
	public TripClass(String origin, String destination, BasicDateTime date, int capacity, int duration, User driver) {
		this.origin = origin;
		this.destination = destination;
		this.date = date;
		this.duration = duration;
		this.usersInRide = new Array<User>(capacity);
		this.usersWaitingRide = new QueueInList<User>();
		this.driver = driver;
		this.capacity = capacity;
	}

	/**
	 * O(1)
	 */
	@Override
	public BasicDateTime getBasicDateTime() {
		return date;
	}

	/**
	 * O(1)
	 */
	@Override
	public String getOrigin() {
		return origin;
	}

	/**
	 * O(1)
	 */
	@Override
	public String getDestination() {
		return destination;
	}

	/**
	 * O(1)
	 */
	@Override
	public int getDuration() {
		return duration;
	}

	/**
	 * O(1)
	 */
	@Override
	public boolean hasRides() {
		return usersInRide.size() > 0;
	}

	/**
	 * O(1) all cases
	 */
	@Override
	public void addUserAsRide(User user) throws TripIsFullException {
		if (usersInRide.size() == capacity) {
			usersWaitingRide.enqueue(user);
			throw new TripIsFullException(usersWaitingRide.size());
		} else {
			usersInRide.addLast(user);
		}
	}

	/**
	 * Best case: O(1)
	 * Average case: O(n), n = size of usersWaitingRide
	 * Worst case: O(n), n = size of usersWaitingRide
	 */
	@Override
	public String toString() {
		String simpleStr = this.toSimpleString();
		String usersInRideStr = getUsersInRideList();
		StringBuilder sb = new StringBuilder(20 + simpleStr.length() + usersInRideStr.length());
		String newLine = System.lineSeparator();
		sb.append(simpleStr);
		sb.append("Lugares vagos: ").append(freeSlots()).append(newLine);
		sb.append(usersInRideStr).append(newLine);
		sb.append("Em espera: ").append(usersWaitingRide.size()).append(newLine);
		return sb.toString();
	}

	/**
	 * O(n), n = number of users in usersWaitingRide
	 * Reads the {@link User users} from usersInRide and concatenates their email in
	 * a {@link String} with the desired format
	 * 
	 * @return {@link String} with the {@link User user}'s in usersInRide emails
	 *         concatenated in a {@link String}
	 */
	private String getUsersInRideList() {
		try {
			Iterator<User> iter = usersInRide.iterator();
			StringBuilder result = new StringBuilder(35 * usersInRide.size());
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

	/**
	 * O(1) all cases
	 */
	@Override
	public String getDriverEmail() {
		return driver.getEmail();
	}

	/**
	 * Best case: O(1) if found in root
	 * Average case: O(log n)
	 * Worst case: O( 2 * log n) if we check both conditions
	 * Checks if there are free slots on this {@link Trip} and fills them with
	 * {@link User Users} in queue, if there are any
	 */
	private void updateQueue() {
		try {
			if (hasFreeSlots() && !usersWaitingRide.isEmpty()) {
				User user = usersWaitingRide.dequeue();
				if (user.hasRideOnDate(this.date) || user.hasTripOnDate(this.date)) {
					updateQueue();
				} else {
					addUserAsRide(user);
				}
			}
		} catch (TripIsFullException e) {
			throw new AssertionError("This code should be unreachable!");
		}
	}

	/**
	 * Best case: O(1) if found on first position
	 * Average case: O(n+log n)
	 * Worst case: O(n+log n)
	 */
	@Override
	public void removeUserRide(User user) {
		usersInRide.remove(usersInRide.find(user));
		updateQueue();
	}

	/**
	 * O(1) all cases
	 */
	@Override
	public boolean hasFreeSlots() {
		return freeSlots() > 0;
	}

	/**
	 * O(1) all cases
	 */
	@Override
	public int freeSlots() {
		return capacity - usersInRide.size();
	}

	/**
	 * O(1) all cases
	 */
	@Override
	public TripWrapper wrap() {
		return new TripWrapperClass(this);
	}

	/**
	 * O(1) all cases
	 */
	@Override
	public String toMediumDetailString() {
		return this.toSimpleString();
	}

	/**
	 * O(1) all cases
	 * Returns a {@link String} with the following format:
	 * <p>
	 * {@link #getDriverEmail() driver email}<br>
	 * {@link #getOrigin() origin}-{@link #getDestination() destination}<br>
	 * {@link BasicDateTime#toString() dateTime} {@link #getDuration() duration}<br>
	 * 
	 * @return {@link String} with a simple description of this {@link Trip}
	 */
	private String toSimpleString() {
		StringBuilder sb = new StringBuilder(120);
		String newLine = System.lineSeparator();
		sb.append(driver.getEmail()).append(newLine);
		sb.append(origin).append("-").append(destination).append(newLine);
		sb.append(date.toString()).append(" ").append(duration).append(newLine);
		return sb.toString();
	}

	/**
	 * O(1) all cases
	 */
	@Override
	public String toDateAndDriverString() {
		StringBuilder sb = new StringBuilder(40);
		String newLine = System.lineSeparator();
		sb.append(date.toDateString()).append(" ").append(driver.getEmail()).append(newLine);
		return sb.toString();
	}

}
