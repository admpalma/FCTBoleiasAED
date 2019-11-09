package fctBoleias.trip;

import java.io.Serializable;

import basicDateTime.BasicDateTime;

public interface Trip extends Serializable {

	/**
	 * @return a {@link BasicDateTime} holding this {@link Trip Trip's} date
	 */
	BasicDateTime getBasicDateTime();

}
