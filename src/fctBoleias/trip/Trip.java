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
	
}
