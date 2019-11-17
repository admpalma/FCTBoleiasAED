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
	public BasicDateTime getBasicDateTime() {
		return trip.getBasicDateTime();
	}

	@Override
	public boolean hasRides() {
		return trip.hasRides();
	}

	@Override
	public String getDriverEmail() {
		return trip.getDriverEmail();
	}

	@Override
	public String getOrigin() {
		return trip.getOrigin();
	}

	@Override
	public String getDestination() {
		return trip.getDestination();
	}

	@Override
	public int getDuration() {
		return trip.getDuration();
	}

	@Override
	public boolean hasFreeSlots() {
		return trip.hasFreeSlots();
	}

	@Override
	public String toString() {
		return trip.toString();
	}

	@Override
	public int freeSlots() {
		return trip.freeSlots();
	}

	@Override
	public String toMediumDetailString() {
		return trip.toMediumDetailString();
	}

	@Override
	public String toDateAndDriverString() {
		return trip.toDateAndDriverString();
	}

}
