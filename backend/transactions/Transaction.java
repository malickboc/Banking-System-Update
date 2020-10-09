package transactions;

/**
 * Representation of a transaction.
 * 
 * @author: CPS 240 Homework 3 Potential Solution
 */
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transaction {

	int transactionID;
	String accountNumber;
	TransactionType type;
	String amount;
	String accountBalance;
	LocalDate dateOfTransaction;

	/**
	 * Generates a transaction representation.
	 * 
	 * @param transactionID:  The transaction id.
	 * @param accountNumber:  The associated account number.
	 * @param type:           The type of transaction.
	 * @param amount:         The amount of the transaction.
	 * @param accountBalance: The resulting account balance.
	 * @param localDate:      The date of the transaction.
	 */
	public Transaction(int transactionID, String accountNumber, TransactionType type, String amount,
			String accountBalance, LocalDate localDate) {
		this.transactionID = transactionID;
		this.accountNumber = accountNumber;
		this.type = type;
		this.amount = amount;
		this.accountBalance = accountBalance;
		this.dateOfTransaction = localDate;
	}

	@Override
	public String toString() {
		return String.format("%s,%s,%s,%s,%s,%s", transactionID, accountNumber, type, amount, accountBalance,
				dateOfTransaction);
	}

	public int getTransactionID() {
		return transactionID;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public TransactionType getType() {
		return type;
	}

	public String getAmount() {
		return amount;
	}

	public String getAccountBalance() {
		return accountBalance;
	}

	public LocalDate getDateOfTransaction() {
		return dateOfTransaction;
	}

}
