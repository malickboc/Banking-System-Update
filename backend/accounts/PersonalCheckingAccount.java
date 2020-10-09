package accounts;

/**
 * Personal checking account representation for an account.
 * 
 * @author CPS 240 Homework 3 Potential Solution
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import transactions.TransactionDAO;
import transactions.TransactionType;

public class PersonalCheckingAccount extends Account {

	/*
	 * The penalty for overdrafting an account.
	 */
	private static final BigDecimal OVERDRAFT_FEE = new BigDecimal("35.00");
	/*
	 * The maximum amount the account can be overdraft to.
	 */
	private static final BigDecimal OVERDRAFT_MAXIMUM = new BigDecimal("-1500.00");
	/*
	 * The monthly fee charged to the account.
	 */
	private static final BigDecimal MONTHLY_FEE = new BigDecimal("10.00");
	/*
	 * The minimum account balance to waive the monthly fee.
	 */
	private static final BigDecimal MONTHLY_FEE_DEPOSIT = new BigDecimal("500.00");
	/*
	 * The minimum average account balance for all linked accounts
	 */
	private static final BigDecimal LINKED_ACCOUNT_MINIMUM_AVERAGE = new BigDecimal("5000.00");

	private String linkedOwner;
	private boolean canOverdraft;
	private boolean minimumMonthlyFeeDepositWaived;

	/**
	 * Defines the basic representation of a personal checking account.
	 * 
	 * @param accountNumber: The initial account number assigned to the account.
	 * @param linkedOwner:   The secondary owner of the account.
	 */
	public PersonalCheckingAccount(String accountNumber, String linkedOwner) {
		super(accountNumber, AccountType.PERSONAL_CHECKING_ACCOUNT);
		this.linkedOwner = linkedOwner;
	}

	/**
	 * 
	 * @param accountNumber:  The account number.
	 * @param linkedOwner:    The secondary account owner.
	 * @param type:           The account type.
	 * @param balance:        The account balance.
	 * @param canOverdraft:   If the account can overdraft.
	 * @param accountDAO:     Database connection to the accountDAO for updates.
	 * @param transactionDAO: Database connection to the transactionDAO for updates.
	 */
	public PersonalCheckingAccount(String accountNumber, String linkedOwner, AccountType type, BigDecimal balance,
			boolean canOverdraft, AccountDAO accountDAO, TransactionDAO transactionDAO) {
		super(accountNumber, type, balance, accountDAO, transactionDAO);
		this.linkedOwner = linkedOwner;
		this.canOverdraft = canOverdraft;
	}

	@Override
	public BigDecimal deposit(String amountToDeposit) throws IOException {
		if (!Account.isAmountValid(amountToDeposit)) {
			throw new IllegalArgumentException("Amount not valid: " + amountToDeposit);
		}
		BigDecimal validAmount = new BigDecimal(amountToDeposit);
		if (this.depositExceedsMinimumMonthlyFeeWaiver(validAmount)) {
			this.minimumMonthlyFeeDepositWaived = true;
		}
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
	public BigDecimal getMonthlyFee() throws FileNotFoundException {
		if (minimumMonthlyFeeDepositWaived) {
			return BigDecimal.ZERO;
		}
		BigDecimal sumOfLinkedAccounts = BigDecimal.ZERO;
		List<Account> accounts = accountTableDAO.getAccounts(linkedOwner);
		for (Account account : accounts) {
			sumOfLinkedAccounts = sumOfLinkedAccounts.add(account.getBalance());
		}
		if (sumOfLinkedAccounts.divide(new BigDecimal(accounts.size()), RoundingMode.CEILING)
				.compareTo(LINKED_ACCOUNT_MINIMUM_AVERAGE) >= 0) {
			return BigDecimal.ZERO;
		}
		return MONTHLY_FEE;
	}

	/**
	 * True if the deposit meets minimum monthly fee waiver.
	 * 
	 * @param depositAmount: The amount to deposit.
	 */
	boolean depositExceedsMinimumMonthlyFeeWaiver(BigDecimal depositAmount) {
		return depositAmount.compareTo(MONTHLY_FEE_DEPOSIT) >= 0;
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
		return String.format("%s,%s,%s,%s,%s", accountNumber, linkedOwner, accountType, balance.toPlainString(),
				canOverdraft);
	}

	public String getLinkedOwner() {
		return linkedOwner;
	}

	public void setLinkedOwner(String linkedOwner) {
		this.linkedOwner = linkedOwner;
	}

	public boolean canOverdraft() {
		return canOverdraft;
	}

	public boolean isMinimumMonthlyFeeDepositWaived() {
		return minimumMonthlyFeeDepositWaived;
	}

	public void setMinimumMonthlyFeeDepositWaived(boolean minimumMonthlyFeeDepositWaived) {
		this.minimumMonthlyFeeDepositWaived = minimumMonthlyFeeDepositWaived;
	}

	public void setCanOverdraft(boolean canOverdraft) {
		this.canOverdraft = canOverdraft;
	}

}
