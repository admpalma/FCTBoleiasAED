package fctBoleias.trip;

import basicDateTime.BasicDateTime;
import dataStructures.Wrapper;

/**
 * Implements a {@link Wrapper} for a {@link Trip}
 */
public class TripWrapperClass implements TripWrapper {

	private Trip trip;

	public TripWrapperClass(Trip trip) {
		this.trip = trip;
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public BasicDateTime getBasicDateTime() {
		return trip.getBasicDateTime();
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public boolean hasRides() {
		return trip.hasRides();
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public String getDriverEmail() {
		return trip.getDriverEmail();
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public String getOrigin() {
		return trip.getOrigin();
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public String getDestination() {
		return trip.getDestination();
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public int getDuration() {
		return trip.getDuration();
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public boolean hasFreeSlots() {
		return trip.hasFreeSlots();
	}

	@Override
	/**
	 * Best case: O(1)
	 * Average case: O(n), n = size of usersWaitingRide
	 * Worst case: O(n), n = size of usersWaitingRide
	 */
	public String toString() {
		return trip.toString();
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public int freeSlots() {
		return trip.freeSlots();
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public String toMediumDetailString() {
		return trip.toMediumDetailString();
	}

	@Override
	/**
	 * O(1) all cases
	 */
	public String toDateAndDriverString() {
		return trip.toDateAndDriverString();
	}

}
