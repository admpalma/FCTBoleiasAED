package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import basicDateTime.BasicDateTimeClass;
import basicDateTime.InvalidDateException;

class BasicDateTimeClassTest {

	private BasicDateTimeClass[] dates;
	
	private String[] datesInit = {
		"01-01-2010 12:00",
		"01-01-2010 12:02",
		"01-01-2010 13:00",
		"02-01-2010 12:00",
		"01-02-2010 12:00",
		"01-01-2012 12:00"};
	
	private String[] invalidDatesInit = {
		"01-01-0000 12:00",
		"01-00-2010 12:00",
		"01-13-2010 12:00",
		"00-01-2010 12:00",
		"30-02-2010 12:00",
		"31-04-2010 12:00",
		"32-01-2010 12:00",
		"01-01-2010 24:00",
		"01-01-2010 12:60"};

	@BeforeEach
	void setUp() throws Exception {
		dates = new BasicDateTimeClass[6];
		for (int i = 0; i < datesInit.length; i++) {
			String[] newDate = datesInit[i].split(" "); 
			dates[i] = new BasicDateTimeClass(newDate[0], newDate[1]);
		}
	}

	@Test
	void testBasicDateTimeClass() {
		assertEquals(2010, dates[0].getYear());
		assertEquals(1, dates[0].getMonth());
		assertEquals(1, dates[0].getDay());
		assertEquals(12, dates[0].getHour());
		assertEquals(0, dates[0].getMinutes());
		for (int i = 0; i < invalidDatesInit.length; i++) {
			String[] newInvalidDate = invalidDatesInit[i].split(" "); 
			assertThrows(InvalidDateException.class, () -> new BasicDateTimeClass(newInvalidDate[0], newInvalidDate[1]));
		}
	}

	@Test
	void testGetYear() {
		assertEquals(2010, dates[0].getYear());
		assertEquals(2012, dates[5].getYear());
	}

	@Test
	void testGetMonth() {
		assertEquals(1, dates[0].getMonth());
		assertEquals(2, dates[4].getMonth());
	}

	@Test
	void testGetDay() {
		assertEquals(1, dates[0].getDay());
		assertEquals(2, dates[3].getDay());
	}

	@Test
	void testGetHour() {
		assertEquals(12, dates[0].getHour());
		assertEquals(13, dates[2].getHour());
	}

	@Test
	void testGetMinutes() {
		assertEquals(0, dates[0].getMinutes());
		assertEquals(2, dates[1].getMinutes());
	}

	@Test
	void testGetRawDate() {
		short rawDate[] = dates[0].getRawDate();
		assertEquals(2010, rawDate[0]);
		assertEquals(1, rawDate[1]);
		assertEquals(1, rawDate[2]);
		assertEquals(12, rawDate[3]);
		assertEquals(0, rawDate[4]);
	}

	/**
	 * Lacking generic test
	 */
	@Test
	void testCompareTo() {
		for (int i = 0; i < dates.length; i++) {
			assertTrue(dates[i].compareTo(dates[i]) == 0);
		}
		for (int i = 0; i < dates.length; i++) {
			for (int j = 1; j < dates.length && i < j; j++) {
				if ((i == 0 ||i == 1 || i == 2) && (j == 0 || j == 1 || j == 2)) {
					assertTrue(dates[i].compareTo(dates[j]) == 0);
				} else {
					assertTrue(dates[i].compareTo(dates[j]) < 0);
				}
				
			}
		}
		for (int i = 1; i < dates.length; i++) {
			for (int j = 0; j < dates.length && j < i; j++) {
				if ((i == 0 || i == 1 || i == 2) && (j == 0 || j == 1 || j == 2)) {
					assertTrue(dates[i].compareTo(dates[j]) == 0);
				} else {
					assertTrue(dates[i].compareTo(dates[j]) > 0);
				}
				
			}
		}
	}
	
	@Test
	void testToString() {
		for (int i = 0; i < dates.length; i++) {
			assertEquals(datesInit[i], dates[i].toString());
		}
	}

}
