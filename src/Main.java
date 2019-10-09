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
	 * Enum for user commands Each command is associated with its message in the
	 * help menu
	 *
	 * @author Joao Bordalo
	 *
	 */
	private enum GlobalCommand {
		TERMINA("termina - Termina a execucao do programa"),
		REGISTA("regista - Regista um novo utilizador no programa"),
		ENTRADA("entrada - Permite a entrada (\"login\") dum utilizador no programa"),
		AJUDA("ajuda - Mostra os comandos existentes"), SAI("sai - Termina a sessao deste utilizador no programa"),
		UNKNOWN("");

		private final String helpMessage; // Associates each command with its help message

		private GlobalCommand(String helpMessage) {
			this.helpMessage = helpMessage;
		}

		public String getHelpMessage() {
			return helpMessage;
		}

	}

	private enum LoggedInCommand {
		NOVA("nova - Regista uma nova deslocacao"), LISTA("lista - Lista todas ou algumas deslocacoes registadas"),
		BOLEIA("boleia - Regista uma boleia para uma dada deslocacao"),
		CONSULTA("consulta - Lista a informacao de uma dada deslocacao"), REMOVE("remove - Retira uma dada deslocacao"),
		AJUDA("ajuda - Mostra os comandos existentes"), SAI("sai - Termina a sessao deste utilizador no programa"),
		UNKNOWN("");

		private final String helpMessage; // Associates each command with its help message

		private LoggedInCommand(String helpMessage) {
			this.helpMessage = helpMessage;
		}

		public String getHelpMessage() {
			return helpMessage;
		}

	}

	// MENU RELATED METHODS

	/**
	 * main method containing the essential local variables for the program
	 * execution It is also responsible for selecting how to process the user's
	 * input
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Manager manager = new ManagerClass();

		GlobalCommand cmd = getGlobalCommand(in);
		
		// TODO Fix with enum thing
		
		do {

			if (manager.isLoggedIn()) {
				loggedInCommandInterpreter(manager, in);
			} else {
				globalCommandInterpreter(manager, in);
			}

		} while (manager.isLoggedIn() || !(cmd.equals(GlobalCommand.TERMINA)));

		in.close();
	}

	// NOT LOGGED IN METHODS

	/**
	 * Command interpreter when no user is logged in
	 * 
	 * @param manager - manager object
	 * @param in      - scanner to get user input
	 */
	private static void globalCommandInterpreter(Manager manager, Scanner in) {
		GlobalCommand cmd = getGlobalCommand(in);
		switch (cmd) {
		case AJUDA:
			globalProcessHelp();
			break;
		case TERMINA:
			processEnd();
			break;
		case REGISTA:
			processRegister(manager, in);
			break;
		case ENTRADA:
			processLogin(manager, in);
			break;
		default:
			processUnknownCommand(in);
			break;
		}
	}

	private static void processLogin(Manager manager, Scanner in) {
		
		String name = in.nextLine().trim();
		
		
		
	}

	private static void processRegister(Manager manager, Scanner in) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets a user global command
	 *
	 * @param in - scanner
	 * @return Command.valueOf(command) or Command.UNKNOWN
	 */
	private static GlobalCommand getGlobalCommand(Scanner in) {
		try {
			System.out.printf(DEFAULT_PROMPT);
			String command = in.nextLine().toUpperCase();
			return GlobalCommand.valueOf(command);
		} catch (IllegalArgumentException e) {
			return GlobalCommand.UNKNOWN;
		}
	}

	/**
	 * Gets a user logged in command
	 *
	 * @param in - scanner
	 * @return Command.valueOf(command) or Command.UNKNOWN
	 */
	private static LoggedInCommand getLoggedInCommand(Scanner in, Manager manager) {
		try {
			// TODO
			// System.out.printf("%s %s", manager.getCurrentUserEmail(), DEFAULT_PROMPT);
			String command = in.nextLine().toUpperCase();
			return LoggedInCommand.valueOf(command);
		} catch (IllegalArgumentException e) {
			return LoggedInCommand.UNKNOWN;
		}
	}

	/**
	 * Prints the help message associated with a user not being logged in
	 *
	 * @pre: !manager.isLoggedIn()
	 */
	private static void globalProcessHelp() {
		// If there isn't a user logged in
		for (GlobalCommand command : GlobalCommand.values()) {
			System.out.printf(command.getHelpMessage());
		}

	}

	/**
	 * Prints the ending of program message
	 *
	 * @pre: !manager.isLoggedIn()
	 */
	private static void processEnd() {
		System.out.println(FINAL_MESSAGE);
	}

	// LOGGED IN METHODS

	/**
	 * Command interpreter when there's a user logged in
	 * 
	 * @param manager - manager object
	 * @param in      - scanner to get user input
	 *
	 * @pre: manager.isLoggedIn()
	 */
	private static void loggedInCommandInterpreter(Manager manager, Scanner in) {
		LoggedInCommand cmd = getLoggedInCommand(in, manager);
		switch (cmd) {
		case AJUDA:
			loggedInProcessHelp();
			break;
		case SAI:
			processLeave(manager);
			break;
		case NOVA:
			processNew(manager, in);
			break;
		case LISTA:
			processList(manager, in);
			break;
		case BOLEIA:
			processTakeRide(manager, in);
			break;
		case CONSULTA:
			processConsult(manager, in);
			break;
		case REMOVE:
			processRemove(manager, in);
			break;
		default:
			processUnknownCommand(in);
			break;
		}
	}

	private static void processRemove(Manager manager, Scanner in) {
		
		String date = in.nextLine();
		in.nextLine();

		try {
			manager.remove(date);
			System.out.println(RIDE_REMOVED);
		} catch (NotLoggedInException | NoTripOnDayException | InvalidDateException e) {
			System.out.println(e.getMessage());
		} catch (TripHasRidesException e) {
			try {
				System.out.printf(e.getMessage(), manager.getCurrentUserName());
			} catch (NotLoggedInException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private static void processConsult(Manager manager, Scanner in) {
		// TODO Auto-generated method stub

	}

	private static void processTakeRide(Manager manager, Scanner in) {
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

	private static void processList(Manager manager, Scanner in) {
		// TODO Auto-generated method stub

	}

	/**
	 * Registers a new trip on current {@link User}
	 * 
	 * @param manager - manager class
	 * @param in      - Scanner
	 */
	private static void processNew(Manager manager, Scanner in) {
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

	private static void processLeave(Manager manager) {
		// TODO Auto-generated method stub

	}

	/**
	 * Prints the help message associated with a user being logged in
	 *
	 * @pre: manager.isLoggedIn()
	 */
	private static void loggedInProcessHelp() {
		// If there is a user logged in
		for (LoggedInCommand command : LoggedInCommand.values()) {
			System.out.printf(command.getHelpMessage());
		}

	}

	// GENERAL METHODS

	/**
	 * Shows a warning message when the user enters an unknown command
	 *
	 * @param in - Scanner for running in.nextLine() and skipping any bad input
	 */
	private static void processUnknownCommand(Scanner in) {
		System.out.println(UNKNOWN_COMMAND);
		in.nextLine();
	}

}
