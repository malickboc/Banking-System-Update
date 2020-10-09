package accounts;

/**
 * Student checking account representation for an account.
 * 
 * @author CPS 240 Homework 3 Potential Solution
 */

import java.io.IOException;
import java.math.BigDecimal;

import transactions.TransactionDAO;
import transactions.TransactionType;

public class StudentCheckingAccount extends Account {

	/*
	 * The penalty for overdrafting an account.
	 */
	private static final BigDecimal OVERDRAFT_FEE = new BigDecimal("35.00");
	/*
	 * The maximum amount the account can be overdraft to.
	 */
	private static final BigDecimal OVERDRAFT_MAXIMUM = new BigDecimal("-500.00");
	/*
	 * The monthly fee charged to the account.
	 */
	private static final BigDecimal MONTHLY_FEE = BigDecimal.ZERO;

	private boolean canOverdraft;

	/**
	 * Defines the basic representation of a student checking account.
	 * 
	 * @param accountNumber: The initial account number assigned to the account.
	 */
	public StudentCheckingAccount(String accountNumber) {
		super(accountNumber, AccountType.STUDENT_CHECKING_ACCOUNT);
	}

	/**
	 * 
	 * @param accountNumber:  The account number.
	 * @param type:           The account type.
	 * @param balance:        The account balance.
	 * @param canOverdraft:   If the account can overdraft.
	 * @param accountDAO:     Database connection to the accountDAO for updates.
	 * @param transactionDAO: Database connection to the transactionDAO for updates.
	 */
	public StudentCheckingAccount(String accountNumber, AccountType type, BigDecimal balance, boolean canOverdraft,
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
		this.applyOverDraftFee();
		return super.getBalance();
	}

	/**
	 * Calculates the monthly fee for the account called by an external API.
	 * 
	 * @return: The monthly fee.
	 */
	public BigDecimal getMonthlyFee() {
		return MONTHLY_FEE;
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

	@Override
	public String toString() {
		return String.format("%s,%s,%s,%s", accountNumber, accountType, balance.toPlainString(), canOverdraft);
	}

	public boolean canOverdraft() {
		return canOverdraft;
	}

	public void setCanOverdraft(boolean canOverdraft) {
		this.canOverdraft = canOverdraft;
	}

}
