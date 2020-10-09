package accounts.daos.impls;

/**
 * Personal checking account dao implementation.
 * 
 * @author CPS 240 Homework 3 Potential Solution
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;

import accounts.Account;
import accounts.AccountDAO;
import accounts.AccountType;
import accounts.PersonalCheckingAccount;
import accounts.table.AccountTableDAO;
import transactions.TransactionDAO;
import transactions.TransactionDAOImpl;
import user.User;

public class PersonalCheckingAccountDAOImpl implements AccountDAO {

	/*
	 * The database to connect to.
	 */
	private static final String DATABASE_NAME = "personalcheckingaccounts.txt";

	private AccountTableDAO accountTableDAO;
	private TransactionDAO transactionDAO;

	/**
	 * Constructor initializing accountTableDAO and transactionDAO to existing
	 * connections.
	 * 
	 * @param accountTableDAO: Provides access to the accounts a user has.
	 * @param transactionDAO:  Provides access to the accounts a user has.
	 */
	public PersonalCheckingAccountDAOImpl(AccountTableDAO accountTableDAO, TransactionDAO transactionDAO) {
		this.accountTableDAO = accountTableDAO;
		this.transactionDAO = transactionDAO;
	}

	@Override
	public void addAccount(User primaryUser) throws IllegalStateException, IOException {
		Account account = this.createAccount(primaryUser);
		this.accountTableDAO.addAccount(primaryUser, account);
		this.writeAccountToDatabase(account);
	}

	@Override
	public void addAccountWithAuthorizedUser(User primaryUser, User authorizedUser)
			throws IllegalStateException, IOException {
		Account account = this.createAccount(primaryUser);
		this.accountTableDAO.addAccount(primaryUser, account);
		this.accountTableDAO.addAccount(authorizedUser, account);
		this.writeAccountToDatabase(account);

	}

	@Override
	public Account getAccount(String accountNumber) throws FileNotFoundException {
		File accountFile = new File(DATABASE_NAME);
		Scanner accountScanner = new Scanner(accountFile);
		Account account = null;
		while (accountScanner.hasNextLine()) {
			String[] accountLine = accountScanner.nextLine().split(",");
			String fileAccountNumber = accountLine[0];
			if (accountNumber.equals(fileAccountNumber)) {
				account = new PersonalCheckingAccount(accountLine[0], accountLine[1],
						AccountType.valueOf(accountLine[2]), new BigDecimal(accountLine[3]),
						Boolean.valueOf(accountLine[4]), this, transactionDAO);
			}
		}
		if (account == null) {
			accountScanner.close();
			throw new IllegalArgumentException("Personal Checking Account " + accountNumber + " does not exist.");
		}
		accountScanner.close();
		return account;
	}

	@Override
	public void updateAccount(String accountNumber, BigDecimal balance) throws IOException {
		File accountsFile = new File(DATABASE_NAME);
		File tempFile = new File("temp.txt");
		BufferedReader reader = new BufferedReader(new FileReader(accountsFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		String currentLine;
		while ((currentLine = reader.readLine()) != null) {
			String fileAccountNumber = currentLine.split(",")[0];
			if (fileAccountNumber.equals(accountNumber)) {
				String[] line = currentLine.split(",");
				line[3] = balance.toPlainString();

				writer.write(String.join(",", line) + System.getProperty("line.separator"));
			} else {
				writer.write(currentLine + System.getProperty("line.separator"));
			}
		}
		writer.close();
		reader.close();
		accountsFile.delete();
		tempFile.renameTo(accountsFile);
	}

	@Override
	public void writeAccountToDatabase(Account account) throws IllegalStateException, FileNotFoundException {
		File accountFile = new File(DATABASE_NAME);
		Scanner accountScanner = new Scanner(accountFile);
		while (accountScanner.hasNextLine()) {
			String[] accountLine = accountScanner.nextLine().split(",");
			String accountNumber = accountLine[0];
			if (String.valueOf(account.getAccountNumber()).equals(accountNumber)) {
				throw new IllegalStateException("Account " + account.getAccountNumber() + " already exists.");
			}
		}
		accountScanner.close();
		PrintWriter writer = new PrintWriter(new FileOutputStream(new File(DATABASE_NAME), true));
		writer.println(account);
		writer.close();
	}

	@Override
	public int getNextAccountID() throws IOException {
		BufferedReader reader;
		int lines = 0;
		reader = new BufferedReader(new FileReader(DATABASE_NAME));
		while (reader.readLine() != null)
			lines++;
		reader.close();
		lines += 1;
		return lines;
	}

	/**
	 * Creates an account representation.
	 * 
	 * @return: The created account.
	 * @throws IOException: Thrown if a connection to the database could not be
	 *                      established.
	 */
	Account createAccount(User primaryUser) throws IOException {
		int nextAccountNumber = this.getNextAccountID();
		Account account = new PersonalCheckingAccount(String.valueOf(nextAccountNumber),
				primaryUser.getDriversLicense());
		return account;
	}
}
