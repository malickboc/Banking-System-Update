package user;

/**
 * Acts as a mediator between the view and model for accessing and updating
 * users.
 * 
 * @author CPS 240 Homework 3 Potential Solution
 *
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public class UserController {

	private UserDAO userDAO;

	/**
	 * Constructor initializing userDAO to existing connection.
	 * 
	 * @param userDAO: Provides access to account data.
	 */
	public UserController(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * Generates and returns a user representation.
	 * 
	 * @param driversLicense: The drivers license.
	 * @return: A user.
	 * @throws FileNotFoundException:    Thrown if a connection to the database
	 *                                   could not be established.
	 * @throws IllegalArgumentException: Thrown if the user does not exist.
	 */
	public User getUser(String driversLicense) throws FileNotFoundException, IllegalArgumentException {
		return userDAO.getUser(driversLicense);
	}

	/**
	 * Adds a user to the user database.
	 * 
	 * @param driversLicense: The drivers license.
	 * @param firstName:      The first name.
	 * @param middleName:     the middle name.
	 * @param lastName:       The last name.
	 * @param dateOfBirth:    The date of birth.
	 * @param driversLicense: The user's drivers license.
	 * @param streetOne:      The street line one.
	 * @param streetTwo:      The street line two.
	 * @param city:           The city.
	 * @param state:          The state.
	 * @param zip:            The zip.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 * @throws IllegalStateException: If the user already exists.
	 */
	public void addUser(String driversLicense, String firstName, String middleName, String lastName, String dateOfBirth,
			String streetOne, String streetTwo, String city, String state, String zip)
			throws FileNotFoundException, IllegalStateException {
		this.userDAO.addUser(driversLicense, firstName, middleName, lastName, dateOfBirth, streetOne, streetTwo, city,
				state, zip);
	}

	/**
	 * Removes a user.
	 * 
	 * @param driversLicense: The drivers license.
	 * @return: True if the user was removed successfully. False otherwise.
	 * @throws IOException: Thrown if a connection to the database could not be
	 *                      established.
	 */
	public boolean removeUser(String driversLicense) throws IOException {
		return this.userDAO.removeUser(driversLicense);
	}
}
