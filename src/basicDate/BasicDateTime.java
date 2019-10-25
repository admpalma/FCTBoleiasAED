package basicDate;

public interface BasicDateTime extends Comparable<BasicDateTime> {

	/**
	 * @return the year field of this date
	 */
	int getYear();

	/**
	 * @return the month field of this date
	 */
	int getMonth();

	/**
	 * @return the day field of this date
	 */
	int getDay();
	
	int getHour();
	
	int getMinutes();
	
	int compareTo(BasicDateTime date);
	
	String toString();

}
