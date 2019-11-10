import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import basicDateTime.InvalidDateException;
import dataStructures.Iterator;
import dataStructures.SortedMap;
import fctBoleias.DateOccupiedException;
import fctBoleias.InvalidPasswordFormatException;
import fctBoleias.Manager;
import fctBoleias.ManagerClass;
import fctBoleias.NoRegisteredTripsException;
import fctBoleias.NoRideOnDayException;
import fctBoleias.NoTripOnDayException;
import fctBoleias.NonExistentTripException;
import fctBoleias.NonExistentUserException;
import fctBoleias.trip.CantRideSelfException;
import fctBoleias.trip.InvalidTripDataException;
import fctBoleias.trip.Trip;
import fctBoleias.trip.TripHasRidesException;
import fctBoleias.trip.TripIsFullException;
import fctBoleias.trip.TripWrapper;
import fctBoleias.user.IncorrectPasswordException;
import fctBoleias.user.User;

public class Main {

	private static final String DEFAULT_PROMPT = "> ";

	/**
	 * Messages used in {@link Main#addTrip}
	 */
	private static final String USERNAME_ALREADY_HAS_REGISTERED_TRIP_RIDE_ON_DATE = "%s ja tem uma deslocacao ou boleia registada nesta data.%n";
	private static final String TRIP_N_REGISTERED_THANKS_USERNAME = "Deslocacao %d registada. Obrigado %s.%n";

	/**
	 * Messages used in {@link Main#takeRide} and {@link Main#consult}
	 */
	private static final String USERNAME_ALREADY_REGISTERED_TRIP_RIDE_ON_DATE = "%s ja registou uma boleia ou deslocacao nesta data.%n";
	private static final String INEXISTENT_USER = "Utilizador inexistente.";
	private static final String RIDE_REGISTERED = "Boleia registada.";

	/**
	 * Messages used in {@link Main#exit}
	 */
	private static final String EXIT_MESSAGE_USERNAME = "Ate a proxima %s.%n";

	/**
	 * Messages used in {@link Main#list}
	 */
	private static final String GIVEN_USER_NOT_EXISTS = "Nao existe o utilizador dado.";
	private static final String DATE_ALLOWED_PATTERN = "^[0-9-]+$";

	/**
	 * Messages used in {@link Main#cancelRide}
	 */
	private static final String USERNAME_RIDE_REMOVED = "%s boleia retirada.%n";

	/**
	 * Messages used in {@link Main#processEnd}
	 */
	private static final String END_MESSAGE = "Obrigado. Ate a proxima.";

	/**
	 * Messages used in {@link Main#registerUser}
	 */
	private static final String REGISTO_N_EFETUADO = "Registo %d efetuado.%n";
	private static final String USER_ALREADY_EXISTS = "Utilizador ja existente.";
	private static final String ASK_NAME_REGISTER = "nome (maximo 50 caracteres): ";
	private static final String ASK_PW_REGISTER = "password (entre 4 e 6 caracteres - digitos e letras): ";

	/**
	 * Messages used in {@link Main#login}
	 */
	private static final String NON_EXISTENT_USER = "Utilizador nao existente.";
	private static final String VISITA_N_EFETUADA = "Visita %d efetuada.%n";
	private static final String ASK_PW_LOGIN = "password: ";

	/**
	 * Messages used in {@link Main#remove}
	 */
	private static final String TRIP_REMOVED = "Deslocacao removida.";

	/**
	 * The number of attempts a user has to choose a password for his registration
	 */
	private static final int PASSWORD_ATTEMPTS_LIMIT = 3;

	/**
	 * Location where the serialized {@link Manager} is to be stored at
	 */
	private static final String SERIALIZING_LOCATION = "manager.ser";

	/**
	 * {@link Enum} containing all of the possible commands and warning messages
	 * where applicable.
	 */
	private enum Commands {
		AJUDA, TERMINA, REGISTA, ENTRADA, SAI, NOVA, LISTA, BOLEIA, CONSULTA, REMOVE, RETIRA,
		UNKNOWN("Comando invalido.");

		/**
		 * Stores each {@link Commands command's} warning message (where applicable)
		 */
		private final String warningMessage;

