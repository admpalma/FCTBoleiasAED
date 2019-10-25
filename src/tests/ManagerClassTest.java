package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fctBoleias.InvalidPasswordFormatException;
import fctBoleias.Manager;
import fctBoleias.ManagerClass;
import fctBoleias.NotLoggedInException;

class ManagerClassTest {

	private Manager manager;
	private String email;
	private String name;
	private String password;
	
	@BeforeEach
	void setUp() throws Exception {
		manager = new ManagerClass();
		email = "email1";
		name = "name1";
		password = "valid";
	}

	@Test
	void testManagerClass() {
		assertFalse(manager.isLoggedIn());
		assertThrows(NotLoggedInException.class, () -> manager.getCurrentUserName());
	}

	@Test
	void testIsLoggedIn() {
		fail("Not yet implemented");
	}

	@Test
	void testAddNewTrip() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCurrentUserName() {
		fail("Not yet implemented");
	}

	@Test
	void testRemove() {
		fail("Not yet implemented");
	}

	@Test
	void testAddNewRide() {
		fail("Not yet implemented");
	}

	@Test
	void testIsUserRegistered() {
		assertFalse(manager.isUserRegistered(email));
		try {
			manager.registerUser(email, name, password);
		} catch (InvalidPasswordFormatException e) {
			
		}
		assertTrue(manager.isUserRegistered(email));
	}

	@Test
	void testRegisterUser() {
		try {
			assertFalse(manager.isUserRegistered(email));
			assertFalse(manager.isUserRegistered("email"));
			assertEquals(1, manager.registerUser(email, name, password));
			assertEquals(2, manager.registerUser("email", "name", "pass"));
			assertThrows(IllegalArgumentException.class, () -> manager.registerUser(email, name, password));
			assertThrows(IllegalArgumentException.class, () -> manager.registerUser("email", "name", "pass"));
			assertTrue(manager.isUserRegistered(email));
			assertTrue(manager.isUserRegistered("email"));
		} catch (InvalidPasswordFormatException e) {
			
		}
	}

}
