import java.util.Scanner;

import fctBoleias.CantRideHimselfException;
import fctBoleias.DateOccupiedException;
import fctBoleias.InvalidDataException;
import fctBoleias.InvalidDateException;
import fctBoleias.Manager;
import fctBoleias.ManagerClass;
import fctBoleias.NoTripOnDayException;
import fctBoleias.NotLoggedInException;
import fctBoleias.TripHasRidesException;
import fctBoleias.TripNotExistsException;
import fctBoleias.User;
import fctBoleias.UserNotExistException;

public class Main {

	private static final String DEFAULT_PROMPT = "> ";

	private static final String UNKNOWN_COMMAND = "Comando inexistente.";

	// Register messages
	private static final String REGISTRATION_SUCCESSFUL = "Registo efetuado.";
	private static final String REGISTRATION_FAILED = "Registo nao efetuado.";
	private static final String USER_ALREADY_EXISTS = "Utilizador ja existente.";
	private static final String ASK_NAME_REGISTER = "nome (maximo 50 caracteres): ";
	private static final String ASK_PW_REGISTER = "password (entre 3 e 5 caracteres - digitos e letras): ";
	private static final String INVALID_PASSWORD = "Password incorrecta.";

	// Login messages
	private static final String ASK_PW_LOGIN = "password: ";
	private static final String USER_DOESNT_EXIST = "Utilizador nao existente.";

	// Password requirements
	private static final int MAX_PASSWORD_ATTEMPTS = 3;
	private static final int MIN_PW_LIMIT = 3; // minimum number of characters in a password
	private static final int MAX_PW_LIMIT = 5; // maximum number of characters in a password

	// Ride related messages
	private static final String NO_REGISTERED_RIDES = "%s nao tem deslocacoes registadas.%n";
	private static final String NO_REGISTERED_RIDES_IN_DATE = " nao existem deslocacoes registadas para ";
	private static final String RIDE_NOT_REGISTERED = "Deslocacao nao registada.";
	private static final String RIDE_REGISTERED_THANKS = "Deslocacao registada. Obrigado ";
	private static final String RIDE_REGISTERED = "Boleia registada.";
	private static final String REGISTERED_RIDES = "Boleias registadas: ";
	private static final String FULL_RIDE_CAPACITY = " nao existe lugar. Boleia nao registada.";
	private static final String AVAILABLE_RIDE_CAPACITY = "Lugares vagos: ";
	private static final String OWN_RIDE_FAIL = " nao pode dar boleia a si propria. Boleia nao registada.";
	private static final String RIDE_DOESNT_EXIST = "Deslocacao nao existe.";
	private static final String NON_EXISTENT_USER = "Utilizador inexistente.";
	private static final String INVALID_DATE = "Data invalida.";
	private static final String RIDE_REMOVED = "Deslocacao removida.";
	private static final String INVALID_DATA = "Dados invalidos."; // Invalid data when registering new boleia

	private static final String FINAL_MESSAGE = "Obrigado. Ate a proxima.";

	/**
	 * {@link Enum} containing all of the possible commands
	 * and warning messages where applicable.
	 */
	private enum Commands {
		AJUDA, TERMINA, REGISTA, ENTRADA, SAI, NOVA, LISTA, BOLEIA, CONSULTA, REMOVE, RETIRA, UNKNOWN("Comando invalido.");

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
		 * {@link Commands.UNKNOWN} in case there's no other valid match
		 */
		private static Commands getCommand(Scanner in) {
			try {
				assert(in.hasNextLine());
				String command = in.nextLine().toUpperCase();
				return Commands.valueOf(command);
			} catch (IllegalArgumentException e) {
				return Commands.UNKNOWN;
			}
		}

	}

