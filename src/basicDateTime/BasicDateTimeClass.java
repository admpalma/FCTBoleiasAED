	package basicDateTime;

import java.util.Arrays;

/**
 * An implementation of {@link BasicDateTime} using a <code>short[]</code>
 */
public class BasicDateTimeClass implements BasicDateTime {

	/**
	 * Number of positions in {@link BasicDateTimeClass#rawDate rawDate} reserved for only the date
	 */
	private static final int NUM_DATE_ONLY_FIELDS = 3;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static final String DATE_TOSTRING_FORMAT = "%d-%d-%d %d:%d";

	/**
	 * {@link BasicDateTimeClass#rawDate rawDate's} array length
	 */
	private static final int NUM_FIELDS = 5;

	private static final String DATE_TODATESTRING_FORMAT = "%d-%d-%d";

	/**
	 * Array storing the date and time's elements <code>(year, month, day, hour and minutes)</code>
	 */
	private short[] rawDate;

	/**
	 * Converts a {@link String} into a {@link BasicDateTimeClass}
	 * This constructor takes a date and a time TODO
	 * @param date a {@link String} with the format <code>"dd-mm-yyyy"</code>
	 * @param time a {@link String} with the format <code>"hh:mm"</code>
	 * @throws InvalidDateException if <code>date</code> contains impossible values
	 */
	public BasicDateTimeClass(String date, String time) throws InvalidDateException {
		//String[] dateAndTime = dateTime.split(" ");
		//String[] date = dateAndTime[0].split("-");
		String[] newDate = date.split("-");
		//String[] time = dateAndTime[1].split(":");
		String[] newTime = time.split(":");
		rawDate = new short[NUM_FIELDS];

		int j = newDate.length - 1;
		for (int i = 0; i < newDate.length; i++) {
			rawDate[j] = Short.parseShort(newDate[i].trim());
			j--;
		}
		for (int i = 0; i < newTime.length; i++) {
			rawDate[i + newDate.length] = Short.parseShort(newTime[i].trim());
		}
		if (!isValid()) {
			throw new InvalidDateException();
		}
	}

	/**
	 * Converts a {@link String} into a {@link BasicDateTimeClass}
	 * This constructor takes a {@link String date} only (when time parameter not needed) TODO
	 * @param date a {@link String} with the format <code>"dd-mm-yyyy"</code>
	 * @throws InvalidDateException if <code>date</code> contains impossible values
	 */
	public BasicDateTimeClass(String date) throws InvalidDateException {
		this(date, "00:00"); // Gives placeholder time
	}

	/**
	 * @return: boolean true if the date is valid, false otherwise
	 * doesn't use getMonth() or getDay() since they have isValid() as @pre
	 */
	private boolean isValid() {
		int year = rawDate[0];
		int month = rawDate[1];
		int day = rawDate[2];
		int hour = rawDate[3];
		int minutes = rawDate[4];

		return  year > 0 && month > 0 && month <= 12 &&
				day > 0 && day <= daysInMonth(month, year) &&
				hour >= 0 && hour < 24 &&
				minutes >= 0 && minutes < 60;
	}

	@Override
	public int getYear() {
		return rawDate[0];
	}

	@Override
	public int getMonth() {
		return rawDate[1];
	}

	@Override
	public int getDay() {
		return rawDate[2];
	}

	@Override
	public int getHour() {
		return rawDate[3];
	}

	@Override
	public int getMinutes() {
		return rawDate[4];
	}

	/**
	 * Checks if a year is a leap year (366 days)
	 * @return boolean true if the year is a leap year and false otherwise
	 */
	private boolean isLeapYear(int year) {
		assert(year > 0);
		// if it's not divisible by 4 it's a common year
		// elif it's not divisible by 100 it's a leap year
		// elif it's not divisible by 400 it's a common year
		// else it's a leap year
		// if it's divisible by four and not divisible by 100 OR it is divisible by 400 it's leap
		//return ((year % 4 == 0) && (year % 100 != 0)) || year % 400 == 0;
		return year % 4 == 0;
	}

	/**
	 * Returns the number of days in a given month
	 * @param month - month to check
	 * @param year - year to check if we're on a leap year
	 * @return int number of days in given month
	 */
	private int daysInMonth(int month, int year) {
		/* 28, 29 => 2, depending on leap year
		 * 31 => 1, 3, 5, 7, 8, 10, 12
		 * 30 => 4, 6, 9, 11
		 */
		int days = 31;
		if (month == 2) {  // February
			if (isLeapYear(year)) days = 29;
			else days = 28;
		} else if (month < 7 && month % 2 == 0 || (month > 7 && month % 2 != 0)) days = 30;
		return days;
	}

	/**
	 * @return this {@link BasicDateTimeClass} {@link BasicDateTimeClass#rawDate rawDate}
	 */
	public short[] getRawDate() {
		assert(isValid());
		return rawDate;
	}

	/**
	 * @return <code>short[]</code> containing the date only fields in a raw state
	 */
	public short[] getDateOnlyVector() {
		short[] dateOnlyVector = new short[NUM_DATE_ONLY_FIELDS];
		for (int i = 0; i < NUM_DATE_ONLY_FIELDS; i++) {
			dateOnlyVector[i] = rawDate[i];
		}
		return dateOnlyVector;
	}

	@Override
	public int compareTo(BasicDateTime date) {
		if (date instanceof BasicDateTimeClass) {
			int compareResult = 0, testField = 0;
			short[] rawDate = ((BasicDateTimeClass) date).getDateOnlyVector();
			short[] thisRawDate = this.getDateOnlyVector();
			while (compareResult == 0 && testField < NUM_DATE_ONLY_FIELDS) {
				compareResult = Integer.compare(thisRawDate[testField], rawDate[testField]);
				testField++;
			}
			return compareResult;
		} else {
			return genericCompare(date);
		}
	}

	@Override
	public String toString() {
		assert(isValid());
		return String.format(DATE_TOSTRING_FORMAT, rawDate[2], rawDate[1], rawDate[0], rawDate[3], rawDate[4]);
	}

	/**
	 * Generic version of {@link BasicDateTimeClass#compareTo(BasicDateTime) compareTo(BasicDateTime)}
	 * @param date the {@link BasicDateTime} to be compared
	 * @see {@link BasicDateTimeClass#compareTo(BasicDateTime) compareTo(BasicDateTime)}
	 */
	private int genericCompare(BasicDateTime date) {
		int compareResult = Integer.compare(this.getYear(), date.getYear());
		if (compareResult == 0) {
			compareResult = Integer.compare(this.getMonth(), date.getMonth());
		} else if (compareResult == 0) {
			compareResult = Integer.compare(this.getDay(), date.getDay());
		}
		return compareResult;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(getDateOnlyVector());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicDateTimeClass other = (BasicDateTimeClass) obj;
		if (!Arrays.equals(this.getDateOnlyVector(), other.getDateOnlyVector()))
			return false;
		return true;
	}

	@Override
	public String toDateString() {
		assert(isValid());
		return String.format(DATE_TODATESTRING_FORMAT, rawDate[2], rawDate[1], rawDate[0]);
	}

}
