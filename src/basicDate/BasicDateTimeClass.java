package basicDate;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Class that creates objects used to store dates and manipulate dates
 *
 */
public class BasicDateTimeClass implements BasicDateTime, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	// Constant containing the rawDate's array initial length
	private static final int NUM_FIELDS = 5;

	// Array that is going to store the date's elements (day, month and year)
	private short[] rawDate;

	/**
	 * Builds a new raw date object.
	 * @param date - a string of the form N1-N2-N3,
	 * where N1,N2,N3 are positive numbers representable as integers.
	 * @throws InvalidDateException 
	 */
	public BasicDateTimeClass(String dateTime) throws InvalidDateException {
		String[] dateAndTime = dateTime.split(" ");
		String[] date = dateAndTime[0].split("-");
		String[] time = dateAndTime[1].split(":");
		rawDate = new short[NUM_FIELDS];
		
		int j = date.length - 1;
		for (int i = 0; i < date.length; i++) {
			rawDate[j] = Short.parseShort(date[i].trim());
			j--;
		}
		for (int i = 0; i < time.length; i++) {
			rawDate[i + date.length] = Short.parseShort(date[i].trim());
		}

		if (!isValid()) {
			throw new InvalidDateException();
		}

	}

	/**
	 * @return: boolean true if the date is valid, false otherwise
	 * doesn't user getMonth() or getDay() since they have isValid() as @pre
	 */
	public boolean isValid() {
		int year = rawDate[2];
		int month = rawDate[1];
		int day = rawDate[0];
		int hour = rawDate[3];
		int minutes = rawDate[4];

		return  day > 0 && month > 0 && year > 0 &&
				day <= daysInMonth(month, year) &&
				month <= 12 && hour > 0 && hour < 24 &&
				minutes > 0 && minutes < 60;
	}

	@Override
	public int getYear() {
		assert(isValid());
		return rawDate[2];
	}

	@Override
	public int getDay() {
		assert(isValid());
		return rawDate[0];
	}

	@Override
	public int getMonth() {
		assert(isValid());
		return rawDate[1];
	}

	/**
	 * Checks if a year is a leap year (366 days)
	 * @return boolean true if the year is a leap year and false otherwise
	 * @pre: year > 0
	 */
	private boolean isLeapYear(int year) {
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

	public short[] getRawDate() {
		return rawDate;
	}

	@Override
	public int compareTo(BasicDateTime date) {
		int compareResult = 0, i = 0;
		if (date instanceof BasicDateTimeClass) {
			short[] rawDate = ((BasicDateTimeClass) date).getRawDate();
			while (compareResult == 0) {
				compareResult = Integer.compare(this.rawDate[i], rawDate[i]);
			}
			return compareResult;
		} else {
			return genericCompare(date);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(rawDate);
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
		if (!Arrays.equals(rawDate, other.rawDate))
			return false;
		return true;
	}

	private int genericCompare(BasicDateTime date) {
		int compareResult = Integer.compare(this.getYear(), date.getYear());
		if (compareResult == 0) {
			compareResult = Integer.compare(this.getMonth(), date.getMonth());
		}
		if (compareResult == 0) {
			compareResult = Integer.compare(this.getDay(), date.getDay());
		}
		if (compareResult == 0) {
			compareResult = Integer.compare(this.getHour(), date.getHour());
		}
		if (compareResult == 0) {
			compareResult = Integer.compare(this.getMinutes(), date.getMinutes());
		}
		return compareResult;
	}

	@Override
	public int getHour() {
		return rawDate[3];
	}

	@Override
	public int getMinutes() {
		return rawDate[4];
	}

}