	/**
	 * {@link Enum} containing the commands that can be executed
	 * when there is no {@link User} logged in and its corresponding help messages.
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
		 * Converts a {@link Commands} type command into {@link LoggedOutCommands}
		 * if such is possible, otherwise returns {@link IllegalArgumentException}
		 * @param command {@link Commands} type to be converted
		 * @return the converted command
		 * @throws IllegalArgumentException if it is not possible to convert the given command
		 */
		public static LoggedOutCommands toLoggedOutCommand(Commands command) throws IllegalArgumentException {
			return LoggedOutCommands.valueOf(command.name());
		}

	}

	/**
	 * {@link Enum} containing the commands that can be executed
	 * when there is a {@link User} logged in and its corresponding help messages.
	 */
	private enum LoggedInCommands {
		AJUDA("ajuda - Mostra os comandos existentes"),
		SAI("sai - Termina a sessao deste utilizador no programa"),
		NOVA("nova - Regista uma nova deslocacao"),
		LISTA("lista - Lista todas ou algumas deslocacoes registadas"),
		BOLEIA("consulta - Lista a informacao de uma dada deslocacao"),
		CONSULTA("consulta - Lista a informacao de uma dada deslocacao"),
		RETIRA("retira - Retira uma dada boleia"),
		REMOVE("remove - Retira uma dada deslocacao");

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
		 * Converts a {@link Commands} type command into {@link LoggedInCommands}
		 * if such is possible, otherwise returns {@link IllegalArgumentException}
		 * @param command {@link Commands} type to be converted
		 * @return the converted command
		 * @throws IllegalArgumentException if it is not possible to convert the given command
		 */
		public static LoggedInCommands toLoggedInCommand(Commands command) throws IllegalArgumentException {
			return LoggedInCommands.valueOf(command.name());
		}

	}

	// MENU RELATED METHODS

	/**
	 * Starts the execution of the program FCTBoleias,
	 * resuming its status from the previous execution
	 * and saves it when the user chooses to exit.
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Manager manager = new ManagerClass();
		Commands command;
		do {
			// TODO prompt shit
			//System.out.printf("%s %s", manager.getCurrentUserEmail(), DEFAULT_PROMPT);
			command = Commands.getCommand(in);
			if (manager.isLoggedIn()) {
				executeLoggedInCommand(command, manager, in);
			} else {
				executeLoggedOutCommand(command, manager, in);
			}
		} while (manager.isLoggedIn() || !(command.equals(Commands.TERMINA)));
		in.close();
	}

	/**
	 * Attempts to execute the given {@link Commands command} assumimng there is no user logged in,
	 * and prints a warning in case this attempt fails.
	 * @param command {@link Commands Command} to be executed
	 * @param manager {@link Manager} containing the most relevant data of the program
	 * @param in {@link Scanner} that might contain adicional user input
	 */
	private static void executeLoggedOutCommand(Commands command, Manager manager, Scanner in) {
		try {
			assert(manager.isLoggedIn());
			LoggedOutCommands loggedOutCommand = LoggedOutCommands.toLoggedOutCommand(command);
			loggedOutCommandInterpreter(manager, in, loggedOutCommand);
		} catch (IllegalArgumentException e) {
			unknownCommand(in);
		}
	}

	// NOT LOGGED IN METHODS

	/**
	 * Command interpreter when no user is logged in
	 * TODO
	 * @param manager - manager object
	 * @param in      - scanner to get user input
	 * @param loggedOutCommand
	 */
	private static void loggedOutCommandInterpreter(Manager manager, Scanner in, LoggedOutCommands loggedOutCommand) {
		switch (loggedOutCommand) {
		case AJUDA:
			loggedOutHelp(manager);
			break;
		case ENTRADA:
			break;
		case REGISTA:
			break;
		case TERMINA:
			processEnd(manager);
			break;
		}
	}

	/**
	 * Prints the help messages associated with a {@link User} not being logged in
	 * @param manager TODO
	 */
	private static void loggedOutHelp(Manager manager) {
		assert(!manager.isLoggedIn());
		for (LoggedOutCommands command : LoggedOutCommands.values()) {
			System.out.printf(command.getHelpMessage());
		}
	}

	/**
	 * Prints the ending of program message
	 * (should only run when there's no {@link User} logged in)
	 * @param manager TODO
	 */
	private static void processEnd(Manager manager) {
		assert(!manager.isLoggedIn());
		System.out.println(FINAL_MESSAGE);
	}

	// LOGGED IN METHODS
	
	/**
	 * Attempts to execute the given {@link Commands command} assumimng there is no user logged in,
	 * and prints a warning in case this attempt fails.
	 * @param command {@link Commands Command} to be executed
	 * @param manager {@link Manager} containing the most relevant data of the program
	 * @param in {@link Scanner} that might contain adicional user input
	 */
	private static void executeLoggedInCommand(Commands command, Manager manager, Scanner in) {
		try {
			assert(manager.isLoggedIn());
			LoggedInCommands loggedInCommand = LoggedInCommands.toLoggedInCommand(command);
			loggedInCommandInterpreter(manager, in, loggedInCommand);
		} catch (IllegalArgumentException e) {
			unknownCommand(in);
		}
	}

	/**
	 * Command interpreter when there's a user logged in
	 * TODO
	 * @param manager - manager object
	 * @param in      - scanner to get user input
	 * @param loggedInCommand
	 *
	 */
	private static void loggedInCommandInterpreter(Manager manager, Scanner in, LoggedInCommands loggedInCommand) {
		switch (loggedInCommand) {
		case AJUDA:
			loggedInHelp(manager);
			break;
		case BOLEIA:
			takeRide(manager, in);
			break;
		case CONSULTA:
			break;
		case LISTA:
			break;
		case NOVA:
			addTrip(manager, in);
			break;
		case REMOVE:
			remove(manager, in);
			break;
		case RETIRA:
			break;
		case SAI:
			break;
		}
	}

	/**
	 * Prints the help messages associated with a {@link User} being logged in
	 * @param manager TODO
	 */
	private static void loggedInHelp(Manager manager) {
		assert(manager.isLoggedIn());
		for (LoggedInCommands command : LoggedInCommands.values()) {
			System.out.printf(command.getHelpMessage());
		}
	}

	/**
	 * TODO
	 * @param manager
	 * @param in
	 */
	private static void remove(Manager manager, Scanner in) {
		String date = in.nextLine();
		in.nextLine();
		try {
			manager.remove(date);
			System.out.println(RIDE_REMOVED);
		} catch (NotLoggedInException | NoTripOnDayException | InvalidDateException | TripHasRidesException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * TODO
	 * @param manager
	 * @param in
	 */
	private static void takeRide(Manager manager, Scanner in) {
		String name = in.next();
		String date = in.next().trim();
		try {
			manager.addNewRide(name, date);
			System.out.println(RIDE_REGISTERED);
		} catch (NotLoggedInException | UserNotExistException
				| InvalidDateException | TripNotExistsException e) {
			System.out.println(e.getMessage());
		} catch (CantRideHimselfException | DateOccupiedException e) {
			try {
				System.out.printf(e.getMessage(), manager.getCurrentUserName());
			} catch (NotLoggedInException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Registers a new trip on current {@link User}
	 * TODO
	 * @param manager - manager class
	 * @param in      - Scanner
	 */
	private static void addTrip(Manager manager, Scanner in) {
		String origin = in.nextLine();
		String destiny = in.nextLine();
		String date = in.next();
		String hourMinute = in.next();
		int duration = in.nextInt();
		int numberSeats = in.nextInt();
		in.nextLine();
		try {
			manager.addNewTrip(origin, destiny, date, hourMinute, duration, numberSeats);
		} catch (NotLoggedInException | InvalidDataException e) {
			System.out.println(e.getMessage());
		} catch (DateOccupiedException e) {
			try {
				System.out.printf(e.getMessage(), manager.getCurrentUserName());
			} catch (NotLoggedInException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	// GENERAL METHODS

	/**
	 * Shows a warning message when the user enters an unknown command
	 * and cleans any remainers (e.g.: arguments) of such command
	 *
	 * @param in {@link Scanner} that might contain bad input needing to be cleared
	 */
	private static void unknownCommand(Scanner in) {
		System.out.println(Commands.UNKNOWN.getWarningMessage());
		in.nextLine();
	}

}
