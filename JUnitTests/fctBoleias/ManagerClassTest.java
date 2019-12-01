package fctBoleias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fctBoleias.InvalidPasswordFormatException;
import fctBoleias.LoggedInException;
import fctBoleias.Manager;
import fctBoleias.ManagerClass;
import fctBoleias.NonExistentUserException;
import fctBoleias.NotLoggedInException;
import fctBoleias.UserAlreadyRegisteredException;
import fctBoleias.user.IncorrectPasswordException;

class ManagerClassTest {

	private Manager manager;

	private String[] email = { "email1", "email2", "email3", "email4", "email5" };

	private String[] name = { "name1", "name2", "name3", "name4", "name5" };

	private String[] password = { "valid1", "valid2", "valid3", "valid4", "valid5" };

	@BeforeEach
	void setUp() throws Exception {
		manager = new ManagerClass();
	}

	@Test
	void testManagerClass() {
		assertFalse(manager.isLoggedIn());
		assertThrows(NotLoggedInException.class, () -> manager.getCurrentUserName());
		assertThrows(NotLoggedInException.class, () -> manager.getCurrentUserEmail());
		assertThrows(NotLoggedInException.class, () -> manager.getCurrentUserTripNumber());
	}

	@Test
	void testIsLoggedIn() {
		assertFalse(manager.isLoggedIn());
		testRegisterUser();
		assertFalse(manager.isLoggedIn());
		for (int i = 0; i < email.length; i++) {
			try {
				manager.userLogin(email[i], password[i]);
			} catch (Exception e) {
				throw new AssertionError("Should be unreachable");
			}
			assertTrue(manager.isLoggedIn());
			manager.logoutCurrentUser();
			assertFalse(manager.isLoggedIn());
		}
	}

	@Test
	void testUserLogin() {
		for (int i = 0; i < email.length; i++) {
			int index = i;
			assertThrows(NonExistentUserException.class, () -> manager.userLogin(email[index], password[index]));
		}
		testRegisterUser();
		for (int i = 0; i < email.length; i++) {
			int index = i;
			assertThrows(IncorrectPasswordException.class, () -> manager.userLogin(email[index], "###"));
		}
		for (int i = 0; i < email.length; i++) {
			try {
				manager.userLogin(email[i], password[i]);
			} catch (Exception e) {
				throw new AssertionError("Should be unreachable");
			}
			int index = i;
			assertThrows(LoggedInException.class, () -> manager.userLogin(email[index], password[index]));
			manager.logoutCurrentUser();
		}

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
		for (int i = 0; i < email.length; i++) {
			assertFalse(manager.isUserRegistered(email[i]));
		}
		for (int i = 0; i < email.length; i++) {
			try {
				manager.registerUser(email[i], name[i], password[i]);
			} catch (InvalidPasswordFormatException e) {
				throw new AssertionError("Should be unreachable");
			}
		}
		for (int i = 0; i < email.length; i++) {
			assertTrue(manager.isUserRegistered(email[i]));
		}
	}

	@Test
	void testRegisterUser() {
		for (int i = 0; i < email.length; i++) {
			assertFalse(manager.isUserRegistered(email[i]));
		}
		for (int i = 0; i < email.length; i++) {
			int index = i;
			assertThrows(InvalidPasswordFormatException.class,
					() -> manager.registerUser(email[index], name[index], "###"));
		}
		for (int i = 0; i < email.length; i++) {
			try {
				assertEquals(i + 1, manager.registerUser(email[i], name[i], password[i]));
			} catch (InvalidPasswordFormatException e) {
				throw new AssertionError("Should be unreachable");
			}
		}
		for (int i = 0; i < email.length; i++) {
			int index = i;
			assertThrows(UserAlreadyRegisteredException.class,
					() -> manager.registerUser(email[index], name[index], password[index]));
		}
		for (int i = 0; i < email.length; i++) {
			assertTrue(manager.isUserRegistered(email[i]));
		}
	}

}
