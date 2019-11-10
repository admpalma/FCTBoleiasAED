package basicDateTime;

import java.io.Serializable;

/**
 * Stores and represents a date and time
 */
public interface BasicDateTime extends Comparable<BasicDateTime>, Serializable {

	/**
	 * @return the <code>year</code> field of this date
	 */
	int getYear();

	/**
	 * @return the <code>month</code> field of this date
	 */
	int getMonth();

	/**
	 * @return the <code>day</code> field of this date
	 */
	int getDay();
	
	/**
	 * @return the <code>hour</code> field of this time
	 */
	int getHour();
	
	/**
	 * @return the <code>minutes</code> field of this time
	 */
	int getMinutes();
	
	/**
	 * Returns a {@link String} with the format <code>"dd-mm-yyyy hh:mm"</code>
	 * @return this {@link BasicDateTime BasicDateTime's} {@link String} version
	 */
	String toString();
	
	/**
	 * Compares two date and time objects considering only the date
	 * @param date {@link BasicDateTime} to be compared with
	 */
	@Override
	int compareTo(BasicDateTime date);
	
	/**
	 * Narrows the equality requirements to only consider the date attributes
	 * @param obj the reference object with which to compare.
	 * @return <code>true</code> if this object is the same as the objargument;
	 * <code>false</code> otherwise.
	 */
	@Override
	boolean equals(Object obj);

	/**
	 * Returns a {@link String} with the format <code>"dd-mm-yyyy"</code>
	 * @return this {@link BasicDateTime BasicDateTime's} {@link Date String} version
	 */
	String toDateString();

}
