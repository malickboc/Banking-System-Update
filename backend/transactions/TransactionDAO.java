package transactions;

/**
 * Transaction DAO access class.
 * 
 * @author CPS 240 Homework 3 Potential Solution
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionDAO {

	/**
	 * Returns a given transaction given a transaction id.
	 * 
	 * @param transactionID: The transaction id.
	 * @return: The transaction.
	 * @throws FileNotFoundException:    Thrown if a connection to the database
	 *                                   could not be established.
	 * @throws IllegalArgumentException: If the transaction could not be found.
	 */
	public Transaction getTransaction(String transactionID) throws FileNotFoundException, IllegalArgumentException;

	/**
	 * Generates and returns a list of transactions for a given account.
	 * 
	 * @param accountNumber: The associated account.
	 * @return: The list of transactions for a given account.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	public List<Transaction> getTransactions(String accountNumber) throws FileNotFoundException;

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
			throws FileNotFoundException;

	/**
	 * Writes a transaction to the transaction database.
	 * 
	 * @param accountNumber: The account number.
	 * @param type:          The transaction type.
	 * @param amount:        The transaction amount.
	 * @param balance:       The account balance.
	 * @param now:           The current date.
	 * @throws IOException: Thrown if a connection to the database could not be
	 *                      established.
	 */
	public void writeTransaction(String accountNumber, TransactionType type, String amount, String balance,
			LocalDate now) throws IOException;

	/**
	 * Generates and returns the next available transaction ID.
	 * 
	 * @return: The next available transaction ID.
	 * @throws IOException: Thrown if a connection to the database could not be
	 *                      established.
	 */
	public int getNextTransactionID() throws IOException;

}
