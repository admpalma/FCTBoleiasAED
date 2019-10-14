package basicDate;
/**
 * Class that creates objects used to store dates and manipulate dates
 *
 */
public class BasicDateClass {
	// Constant containing the rawDate's array initial length
	private static final int NUM_FIELDS = 3;
	
	// Array that is going to store the date's elements (day, month and year)
	private int[] rawDate;

	/**
	 * Builds a new raw date object. 
	 * @param date - a string of the form N1-N2-N3, 
	 * where N1,N2,N3 are positive numbers representable as integers.
	 */
	public BasicDateClass(String date) {
		String[] split = date.split("-");
		rawDate = new int[NUM_FIELDS];

		for (int i = 0; i < split.length; i++) {
			rawDate[i] = Integer.parseInt(split[i].trim());
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
		
		
		return  day > 0 && month > 0 && year > 0 &&
				day <= daysInMonth(month, year) && 
				month <= 12;
	}

	/**
	 * Returns the year field of this date, assuming the string used
	 * in the constructor was a valid date (i.e., isValid() ).
	 * @pre: isValid()
	 */
	public int getYear() {
		return rawDate[2];
	}
	
	/**
	 * Returns the day field of this date, assuming the string used
	 * in the constructor was a valid date (i.e., isValid() ).
	 * @pre: isValid()
	 */
	public int getDay() {
		return rawDate[0];
	}
	
	/**
	 * Returns the month field of this date, assuming the string used
	 * in the constructor was a valid date (i.e., isValid() ).
	 * @pre: isValid()
	 */
	public int getMonth() {
		return rawDate[1];
	}

	/** If date bigger or equal to another i.e. 2017/08/20 > 2017/05/20
	 * myDate.biggerOrEqual(BasicDate yourDate);
	 * @param: date - date given date to compare
	 * @return: boolean if this.date is bigger or equal to given date
	 */
	public boolean biggerOrEqual(BasicDateClass date) {
		boolean biggerOrEqual = true;

		if (this.getYear() == date.getYear()) {
		// IF THE YEARS ARE EQUAL CHECKS MONTH, ELSE THE RESULT WILL BE !GETYEAR()<DATE.GETYEAR(), DONT NEED CONDITIONS
			if (this.getMonth() == date.getMonth()) {
			// IF THE MONTHS ARE EQUAL CHECKS DAY, ELSE THE RESULT WILL BE !GETMONTH()<DATE.GETMONTH()	
				biggerOrEqual = !(this.getDay() < date.getDay());
			}
			else {
				biggerOrEqual = !(this.getMonth() < date.getMonth());
			}
		} else {
			biggerOrEqual = !(this.getYear() < date.getYear());
		}
		return biggerOrEqual;
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
	
	/** If date equal to another i.e. 2017/05/20 == 2017/05/20
	 * myDate.equal(BasicDate yourDate);
	 * @param: date - given date to compare
	 * @return: boolean true if this.date is equal to given date
	 */
	public boolean equal(BasicDateClass date) {
		//TODO .equals() e uma coisa
		boolean equal = false;

		if (this.getDay() == date.getDay() && this.getMonth() == date.getMonth() && this.getYear() == date.getYear()) {
			equal = true;
		}
		return equal;
	}
	
	public String giveBasicDateString() {
		return getDay() + "-" + getMonth() + "-" + getYear();
	}
}