		private Commands() {
			this.warningMessage = null;
		}

		private Commands(String warningMessage) {
			this.warningMessage = warningMessage;
		}

		/**
		 * @return the warningMessage
		 */
		public String getWarningMessage() {
			return warningMessage;
		}

		/**
		 * Converts the user's input into a {@link Commands command}
		 * 
		 * @param in - {@link Scanner} containing the user's input
		 * @return the matching {@link Command command} to the user's input,
		 *         {@link Commands.UNKNOWN} in case there's no other valid match
		 */
		private static Commands getCommand(Scanner in) {
			try {
				assert (in.hasNextLine());
				String command = in.next().toUpperCase();
				return Commands.valueOf(command);
			} catch (IllegalArgumentException e) {
				return Commands.UNKNOWN;
			}
		}

	}

	/**
	 * {@link Enum} containing the commands that can be executed when there is no
	 * {@link User} logged in and its corresponding help messages.
	 */
	private enum LoggedOutCommands {
		AJUDA("ajuda - Mostra os comandos existentes"), 
		TERMINA("termina - Termina a execucao do programa"),
		REGISTA("regista - Regista um novo utilizador no programa"),
		ENTRADA("entrada - Permite a entrada (\"login\") dum utilizador no programa");

		/**
		 * Stores each {@link LoggedOutCommands command's} help message
		 */
		private final String helpMessage;

		private LoggedOutCommands(String helpMessage) {
			this.helpMessage = helpMessage;
		}

		/**
		 * @return this {@link LoggedOutCommands command´s} the help message
		 */
		public String getHelpMessage() {
			return helpMessage;
		}

		/**
		 * Converts a {@link Commands} type command into {@link LoggedOutCommands} if
		 * such is possible, otherwise returns {@link IllegalArgumentException}
		 * 
		 * @param command {@link Commands} type to be converted
		 * @return the converted command
		 * @throws IllegalArgumentException if it is not possible to convert the given
		 *                                  command
		 */
		public static LoggedOutCommands toLoggedOutCommand(Commands command) throws IllegalArgumentException {
			return LoggedOutCommands.valueOf(command.name());
		}

	}

	/**
	 * {@link Enum} containing the commands that can be executed when there is a
	 * {@link User} logged in and its corresponding help messages.
	 */
	private enum LoggedInCommands {
		AJUDA("ajuda - Mostra os comandos existentes"), 
		SAI("sai - Termina a sessao deste utilizador no programa"),
		NOVA("nova - Regista uma nova deslocacao"), 
		LISTA("lista - Lista todas ou algumas deslocacoes registadas"),
		BOLEIA("boleia - Regista uma boleia para uma dada deslocacao"),
		CONSULTA("consulta - Lista a informacao de uma dada deslocacao"), 
		RETIRA("retira - Retira uma dada boleia"),
		REMOVE("remove - Elimina uma dada deslocacao");

		/**
		 * Stores each {@link LoggedInCommands command's} help message
		 */
		private final String helpMessage;

		private LoggedInCommands(String helpMessage) {
			this.helpMessage = helpMessage;
		}

		/**
		 * @return this {@link LoggedInCommands command´s} the help message
		 */
		public String getHelpMessage() {
			return helpMessage;
		}

		/**
		 * Converts a {@link Commands} type command into {@link LoggedInCommands} if
		 * such is possible, otherwise returns {@link IllegalArgumentException}
		 * 
		 * @param command {@link Commands} type to be converted
		 * @return the converted command
		 * @throws IllegalArgumentException if it is not possible to convert the given
		 *                                  command
		 */
		public static LoggedInCommands toLoggedInCommand(Commands command) throws IllegalArgumentException {
			return LoggedInCommands.valueOf(command.name());
		}

	}

	// MENU RELATED METHODS

