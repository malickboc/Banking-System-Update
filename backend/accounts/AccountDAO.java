package accounts;

/**
 * Account DAO access class.
 * 
 * @author CPS 240 Homework 3 Potential Solution
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;

import user.User;

public interface AccountDAO {

	/**
	 * Adds an account for a given user.
	 * 
	 * @param primaryUser: The user to associate the account with.
	 * @throws IllegalStateException: If the user already has an account of that
	 *                                type.
	 * @throws IOException:           Thrown if a connection to the database could
	 *                                not be established.
	 */
	public void addAccount(User primaryUser) throws IllegalStateException, IOException;

	/**
	 * Adds an account with an authorized user.
	 * 
	 * @param primaryUser:    The primary account holder.
	 * @param authorizedUser: The secondary account holder.
	 * @throws IllegalStateException: Thrown if either user does not meet age
	 *                                requirements or either has an existing account
	 *                                of that type.
	 * @throws IOException            Thrown if a connection to the database could
	 *                                not be established.
	 */
	public void addAccountWithAuthorizedUser(User primaryUser, User authorizedUser)
			throws IllegalStateException, IOException;

	/**
	 * Gets an account using the specific account dao implementation.
	 * 
	 * @param driversLicense: The drivers license of the user.
	 * @return: The account for that user.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	public Account getAccount(String driversLicense) throws FileNotFoundException;

	/**
	 * Updates a given account following a transaction.
	 * 
	 * @param accountNumber: The account number.
	 * @param balance:       The account balance.
	 * @throws IOException: Thrown if a connection to the database could not be
	 *                      established.
	 */
	public void updateAccount(String accountNumber, BigDecimal balance) throws IOException;

	/**
	 * Writes an existing account to the respective database.
	 * 
	 * @param account: The account
	 * @throws IllegalStateException: Thrown if an account with that ID already
	 *                                exists.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	public void writeAccountToDatabase(Account account) throws IllegalStateException, FileNotFoundException;

	/**
	 * Gets the next available unique account ID.
	 * 
	 * @return: The account ID.
	 * @throws IOException: Thrown if a connection to the database could not be
	 *                      established.
	 */
	public int getNextAccountID() throws IOException;
}
