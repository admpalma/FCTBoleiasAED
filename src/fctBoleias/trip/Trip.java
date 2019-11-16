package fctBoleias.trip;

import java.io.Serializable;

import basicDateTime.BasicDateTime;
import dataStructures.Wrappable;
import fctBoleias.user.User;

public interface Trip extends TripWrapper, Serializable, Wrappable<TripWrapper> {

	/**
	 * Gives the {@link BasicDateTime date} of this {@link Trip}
	 * 
	 * @return a {@link BasicDateTime} holding this {@link Trip Trip's} date
	 */
	BasicDateTime getBasicDateTime();

	/**
	 * Adds {@link User} to {@link Trip this} as a ride
	 * 
	 * @param user {@link User} to add as a ride
	 * @throws TripIsFullException if the {@link Trip} is full and the {@link User}
	 *                             is left on the waiting queue
	 */
	void addUserAsRide(User user) throws TripIsFullException;

	/**
	 * Remove the given {@link User} from the gruop of {@link User Users} taking
	 * ride on this {@link Trip}
	 * 
	 * @param currentUser the given {@link User}
	 */
	void removeUserRide(User user);

}