	/**
	 * Starts the execution of the program FCTBoleias, resuming its status from the
	 * previous execution and saves it when the user chooses to exit.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Manager manager;
		try {
			manager = deserializeManager(SERIALIZING_LOCATION);
		} catch (FileNotFoundException e) {
			manager = new ManagerClass();
		}
		try (Scanner in = new Scanner(System.in)) {
			Commands command;
			do {
				printPrompt(manager);
				command = Commands.getCommand(in);
				if (manager.isLoggedIn()) {
					executeLoggedInCommand(command, manager, in);
				} else {
					executeLoggedOutCommand(command, manager, in);
				}
			} while (manager.isLoggedIn() || !(command.equals(Commands.TERMINA)));
		}
		serializeManager(manager, SERIALIZING_LOCATION);

	}

	/**
	 * Serializes the {@link Manager} object in a specified file location
	 * 
	 * @param manager  {@link Manager} to be serialized
	 * @param location file location of the serialized object
	 * @throws IOException
	 */
	private static void serializeManager(Manager manager, String location) throws IOException {
		try (FileOutputStream fileOut = new FileOutputStream(location)) {
			try (ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
				out.writeObject(manager);
			}
		}
	}

	/**
	 * Deserializes a {@link Manager} object from a specified file location
	 * 
	 * @param location file location of the serialized object
	 * @return deserialized {@link Manager}
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static Manager deserializeManager(String location) throws ClassNotFoundException, IOException {
		try (FileInputStream fileIn = new FileInputStream(location)) {
			try (ObjectInputStream in = new ObjectInputStream(fileIn)) {
				Manager manager = (Manager) in.readObject();
				return manager;
			}
		}
	}

	/**
	 * Prints the command prompt (dependent on whether there is a {@link User}
	 * logged in or not)
	 * 
	 * @param manager {@link Manager} on which to verify whether there is a
	 *                {@link User} logged in or not
	 */
	private static void printPrompt(Manager manager) {
		if (manager.isLoggedIn()) {
			System.out.printf("%s %s", manager.getCurrentUserEmail(), DEFAULT_PROMPT);
		} else {
			System.out.print(DEFAULT_PROMPT);
		}
	}

	/**
	 * Attempts to execute the given {@link Commands command} assuming there is no
	 * {@link User} logged in, and prints a warning in case this attempt fails.
	 * 
	 * @param command {@link Commands Command} to be executed
	 * @param manager {@link Manager} containing the most relevant data of the
	 *                program
	 * @param in      {@link Scanner} that might contain additional user input
	 */
	private static void executeLoggedOutCommand(Commands command, Manager manager, Scanner in) {
		try {
			assert (!manager.isLoggedIn());
			LoggedOutCommands loggedOutCommand = LoggedOutCommands.toLoggedOutCommand(command);
			loggedOutCommandInterpreter(manager, in, loggedOutCommand);
		} catch (IllegalArgumentException e) {
			unknownCommand(in);
		}
	}

	// NOT LOGGED IN METHODS

	/**
	 * Command interpreter for "no user logged in" context Assumes there's no
	 * {@link User} logged in
	 * 
	 * @param manager          {@link Manager} containing the most relevant data of
	 *                         the program
	 * @param in               {@link Scanner} that might contain additional user
	 *                         input
	 * @param loggedOutCommand the {@link LoggedOutCommands LoggedOutCommand} to be
	 *                         run
	 */
	private static void loggedOutCommandInterpreter(Manager manager, Scanner in, LoggedOutCommands loggedOutCommand) {
		assert (!manager.isLoggedIn());
		switch (loggedOutCommand) {
		case AJUDA:
			loggedOutHelp(manager);
			break;
		case ENTRADA:
			login(manager, in);
			break;
		case REGISTA:
			registerUser(manager, in);
			break;
		case TERMINA:
			processEnd(manager);
			break;
		}
	}

	/**
	 * Logs in a {@link User} in the system ({@link Manager}) Assumes there's no
	 * {@link User} already logged in
	 * 
	 * @param manager {@link Manager} where the {@link User} will log in
	 * @param in      {@link Scanner} holding the {@link User User's} login
	 *                information
	 */
	private static void login(Manager manager, Scanner in) {
		assert (!manager.isLoggedIn());
		try {
			String email = in.nextLine().trim();
			if (manager.isUserRegistered(email)) {
				int loginNumber = attemptLoginLoop(manager, in, email);
				System.out.printf(VISITA_N_EFETUADA, loginNumber);
				assert (manager.isLoggedIn());
			} else {
				System.out.println(NON_EXISTENT_USER);
			}
		} catch (IncorrectPasswordException e) {
			System.out.println(e.getMessage());
		} catch (NonExistentUserException e) {
			throw new AssertionError("Execution should never reach this point!");
		}

	}

