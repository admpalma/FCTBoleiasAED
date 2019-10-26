package basicDateTime;

/**
 * Stores and represents a date and time
 */
public interface BasicDateTime extends Comparable<BasicDateTime> {

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

}
