package fctBoleias.trip;

import java.io.Serializable;

import basicDateTime.BasicDateTime;
import fctBoleias.user.User;

public interface Trip extends Serializable {

	/**
	 * Gives the {@link BasicDateTime date} of this {@link Trip}
	 * @return a {@link BasicDateTime} holding this {@link Trip Trip's} date
	 */
	BasicDateTime getBasicDateTime();
	
	// TODO fiz hasRides em vez de dar int numero de users riding porque
	// do que vi, os comandos nao iam usar esse numero para nada cause fila de espera exists
	// if wrong we change and add other getters
	/**
	 * @return boolean true if this {@link Trip} has {@link User users} registered for rides, false otherwise
	 */
	boolean hasRides();

	/**
	 * Adds {@link User} to {@link Trip this} as a ride
	 * @param user {@link User} to add as a ride
	 */
	void addUserAsRide(User user);

	/**
	 * Gives the {@link String email} of the {@link User driver} of this {@link Trip}
	 * @return {@link String driver}
	 */
	String getDriverEmail();

	/**
	 * Gives the {@link String origin} of this {@link Trip}
	 * @return {@link String origin}
	 */
	String getOrigin();
	
	/**
	 * Gives the {@link String destiny} of this {@link Trip}
	 * @return {@link String destiny}
	 */
	String getDestiny();
	
	/**
	 * Gives the duration of this {@link Trip}
	 * @return int duration
	 */
	int getDuration();
	
	

	/**
	 * Checks if there are free slots on this {@link Trip} and fills them with {@link User Users} in queue,
	 * if there are any
	 */
	void updateQueue();

}