	/**
	 * Asks the user repeatedly for a password for his login until the correct one
	 * is supplied or the {@link Main#PASSWORD_ATTEMPTS_LIMIT attempts limit} is
	 * reached Assumes there's no {@link User} already logged in
	 * 
	 * @param manager {@link Manager} where the {@link User} will log in
	 * @param in      {@link Scanner} holding the {@link User User's} login
	 *                information
	 * @param email   {@link User User's} login email
	 * @return ordinal number of this {@link User User's} login
	 * @throws NonExistentUserException   if there's no {@link User} registered in
	 *                                    the {@link Manager system} with the given
	 *                                    <code>email</code>
	 * @throws IncorrectPasswordException if the {@link User} doesn't provide a
	 *                                    valid password within the
	 *                                    {@link Main#PASSWORD_ATTEMPTS_LIMIT
	 *                                    attempts limit}
	 */
	private static int attemptLoginLoop(Manager manager, Scanner in, String email)
			throws NonExistentUserException, IncorrectPasswordException {
		int attemptNumber = 1;
		String password;
		while (attemptNumber <= PASSWORD_ATTEMPTS_LIMIT) {
			try {
				System.out.print(ASK_PW_LOGIN);
				password = in.nextLine();
				return manager.userLogin(email, password);
			} catch (IncorrectPasswordException e) {
				if (attemptNumber == PASSWORD_ATTEMPTS_LIMIT) {
					throw e;
				}
			}
			attemptNumber++;
		}
		throw new AssertionError("Execution should never reach this point!");
	}

