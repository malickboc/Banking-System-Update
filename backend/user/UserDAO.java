package user;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public interface UserDAO {

	/**
	 * Generates and returns a user representation.
	 * 
	 * @param driversLicense: The drivers license.
	 * @return: A user.
	 * @throws FileNotFoundException:    Thrown if a connection to the database
	 *                                   could not be established.
	 * @throws IllegalArgumentException: Thrown if the user does not exist.
	 */
	public User getUser(String driversLicense) throws FileNotFoundException, IllegalArgumentException;

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
			throws FileNotFoundException, IllegalStateException;

	/**
	 * Removes a user.
	 * 
	 * @param driversLicense: The drivers license.
	 * @return: True if the user was removed successfully. False otherwise.
	 * @throws IOException: Thrown if a connection to the database could not be
	 *                      established.
	 */
	public boolean removeUser(String driversLicense) throws IOException;

}
