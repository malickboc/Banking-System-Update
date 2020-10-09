package transactions;

/**
 * Acts as a mediator between the view and model for accessing and updating
 * transactions.
 * 
 * @author CPS 240 Homework 3 Potential Solution
 *
 */
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

public class TransactionController {

	private TransactionDAO transactionDAO;

	/**
	 * Constructor initializing transactionDAO to existing connection.
	 * 
	 * @param transactionDAO: Provides access to transaction data.
	 */
	public TransactionController(TransactionDAO transactionDAO) {
		this.transactionDAO = transactionDAO;
	}

	/**
	 * Returns a given transaction given a transaction id.
	 * 
	 * @param transactionID: The transaction id.
	 * @return: The transaction.
	 * @throws FileNotFoundException:    Thrown if a connection to the database
	 *                                   could not be established.
	 * @throws IllegalArgumentException: If the transaction could not be found.
	 */
	public Transaction getTransaction(String transactionID) throws FileNotFoundException, IllegalArgumentException {
		return transactionDAO.getTransaction(transactionID);
	}

	/**
	 * Generates and returns a list of transactions for a given account.
	 * 
	 * @param accountNumber: The associated account.
	 * @return: The list of transactions for a given account.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	public List<Transaction> getTransactions(String accountNumber) throws FileNotFoundException {
		return transactionDAO.getTransactions(accountNumber);
	}

	/**
	 * Gets a range of transactions between a start and end date for a given
	 * account.
	 * 
	 * @param accountNumber: The account number.
	 * @param startDate:     The start date.
	 * @param endDate:       The end date.
	 * @return: A list of transactions between a start and end date for a given
	 *          account.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	public List<Transaction> getTransactionsBetween(String accountNumber, LocalDate startDate, LocalDate endDate)
			throws FileNotFoundException {
		return transactionDAO.getTransactionsBetween(accountNumber, startDate, endDate);
	}

}