	/**
	 * Registers a new {@link User} in the system ({@link Manager}) Assumes there's
	 * no {@link User} logged in
	 * 
	 * @param manager {@link Manager} where the {@link User} will be registered
	 * @param in      {@link Scanner} holding the new {@link User User's} data
	 */
	private static void registerUser(Manager manager, Scanner in) {
		assert (!manager.isLoggedIn());
		String email = in.nextLine().trim();
		if (manager.isUserRegistered(email)) {
			System.out.println(USER_ALREADY_EXISTS);
		} else {
			try {
				System.out.print(ASK_NAME_REGISTER);
				String name = in.nextLine();
				int registrationNumber = attemptRegistrationLoop(manager, in, email, name);
				System.out.printf(REGISTO_N_EFETUADO, registrationNumber);
			} catch (InvalidPasswordFormatException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Asks the user repeatedly for a password for his registration until a valid
	 * one is picked or the {@link Main#PASSWORD_ATTEMPTS_LIMIT attempts limit} is
	 * reached
	 * 
	 * @param manager manager {@link Manager} where the {@link User} will be
	 *                registered
	 * @param in      {@link Scanner} holding the new {@link User User's}
	 *                <code>password</code>
	 * @param email   new {@link User User's} email
	 * @param name    new {@link User User's} name
	 * @return the number of this registration
	 * @throws InvalidPasswordFormatException if the {@link User} doesn't choose a
	 *                                        valid password within the
	 *                                        {@link Main#PASSWORD_ATTEMPTS_LIMIT
	 *                                        attempts limit}
	 */
	private static int attemptRegistrationLoop(Manager manager, Scanner in, String email, String name)
			throws InvalidPasswordFormatException {
		int attemptNumber = 1;
		while (attemptNumber <= PASSWORD_ATTEMPTS_LIMIT) {
			try {
				System.out.print(ASK_PW_REGISTER);
				String password = in.nextLine();
				return manager.registerUser(email, name, password);
			} catch (InvalidPasswordFormatException e) {
				if (attemptNumber == PASSWORD_ATTEMPTS_LIMIT) {
					throw e;
				}
			}
			attemptNumber++;
		}
		throw new AssertionError("Execution should never reach this point!");
	}

	/**
	 * Prints the help messages associated with a {@link User} not being logged in
	 * Assumes there's no {@link User} logged in
	 * 
	 * @param manager {@link Manager} containing the most relevant data of the
	 *                program
	 */
	private static void loggedOutHelp(Manager manager) {
		assert (!manager.isLoggedIn());
		for (LoggedOutCommands command : LoggedOutCommands.values()) {
			System.out.printf(command.getHelpMessage());
			System.out.println();
		}
	}

	/**
	 * Prints the ending of program message Assumes there's no {@link User} logged
	 * in
	 * 
	 * @param manager {@link Manager} containing the most relevant data of the
	 *                program
	 */
	private static void processEnd(Manager manager) {
		assert (!manager.isLoggedIn());
		System.out.println(END_MESSAGE);
	}

	// LOGGED IN METHODS

	/**
	 * Attempts to execute the given {@link Commands command} assuming there's a
	 * {@link User} logged in, and prints a warning in case this attempt fails.
	 * 
	 * @param command {@link Commands Command} to be executed
	 * @param manager {@link Manager} containing the most relevant data of the
	 *                program
	 * @param in      {@link Scanner} that might contain additional user input
	 */
	private static void executeLoggedInCommand(Commands command, Manager manager, Scanner in) {
		try {
			assert (manager.isLoggedIn());
			LoggedInCommands loggedInCommand = LoggedInCommands.toLoggedInCommand(command);
			loggedInCommandInterpreter(manager, in, loggedInCommand);
		} catch (IllegalArgumentException e) {
			unknownCommand(in);
		}
	}

	/**
	 * Command interpreter for "user logged in" context. Assumes there's a
	 * {@link User} logged in
	 * 
	 * @param manager         {@link Manager} containing the most relevant data of
	 *                        the program
	 * @param in              {@link Scanner} that might contain additional user
	 *                        input
	 * @param loggedInCommand the {@link LoggedInCommands LoggedInCommand} to be run
	 */
	private static void loggedInCommandInterpreter(Manager manager, Scanner in, LoggedInCommands loggedInCommand) {
		assert (manager.isLoggedIn());
		switch (loggedInCommand) {
		case AJUDA:
			loggedInHelp(manager);
			break;
		case BOLEIA:
			takeRide(manager, in);
			break;
		case CONSULTA:
			consult(manager, in);
			break;
		case LISTA:
			list(manager, in);
			break;
		case NOVA:
			addTrip(manager, in);
			break;
		case REMOVE:
			remove(manager, in);
			break;
		case RETIRA:
			cancelRide(manager, in);
			break;
		case SAI:
			exit(manager);
			break;
		}
	}

	/**
	 * Cancels the {@link User current user's} taken ride on a given date. Assumes
	 * there's a {@link User} logged in
	 * 
	 * @param manager {@link Manager} containing the most relevant data of the
	 *                program
	 * @param in      {@link Scanner} to parse the given date
	 */
	private static void cancelRide(Manager manager, Scanner in) {
		try {
			assert (manager.isLoggedIn());
			String date = in.next();
			in.nextLine();
			manager.cancelCurrentUserRide(date);
			System.out.printf(USERNAME_RIDE_REMOVED, manager.getCurrentUserName());
		} catch (InvalidDateException | NoRideOnDayException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Prints the help messages associated with a {@link User} being logged in
	 * Assumes there's a {@link User} logged in
	 * 
	 * @param manager {@link Manager} containing the most relevant data of the
	 *                program
	 */
	private static void loggedInHelp(Manager manager) {
		assert (manager.isLoggedIn());
		for (LoggedInCommands command : LoggedInCommands.values()) {
			System.out.printf(command.getHelpMessage());
			System.out.println();
		}
	}

	/**
	 * Removes a {@link Trip} on a given date from {@link User current user}.
	 * Assumes there's a {@link User} logged in
	 * 
	 * @param manager {@link Manager} containing the {@link Trip} and {@link User}
	 *                whose {@link Trip} we want to remove
	 * @param in      {@link Scanner} containing the {@link String date} of the
	 *                {@link Trip trip} to be removed
	 */
	private static void remove(Manager manager, Scanner in) {
		try {
			String date = in.next();
			in.nextLine();
			manager.remove(date);
			System.out.println(TRIP_REMOVED);
		} catch (NoTripOnDayException | InvalidDateException | TripHasRidesException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Adds {@link User current user} as a {@link Trip ride} to the user
	 * correspondent to {@link String name}'s {@link Trip} on the given date
	 * 
	 * @param manager {@link Manager} containing the {@link User} whose {@link Trip}
	 *                we want to add {@link User current user} as a {@link Trip
	 *                ride} to
	 * @param in      {@link Scanner} containing the {@link Trip trip to be added}
	 *                details
	 */
	private static void takeRide(Manager manager, Scanner in) {
		String name = in.next();
		String date = in.next().trim();
		try {
			manager.addNewRide(name, date);
			System.out.println(RIDE_REGISTERED);
		} catch (InvalidDateException | NonExistentTripException | CantRideSelfException | TripIsFullException e) {
			System.out.println(e.getMessage());
		} catch (NonExistentUserException e) {
			System.out.println(INEXISTENT_USER);
		} catch (DateOccupiedException e) {
			System.out.printf(USERNAME_ALREADY_REGISTERED_TRIP_RIDE_ON_DATE, e.getMessage());
		}
	}

	/**
	 * Registers a new {@link Trip} on {@link User current user} of the given
	 * {@link Manager}. Assumes there's a {@link User} logged in
	 * 
	 * @param manager {@link Manager} in which the {@link Trip} is going to be
	 *                registered
	 * @param in      {@link Scanner} containing the {@link Trip trip to be added}
	 *                details
	 */
	private static void addTrip(Manager manager, Scanner in) {
		try {
			assert (manager.isLoggedIn());
			in.nextLine();
			String origin = in.nextLine();
			String destiny = in.nextLine();
			String date = in.next();
			String hourMinute = in.next();
			int duration = in.nextInt();
			int numberSeats = in.nextInt();
			in.nextLine();
			manager.addTrip(origin, destiny, date, hourMinute, duration, numberSeats);
			System.out.printf(TRIP_N_REGISTERED_THANKS_USERNAME, manager.getCurrentUserTripNumber(),
					manager.getCurrentUserName());
		} catch (InvalidTripDataException e) {
			System.out.println(e.getMessage());
		} catch (DateOccupiedException e) {
			System.out.printf(USERNAME_ALREADY_HAS_REGISTERED_TRIP_RIDE_ON_DATE, e.getMessage());
		}
	}

	/**
	 * Consults a {@link Trip trip} on a given date of a given {@link User current
	 * user} of {@link Manager}. Assume there's a {@link User} logged in
	 * 
	 * @param manager {@link Manager} in which the {@link Trip} is going to be
	 *                consulted
	 * @param in      {@link Scanner} which will contain the {@link Trip trip} to be
	 *                consulted date and {@link User} email
	 */
	private static void consult(Manager manager, Scanner in) {
		String email = in.next();
		String date = in.next().trim();
		in.nextLine();
		try {
			System.out.printf("%s", manager.consult(email, date).toString());
		} catch (NonExistentTripException | InvalidDateException e) {
			System.out.println(e.getMessage());
		} catch (NonExistentUserException e) {
			System.out.println(INEXISTENT_USER);
		}
	}

	/**
	 * Lists {@link Trip trips} according to the given listing mode
	 * 
	 * @param manager {@link Manager} in which the {@link Trip trips} are going to
	 *                be fetched and listed from
	 * @param in      {@link Scanner} which will contain the listing mode
	 */
	private static void list(Manager manager, Scanner in) {
		String listingMode = in.next();
		in.nextLine();
		try {
			if (listingMode.equalsIgnoreCase("minhas")) {
				listUserTrips(manager);
			} else if (listingMode.equalsIgnoreCase("boleias")) {
				listMailOriginDestinyDateTrips(manager.getCurrentUserRides());
			} else if (listingMode.equalsIgnoreCase("todas")) {
				listAllTrips(manager);
			} else {

				if (listingMode.matches(DATE_ALLOWED_PATTERN)) {
					listDateTrips(manager, listingMode);
				} else {
					listMailOriginDestinyDateTrips(manager.getUserTrips(listingMode));
				}

			}
		} catch (NoRegisteredTripsException | InvalidDateException e) {
			System.out.println(e.getMessage());
		} catch (NonExistentUserException e) {
			System.out.println(GIVEN_USER_NOT_EXISTS);
		}

	}

	/**
	 * Auxiliary method to list all {@link Trip trips} on the system |FORMAT: <code>
	 * dd-mm-yyyy userEmail%n
	 * </code>|
	 * 
	 * @param manager {@link Manager} in which the {@link Trip trips} are going to
	 *                be fetched and listed from
	 */
	private static void listAllTrips(Manager manager) {
		Iterator<SortedMap<String, Trip>> outerIterator = manager.getAllTrips();

		while (outerIterator.hasNext()) {
			Iterator<Trip> trips = outerIterator.next().values();
			while (trips.hasNext()) {
				Trip trip = (Trip) trips.next();
				System.out.printf("%s %s%n%n", trip.getBasicDateTime().toDateString(), trip.getDriverEmail());
			}
		}
	}

	/**
	 * Auxiliary method to list all {@link Trip trips} on the given date |FORMAT:
	 * <code>
	 * userEmail%n
	 * </code>|
	 * 
	 * @param manager {@link Manager} in which the {@link Trip trips} are going to
	 *                be fetched and listed from
	 * @param date    {@link String date} of the {@link Trip trips} we want to list
	 */
	private static void listDateTrips(Manager manager, String date)
			throws InvalidDateException, NoRegisteredTripsException {
		Iterator<TripWrapper> trips = manager.getTripsOnDate(date);
		boolean validTrips = false;
		if (trips != null) {
			while (trips.hasNext()) {
				TripWrapper trip = trips.next();
				if (trip.hasFreeSlots()) {
					validTrips = true;
					System.out.println(trip.getDriverEmail());
					System.out.println();
				}
			}
			if (!validTrips) {
				throw new NoRegisteredTripsException();
			}
		} else {
			throw new NoRegisteredTripsException();
		}
	}

	/**
	 * Auxiliary method to list all {@link Trip trips} given in the {@link Iterator}
	 * |FORMAT: <code>
	 * userEmail%n
	 * origin-destiny%n
	 * dd-mm-yyyy hh:mm duration%n%n
	 * </code>|
	 * 
	 * @param manager {@link Manager} in which the {@link Trip trips} are going to
	 *                be fetched and listed from
	 * @param date    {@link String date} of the {@link Trip trips} we want to list
	 */
	private static void listMailOriginDestinyDateTrips(Iterator<TripWrapper> iterator) {
		while (iterator.hasNext()) {
			TripWrapper trip = iterator.next();
			System.out.printf("%s%n%s-%s%n%s %d%n%n", trip.getDriverEmail(), trip.getOrigin(), trip.getDestiny(),
					trip.getBasicDateTime().toString(), trip.getDuration());
		}

	}

	/**
	 * Auxiliary method to list all of the given {@link User}'s {@link Trip trips}
	 * |FORMAT: <code>
	 * userEmail%n
	 * origin-destiny%n
	 * dd-mm-yyyy hh:mm duration%n%n
	 * </code>|
	 * 
	 * @param manager {@link Manager} in which the {@link Trip trips} are going to
	 *                be fetched and listed from
	 */
	private static void listUserTrips(Manager manager) throws NoRegisteredTripsException, NonExistentUserException {
		Iterator<TripWrapper> trips = manager.getUserTrips(manager.getCurrentUserEmail());

		while (trips.hasNext()) {
			System.out.println(trips.next().toString());
		}
	}

	/**
	 * Performs the logout of the <code>Current User</code> Assumes there's a
	 * {@link User} logged in
	 * 
	 * @param manager {@link Manager} in which the <code>Current User</code> is
	 *                logging out
	 */
	private static void exit(Manager manager) {
		assert (manager.isLoggedIn());
		System.out.printf(EXIT_MESSAGE_USERNAME, manager.logoutCurrentUser());
	}

	// GENERAL METHODS

	/**
	 * Shows a warning message when the user enters an unknown command and cleans
	 * any remainders (e.g.: arguments) of such command
	 *
	 * @param in {@link Scanner} that might contain bad input needing to be cleared
	 */
	private static void unknownCommand(Scanner in) {
		System.out.println(Commands.UNKNOWN.getWarningMessage());
		in.nextLine();
	}

}
