package accounts.table;

/**
 * Implementation of the account table DAO representation.
 * 
 * @author: CPS 240 Homework 3 Potential Solution
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import accounts.Account;
import accounts.AccountDAO;
import accounts.daos.impls.BusinessCheckingAccountDAOImpl;
import accounts.daos.impls.BusinessSavingsAccountDAOImpl;
import accounts.daos.impls.PersonalCheckingAccountDAOImpl;
import accounts.daos.impls.PersonalSavingsAccountDAOImpl;
import accounts.daos.impls.StudentCheckingAccountDAOImpl;
import accounts.daos.impls.StudentSavingsAccountDAOImpl;
import transactions.TransactionDAO;
import user.User;

public class AccountTableDAOImpl implements AccountTableDAO {

	/*
	 * A mapping from a drivers license to an account table for a user.
	 */
	private static Map<String, AccountTable> accountTableDatabase = new HashMap<String, AccountTable>();

	private AccountDAO studentCheckingDAO;
	private AccountDAO personalCheckingDAO;
	private AccountDAO businessCheckingDAO;
	private AccountDAO studentSavingsDAO;
	private AccountDAO personalSavingsDAO;
	private AccountDAO businessSavingsDAO;

	private TransactionDAO transactionDAO;

	/**
	 * Constructor setting up connections to each account DAO.
	 * 
	 * @param transactionDAO: The transaction DAO for each account.
	 */
	public AccountTableDAOImpl(TransactionDAO transactionDAO) {
		accountTableDatabase = loadInfo();
		this.transactionDAO = transactionDAO;
		this.studentCheckingDAO = new StudentCheckingAccountDAOImpl(this, transactionDAO);
		this.personalCheckingDAO = new PersonalCheckingAccountDAOImpl(this, transactionDAO);
		this.businessCheckingDAO = new BusinessCheckingAccountDAOImpl(this, transactionDAO);
		this.studentSavingsDAO = new StudentSavingsAccountDAOImpl(this, transactionDAO);
		this.personalSavingsDAO = new PersonalSavingsAccountDAOImpl(this, transactionDAO);
		this.businessSavingsDAO = new BusinessSavingsAccountDAOImpl(this, transactionDAO);
	}

	@Override
	public void addAccount(User user, Account account) throws IllegalArgumentException, IllegalStateException {
		if (accountTableDatabase.get(user.getDriversLicense()) == null) {
			accountTableDatabase.put(user.getDriversLicense(), new AccountTable(user.getDriversLicense()));
		}

		AccountTable accountTable = accountTableDatabase.get(user.getDriversLicense());
		String accountNumber = "";
		switch (account.getAccountType()) {
		case STUDENT_CHECKING_ACCOUNT:
			accountNumber = accountTable.getStudentCheckingAccountNumber();
			break;
		case PERSONAL_CHECKING_ACCOUNT:
			accountNumber = accountTable.getPersonalCheckingAccountNumber();
			break;
		case BUSINESS_CHECKING_ACCOUNT:
			accountNumber = accountTable.getBusinessCheckingAccountNumber();
			break;
		case STUDENT_SAVINGS_ACCOUNT:
			accountNumber = accountTable.getStudentSavingsAccountNumber();
			break;
		case PERSONAL_SAVINGS_ACCOUNT:
			accountNumber = accountTable.getPersonalSavingsAccountNumber();
			break;
		case BUSINESS_SAVINGS_ACCOUNT:
			accountNumber = accountTable.getBusinessSavingsAccountNumber();
			break;
		default:
			throw new IllegalArgumentException("Invalid Account Type: " + account.getAccountType());
		}

		if (accountNumber != null) {
			throw new IllegalStateException(
					"User: " + user.getDriversLicense() + " already has a " + account.getAccountType().toString());
		}

		switch (account.getAccountType()) {
		case STUDENT_CHECKING_ACCOUNT:
			accountTable.setStudentCheckingAccountNumber(account.getAccountNumber());
			break;
		case PERSONAL_CHECKING_ACCOUNT:
			accountTable.setPersonalCheckingAccountNumber(account.getAccountNumber());
			break;
		case BUSINESS_CHECKING_ACCOUNT:
			accountTable.setBusinessCheckingAccountNumber(account.getAccountNumber());
			break;
		case STUDENT_SAVINGS_ACCOUNT:
			accountTable.setStudentSavingsAccountNumber(account.getAccountNumber());
			break;
		case PERSONAL_SAVINGS_ACCOUNT:
			accountTable.setPersonalSavingsAccountNumber(account.getAccountNumber());
			break;
		case BUSINESS_SAVINGS_ACCOUNT:
			accountTable.setBusinessSavingsAccountNumber(account.getAccountNumber());
			break;
		default:
			throw new IllegalArgumentException("Invalid Account Type: " + account.getAccountType());
		}

		accountTableDatabase.put(user.getDriversLicense(), accountTable);
		saveInfo();

	}

	public List<Account> getAccounts(String driversLicense) throws FileNotFoundException, IllegalArgumentException {
		AccountTable accountTable = accountTableDatabase.get(driversLicense);
		if (accountTable == null) {
			return new ArrayList<Account>();
		}
		List<Account> accounts = new ArrayList<Account>();
		if (accountTable.getStudentCheckingAccountNumber() != null
				&& !accountTable.getStudentCheckingAccountNumber().isEmpty()) {
			accounts.add(studentCheckingDAO.getAccount(accountTable.getStudentCheckingAccountNumber()));
		}
		if (accountTable.getPersonalCheckingAccountNumber() != null
				&& !accountTable.getPersonalCheckingAccountNumber().isEmpty()) {
			accounts.add(personalCheckingDAO.getAccount(accountTable.getPersonalCheckingAccountNumber()));
		}
		if (accountTable.getBusinessCheckingAccountNumber() != null
				&& !accountTable.getBusinessCheckingAccountNumber().isEmpty()) {
			accounts.add(businessCheckingDAO.getAccount(accountTable.getBusinessCheckingAccountNumber()));
		}
		if (accountTable.getStudentSavingsAccountNumber() != null
				&& !accountTable.getStudentSavingsAccountNumber().isEmpty()) {
			accounts.add(studentSavingsDAO.getAccount(accountTable.getStudentSavingsAccountNumber()));
		}
		if (accountTable.getPersonalSavingsAccountNumber() != null
				&& !accountTable.getPersonalSavingsAccountNumber().isEmpty()) {
			accounts.add(personalSavingsDAO.getAccount(accountTable.getPersonalSavingsAccountNumber()));
		}
		if (accountTable.getBusinessSavingsAccountNumber() != null
				&& !accountTable.getBusinessSavingsAccountNumber().isEmpty()) {
			accounts.add(businessSavingsDAO.getAccount(accountTable.getBusinessSavingsAccountNumber()));
		}
		return accounts;
	}

	/**
	 * Reads in a file containing a hashmap.
	 * 
	 * @return The hashmap that gets read in.
	 */
	public HashMap<String, AccountTable> loadInfo() {
		try {
			FileInputStream fis = new FileInputStream("accounttable.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			HashMap<String, AccountTable> result = (HashMap<String, AccountTable>) ois.readObject();
			ois.close();
			return result;
		} catch (IOException e) {
			return new HashMap<String, AccountTable>();
		} catch (ClassNotFoundException e) {
			return new HashMap<String, AccountTable>();
		}

	}

	/**
	 * Saves the account table to a file.
	 */
	public void saveInfo() {
		try {
			FileOutputStream fos = new FileOutputStream("accounttable.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(accountTableDatabase);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
