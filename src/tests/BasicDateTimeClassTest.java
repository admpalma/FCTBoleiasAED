package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import basicDateTime.BasicDateTimeClass;
import basicDateTime.InvalidDateException;

class BasicDateTimeClassTest {

	private BasicDateTimeClass[] dates;

	@BeforeEach
	void setUp() throws Exception {
		dates = new BasicDateTimeClass[6];
		dates[0] = new BasicDateTimeClass("01-01-2010 12:00");
		dates[1] = new BasicDateTimeClass("01-01-2010 12:02");
		dates[2] = new BasicDateTimeClass("01-01-2010 13:00");
		dates[3] = new BasicDateTimeClass("02-01-2010 12:00");
		dates[4] = new BasicDateTimeClass("01-02-2010 12:00");
		dates[5] = new BasicDateTimeClass("01-01-2012 12:00");
	}

	@Test
	void testBasicDateTimeClass() {
		assertEquals(2010, dates[0].getYear());
		assertEquals(1, dates[0].getMonth());
		assertEquals(1, dates[0].getDay());
		assertEquals(12, dates[0].getHour());
		assertEquals(0, dates[0].getMinutes());
		assertThrows(InvalidDateException.class, () -> new BasicDateTimeClass("01-01-0 12:00"));
		assertThrows(InvalidDateException.class, () -> new BasicDateTimeClass("01-00-2010 12:00"));
		assertThrows(InvalidDateException.class, () -> new BasicDateTimeClass("01-13-2010 12:00"));
		assertThrows(InvalidDateException.class, () -> new BasicDateTimeClass("00-01-2010 12:00"));
		assertThrows(InvalidDateException.class, () -> new BasicDateTimeClass("30-02-2010 12:00"));
		assertThrows(InvalidDateException.class, () -> new BasicDateTimeClass("31-04-2010 12:00"));
		assertThrows(InvalidDateException.class, () -> new BasicDateTimeClass("32-01-2010 12:00"));
		assertThrows(InvalidDateException.class, () -> new BasicDateTimeClass("01-01-2010 24:00"));
		assertThrows(InvalidDateException.class, () -> new BasicDateTimeClass("01-01-2010 12:60"));
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
				assertTrue(dates[i].compareTo(dates[j]) < 0);
			}
		}
		for (int i = 1; i < dates.length; i++) {
			for (int j = 0; j < dates.length && j < i; j++) {
				assertTrue(dates[i].compareTo(dates[j]) > 0);
			}
		}
	}
	
	@Test
	void testToString() {
		fail("Not yet implemented");
	}

}
