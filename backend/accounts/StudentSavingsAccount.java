package accounts;

import java.io.IOException;
import java.math.BigDecimal;

import transactions.TransactionDAO;
import transactions.TransactionType;

public class StudentSavingsAccount extends Account {

	/*
	 * The monthly fee charged to the account.
	 */
	private static final BigDecimal MONTHLY_FEE = new BigDecimal("5.00");
	/*
	 * The minimum balance of the account.
	 */
	private static final BigDecimal MINIMUM_BALANCE = new BigDecimal("5.00");
	/*
	 * The minimum account balance to waive the monthly fee.
	 */
	private static final BigDecimal MONTHLY_FEE_WAIVED_MINIMUM_BALANCE = new BigDecimal("250.00");

	private boolean minimumMonthlyFeeBalanceWaived;

	/**
	 * Defines the basic representation of a student savings account.
	 * 
	 * @param accountNumber: The initial account number assigned to the account.
	 */
	public StudentSavingsAccount(String accountNumber) {
		super(accountNumber, AccountType.STUDENT_SAVINGS_ACCOUNT);
	}

	/**
	 * Used to reconstruct a student savings account representation from a file.
	 * 
	 * @param accountNumber:  The account number.
	 * @param type:           The account type.
	 * @param balance:        The account balance.
	 * @param canOverdraft:   If the account can overdraft.
	 * @param accountDAO:     Database connection to the accountDAO for updates.
	 * @param transactionDAO: Database connection to the transactionDAO for updates.
	 */
	public StudentSavingsAccount(String accountNumber, AccountType type, BigDecimal balance, AccountDAO accountDAO,
			TransactionDAO transactionDAO) {
		super(accountNumber, type, balance, accountDAO, transactionDAO);
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
		if (this.isAccountBalanceBelowMinimumBalance(newBalance)) {
			throw new IllegalArgumentException("Account cannot be overdrawn below minimum balance.");
		}
		super.updateAccount(newBalance);
		super.writeTransaction(TransactionType.WITHDRAW, amountToWithdraw);
		return super.getBalance();
	}

	/**
	 * Calculates the monthly fee for the account called by an external API.
	 * 
	 * @return: The monthly fee.
	 */
	public BigDecimal getMonthlyFee() {
		return super.getBalance().compareTo(MONTHLY_FEE_WAIVED_MINIMUM_BALANCE) >= 0 ? BigDecimal.ZERO : MONTHLY_FEE;
	}

	/**
	 * True if the account balance is below the minimum balance. False otherwise.
	 * 
	 * @param newBalance: The new balance.
	 */
	boolean isAccountBalanceBelowMinimumBalance(BigDecimal newBalance) {
		return newBalance.compareTo(MINIMUM_BALANCE) < 0;
	}

	@Override
	public String toString() {
		return String.format("%s,%s,%s", accountNumber, accountType, balance.toPlainString());
	}

	public boolean isMinimumMonthlyFeeBalanceWaived() {
		return minimumMonthlyFeeBalanceWaived;
	}

	public void setMinimumMonthlyFeeBalanceWaived(boolean minimumMonthlyFeeBalanceWaived) {
		this.minimumMonthlyFeeBalanceWaived = minimumMonthlyFeeBalanceWaived;
	}

}
