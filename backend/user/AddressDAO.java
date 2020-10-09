package user;

/**
 * Address DAO access class.
 * 
 * @author CPS 240 Homework 3 Potential Solution
 */
import java.io.FileNotFoundException;
import java.io.IOException;

public interface AddressDAO {

	/**
	 * Generates and returns an address representation for a given user.
	 * 
	 * @param driversLicense: The user's drivers license.
	 * @return: The address.
	 * @throws FileNotFoundException:    Thrown if a connection to the database
	 *                                   could not be established.
	 * @throws IllegalArgumentException: Thrown if the address does not exist.
	 */
	public Address getAddress(String driversLicense) throws FileNotFoundException, IllegalArgumentException;

	/**
	 * Adds an address to the address database.
	 * 
	 * @param driversLicense: The drivers license.
	 * @param streetOne:      The street line one.
	 * @param streetTwo:      The street line two.
	 * @param city:           The city.
	 * @param state:          The state.
	 * @param zip:            The zip.
	 * @return True if the address was added successfully. False otherwise.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 * @throws IllegalStateException: If an address already exists for that user.
	 */
	public boolean addAddress(String driversLicense, String streetOne, String streetTwo, String city, State state,
			String zip) throws FileNotFoundException, IllegalStateException;

	/**
	 * Removes an address for a user.
	 * 
	 * @param driversLicense: The drivers license.
	 * @return: True if the address was removed successfully. False otherwise.
	 * @throws IOException: Thrown if a connection to the database could not be
	 *                      established.
	 */
	public boolean removeAddress(String driversLicense) throws IOException;

}
