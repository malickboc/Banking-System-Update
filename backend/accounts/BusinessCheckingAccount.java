package accounts;

/**
 * Business checking account representation for an account.
 * 
 * @author CPS 240 Homework 3 Potential Solution
 */

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import transactions.TransactionDAO;
import transactions.TransactionType;

public class BusinessCheckingAccount extends Account {

	/*
	 * The penalty for overdrafting an account.
	 */
	private static final BigDecimal OVERDRAFT_FEE = new BigDecimal("35.00");
	/*
	 * The maximum amount the account can be overdraft to.
	 */
	private static final BigDecimal OVERDRAFT_MAXIMUM = new BigDecimal("-7500.00");
	/*
	 * The monthly fee charged to the account.
	 */
	private static final BigDecimal MONTHLY_FEE = new BigDecimal("25.00");
	/*
	 * The minimum account balance to waive the monthly fee.
	 */
	private static final BigDecimal MONTHLY_FEE_WAIVED_MINIMUM_BALANCE = new BigDecimal("2500.00");
	/*
	 * The number of transactions allowed before fees apply.
	 */
	private static final int TRANSACTION_FEE_LIMIT_BEFORE_CHARGE = 100;
	/*
	 * The charge per transaction if the number of transactions excceeds the
	 * transaction fee limit before charge.
	 */
	private static final BigDecimal TRANSACTION_FEE = new BigDecimal("0.25");

	private boolean canOverdraft;
	private int monthlyTransactionCount;

	/**
	 * Defines the basic representation of a business checking account.
	 * 
	 * @param accountNumber: The initial account number assigned to the account.
	 */
	public BusinessCheckingAccount(String accountNumber) {
		super(accountNumber, AccountType.BUSINESS_CHECKING_ACCOUNT);
	}

	/**
	 * Used to reconstruct a business checking account representation from a file.
	 * 
	 * @param accountNumber:  The account number.
	 * @param type:           The account type.
	 * @param balance:        The account balance.
	 * @param canOverdraft:   If the account can overdraft.
	 * @param accountDAO:     Database connection to the accountDAO for updates.
	 * @param transactionDAO: Database connection to the transactionDAO for updates.
	 */
	public BusinessCheckingAccount(String accountNumber, AccountType type, BigDecimal balance, boolean canOverdraft,
			AccountDAO accountDAO, TransactionDAO transactionDAO) {
		super(accountNumber, type, balance, accountDAO, transactionDAO);
		this.canOverdraft = canOverdraft;
	}

	@Override
	public BigDecimal deposit(String amountToDeposit) throws IOException {
		if (!Account.isAmountValid(amountToDeposit)) {
			throw new IllegalArgumentException("Amount not valid: " + amountToDeposit);
		}
		BigDecimal validAmount = new BigDecimal(amountToDeposit);
		BigDecimal newAmount = super.getBalance().add(validAmount);
		super.updateAccount(newAmount);
		super.writeTransaction(TransactionType.DEPOSIT, amountToDeposit);
		return super.getBalance();
	}

	@Override
	public BigDecimal withdraw(String amountToWithdraw) throws IOException {
		if (!Account.isAmountValid(amountToWithdraw)) {
			throw new IllegalArgumentException("Amount not valid: " + amountToWithdraw);
		}
		BigDecimal validAmount = new BigDecimal(amountToWithdraw);
		BigDecimal newBalance = super.getBalance().subtract(validAmount);
		if (this.accountCannotOverdraftAndBalanceBelowZero(newBalance)) {
			throw new IllegalArgumentException("Account cannot be overdrawn");
		}
		if (this.accountExceedsOverdraftMaximum(newBalance)) {
			throw new IllegalArgumentException("Withdraw cannot exceed account overdraft maximum: " + newBalance);
		}
		super.updateAccount(newBalance);
		super.writeTransaction(TransactionType.WITHDRAW, amountToWithdraw);
		this.monthlyTransactionCount++;

		this.applyTransactionFee();
		this.applyOverDraftFee();
		return super.getBalance();
	}

	/**
	 * Applies a transaction fee if necessary.
	 * 
	 * @throws IOException: Thrown if a connection to the database could not be
	 *                      established.
	 */
	void applyTransactionFee() throws IOException {
		if (this.numberOfTransactionsExceedsTransactionLimit()) {
			BigDecimal newBalance = super.getBalance().subtract(TRANSACTION_FEE);
			this.updateAccount(newBalance);
			this.writeTransaction(TransactionType.TRANSACTION_FEE, TRANSACTION_FEE.toPlainString());
		}
	}

	/**
	 * Applies an overdraft fee if necessary.
	 * 
	 * @throws IOException: Thrown if a connection to the database could not be
	 *                      established.
	 */
	void applyOverDraftFee() throws IOException {
		if (super.newBalanceIsBelowZero(super.getBalance())) {
			BigDecimal newBalance = super.getBalance().subtract(OVERDRAFT_FEE);
			this.updateAccount(newBalance);
			this.writeTransaction(TransactionType.OVERDRAFT_FEE, OVERDRAFT_FEE.toPlainString());
		}
	}

	/**
	 * True if the account cannot overdraft and the balance is below zero. False
	 * otherwise.
	 * 
	 * @param newBalance: The new account balance.
	 */
	boolean accountCannotOverdraftAndBalanceBelowZero(BigDecimal newBalance) {
		return !this.canOverdraft && newBalance.compareTo(BigDecimal.ZERO) < 0;
	}

	/**
	 * True if the account exceeds the overdraft maximum. False otherwise.
	 * 
	 * @param newBalance: The new account balance.
	 */
	boolean accountExceedsOverdraftMaximum(BigDecimal newBalance) {
		return newBalance.compareTo(OVERDRAFT_MAXIMUM) == -1;
	}

	/**
	 * True if the number of current transactions exceeds the monthly transaction
	 * limit. False otherwise.
	 * 
	 * @param newBalance: The new account balance.
	 */
	boolean numberOfTransactionsExceedsTransactionLimit() {
		return this.monthlyTransactionCount > TRANSACTION_FEE_LIMIT_BEFORE_CHARGE;
	}

	/**
	 * Calculates the monthly fee for the account called by an external API.
	 * 
	 * @return: The monthly fee.
	 */
	public BigDecimal getMonthlyFee() {
		return super.getBalance().compareTo(MONTHLY_FEE_WAIVED_MINIMUM_BALANCE) >= 0 ? BigDecimal.ZERO : MONTHLY_FEE;
	}

	@Override
	public String toString() {
		return String.format("%s,%s,%s,%s", accountNumber, accountType, balance.toPlainString(), canOverdraft);
	}

	public int getMonthlyTransactionCount() {
		return monthlyTransactionCount;
	}

	public void setMonthlyTransactionCount(int monthlyTransactionCount) {
		this.monthlyTransactionCount = monthlyTransactionCount;
	}

	public boolean canOverdraft() {
		return canOverdraft;
	}

	public void setCanOverdraft(boolean canOverdraft) {
		this.canOverdraft = canOverdraft;
	}

}
