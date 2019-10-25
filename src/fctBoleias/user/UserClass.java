package fctBoleias.user;

import basicDate.BasicDate;
import dataStructures.SortedMap;
import fctBoleias.trip.Trip;

public class UserClass implements User {
	
		// Instance variables containing the users's details and information
		private String email;
		private String name;
		private String password;
		//private Map<LocalDateTime, List<Trip>> tripsByDate; // User's trips by date
		private int nVisits;
		private SortedMap<BasicDate, Trip> rides; // BOLEIAS EM QUE PARTICIPA MAS ¬OWNER
		private SortedMap<BasicDate, Trip> trips; // USER'S (this) TRIPS
		
		/**
		 * User object constructor
		 * Creates an object holding details and information about a user
		 * 
		 * @param email - email of the user
		 * @param name - name of the user
		 * @param password - password of the user
		 */
		public UserClass(String email, String name, String password) {
			this.email = email;
			this.name = name;
			this.password = password;
			this.nVisits = 0;
		}

		/**
		 * @return the email
		 */
		public String getEmail() {
			return email;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the password
		 */
		public String getPassword() {
			return password;
		}

		/**
		 * @return the nVisits
		 */
		public int getnVisits() {
			return nVisits;
		}

	
}
