package accounts.table;

/**
 * Account table DAO representation.
 * 
 * @author: CPS 240 Homework 3 Potential Solution
 */
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import accounts.Account;
import user.User;

public interface AccountTableDAO {

	/**
	 * Associates an existing account to a user.
	 * 
	 * @param user:    The user.
	 * @param account: The account.
	 */
	public void addAccount(User user, Account account);

	/**
	 * Generates and returns a list of accounts the user has.
	 * 
	 * @param user: The user.
	 * @return: The list of accounts the user has.
	 * @throws FileNotFoundException:    : Thrown if a connection to the database
	 *                                   could not be established.
	 * @throws IllegalArgumentException: Thrown if an invalid account type is used.
	 */
	public List<Account> getAccounts(String user) throws FileNotFoundException, IllegalArgumentException;

}
