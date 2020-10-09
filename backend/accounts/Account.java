package accounts;

/**
 * A representation of a generic bank account.
 * 
 * @author: CPS 240 Homework 3 Potential Solution
 */
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import accounts.table.AccountTableDAO;
import transactions.TransactionDAO;
import transactions.TransactionType;

public abstract class Account {

	AccountDAO accountDAO;
	AccountTableDAO accountTableDAO;
	TransactionDAO transactionDAO;
	String accountNumber;
	AccountType accountType;
	BigDecimal balance;

	/**
	 * Defines the basic representation of an account.
	 * 
	 * @param accountNumber: The account number.
	 * @param accountType:   The account type.
	 */
	public Account(String accountNumber, AccountType accountType) {
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.balance = BigDecimal.ZERO;
	}

	/**
	 * Used to reconstruct an account representation from a file.
	 * 
	 * @param accountNumber:  The account number.
	 * @param accountType:    The account type.
	 * @param balance:        The account balance.
	 * @param accountDAO:     Database connection to the accountDAO for updates.
	 * @param transactionDAO: Database connection to the transactionDAO for updates.
	 */
	public Account(String accountNumber, AccountType accountType, BigDecimal balance, AccountDAO accountDAO,
			TransactionDAO transactionDAO) {
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.balance = balance;
		this.accountDAO = accountDAO;
		this.transactionDAO = transactionDAO;
	}

	/**
	 * Deposits a specified amount into the account.
	 * 
	 * @param amountToDeposit: The amount to deposit.
	 * @return: The account balance.
	 * @throws IOException: Thrown if a database connection could not be
	 *                      established.
	 */
	public abstract BigDecimal deposit(String amountToDeposit) throws IOException;

	/**
	 * Withdraws a specified amount into the account.
	 * 
	 * @param amountToWithdraw: The amount to withdraw.
	 * @return: The account balance.
	 * @throws IOException: Thrown if a database connection could not be
	 *                      established.
	 */
	public abstract BigDecimal withdraw(String amountToWithdraw) throws IOException;
	
	/**
	 * Updates the account with a new balance.
	 * 
	 * @param validAmount: A validated amount.
	 * @throws IOException: Thrown if a connection to the database could not be
	 *                      established.
	 */
	void updateAccount(BigDecimal validAmount) throws IOException {
		this.setBalance(validAmount);
		this.accountDAO.updateAccount(this.getAccountNumber(), this.getBalance());
	}

	/**
	 * Writes a transaction to the transactionDAO.
	 * 
	 * @param type:   The type of transaction.
	 * @param amount: The amount of the transaction.
	 * @throws IOException: Thrown if a connection to the database could not be
	 *                      established.
	 */
	void writeTransaction(TransactionType type, String amount) throws IOException {
		this.transactionDAO.writeTransaction(this.getAccountNumber(), type, amount,
				this.getBalance().toPlainString(), LocalDate.now());
	}
	
	/**
	 * True if the new balance is below zero. False otherwise.
	 * 
	 * @param newBalance: The new account balance.
	 */
	boolean newBalanceIsBelowZero(BigDecimal newBalance) {
		return newBalance.compareTo(BigDecimal.ZERO) == -1;
	}

	@Override
	public String toString() {
		return String.format("%s,%s", accountNumber, balance.toPlainString());
	}

	static boolean isAmountValid(String amount) {
		return amount.matches("^\\d+\\.\\d{2}$");
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
