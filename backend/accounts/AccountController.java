package accounts;

/**
 * Acts as a mediator between the view and model for accessing and updating
 * accounts.
 * 
 * @author CPS 240 Homework 3 Potential Solution
 *
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import accounts.table.AccountTableDAO;
import accounts.table.AccountTableDAOImpl;
import transactions.TransactionDAOImpl;
import user.User;

public class AccountController {

	AccountDAO accountDAO;
	AccountTableDAO accountTableDAO;

	/**
	 * Constructor initializing accountDAO and accountTableDAO to existing
	 * connections.
	 * 
	 * @param accountDAO:      Provides access to account data.
	 * @param accountTableDAO: Provides access to the accounts a user has.
	 */
	public AccountController(AccountDAO accountDAO, AccountTableDAO accountTableDAO) {
		this.accountDAO = accountDAO;
		this.accountTableDAO = accountTableDAO;
	}

	/**
	 * Constructor initializing accountDAO to existing connection. A supplemental
	 * account table connection is generated if one is not provided.
	 * 
	 * @param accountDAO:      Provides access to account data.
	 * @param accountTableDAO: Provides access to the accounts a user has.
	 */
	public AccountController(AccountTableDAO accountTableDAO) {
		this.accountTableDAO = accountTableDAO;
		this.accountTableDAO = new AccountTableDAOImpl(new TransactionDAOImpl());
	}

	/**
	 * Mediates control flow between the view and model to add an account for a
	 * user.
	 * 
	 * @param primaryUser: The user who is having an account added.
	 * @throws IllegalStateException: If the user does not meet the qualifications
	 *                                to open an account.
	 * @throws IOException:           Thrown if a connection to the database could
	 *                                not be established.
	 */
	public void addAccount(User primaryUser) throws IllegalStateException, IOException {
		this.accountDAO.addAccount(primaryUser);
	}

	/**
	 * Mediates control flow between the view and model to add an account for a user
	 * and authorized user.
	 * 
	 * @param primaryUser:    The user who is having an account added as primary.
	 * @param authorizedUser: The user who is being added as a secondary user.
	 * @throws IllegalStateException:  If the user does not meet the qualifications
	 *                                 to open an account.
	 * @throws IOExceptionIOException: Thrown if a connection to the database could
	 *                                 not be established.
	 */
	public void addAccount(User primaryUser, User authorizedUser) throws IllegalStateException, IOException {
		this.accountDAO.addAccountWithAuthorizedUser(primaryUser, authorizedUser);
	}

	/**
	 * Returns an account for the user using the respective DAO.
	 * 
	 * @param driversLicense: The drivers license of the user.
	 * @return: The account representation
	 * @throws FileNotFoundException:Thrown if a connection to the database could
	 *                                      not be established.
	 */
	public Account getAccount(String driversLicense) throws FileNotFoundException {
		return accountDAO.getAccount(driversLicense);
	}

	/**
	 * Returns a list of accounts for a user using the account table mapping.
	 * 
	 * @param driversLicense: The drivers license of the user.
	 * @return: A list of accounts for that user.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	public List<Account> getAccounts(String driversLicense) throws FileNotFoundException {
		return accountTableDAO.getAccounts(driversLicense);
	}

	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public AccountTableDAO getAccountTableDAO() {
		return accountTableDAO;
	}

	public void setAccountTableDAO(AccountTableDAO accountTableDAO) {
		this.accountTableDAO = accountTableDAO;
	}

}
