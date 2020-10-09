package transactions;

/**
 * Transaction dao implementation.
 * 
 * @author CPS 240 Homework 3 Potential Solution
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class TransactionDAOImpl implements TransactionDAO {

	/*
	 * The database to connect to.
	 */
	private static final String DATABASE_NAME = "transactions.txt";

	/*
	 * Used to parse the current date of the transaction into a LocalDate object.
	 */
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	/*
	 * Used to parse the date read from the database.
	 */
	private static final DateTimeFormatter DATE_TIME_FORMATTER_FROM_FILE = DateTimeFormatter.ofPattern("yyyy-MM-dd",
			Locale.ENGLISH);

	@Override
	public Transaction getTransaction(String transactionID) throws FileNotFoundException, IllegalArgumentException {
		File transactionFile = new File(DATABASE_NAME);
		Scanner transactionScanner = new Scanner(transactionFile);
		Transaction transaction = null;
		while (transactionScanner.hasNextLine()) {
			String[] transactionLine = transactionScanner.nextLine().split(",");
			String transactionNumber = transactionLine[0];
			if (transactionNumber.equals(transactionID)) {
				transaction = new Transaction(Integer.parseInt(transactionLine[0]), transactionLine[1],
						TransactionType.valueOf(transactionLine[2]), transactionLine[3], transactionLine[4],
						LocalDate.parse(transactionLine[5], DATE_TIME_FORMATTER_FROM_FILE));
			}
		}
		if (transaction == null) {
			transactionScanner.close();
			throw new IllegalArgumentException("Transaction " + transactionID + " does not exist.");
		}
		transactionScanner.close();
		return transaction;
	}

	@Override
	public List<Transaction> getTransactions(String accountNumber) throws FileNotFoundException {
		File transactionFile = new File(DATABASE_NAME);
		Scanner transactionScanner = new Scanner(transactionFile);
		List<Transaction> transactions = new ArrayList<Transaction>();
		while (transactionScanner.hasNextLine()) {
			String[] transactionLine = transactionScanner.nextLine().split(",");
			if (accountNumber.equals(transactionLine[1])) {
				transactions.add(new Transaction(Integer.parseInt(transactionLine[0]), transactionLine[1],
						TransactionType.valueOf(transactionLine[2]), transactionLine[3], transactionLine[4],
						LocalDate.parse(transactionLine[5], DATE_TIME_FORMATTER_FROM_FILE)));
			}
		}
		transactionScanner.close();
		return transactions;
	}

	@Override
	public List<Transaction> getTransactionsBetween(String accountNumber, LocalDate startDate, LocalDate endDate)
			throws FileNotFoundException {
		File transactionFile = new File(DATABASE_NAME);
		Scanner transactionScanner = new Scanner(transactionFile);
		List<Transaction> transactions = new ArrayList<Transaction>();
		while (transactionScanner.hasNextLine()) {
			String[] transactionLine = transactionScanner.nextLine().split(",");
			String fileAccountNumber = transactionLine[1];
			if (accountNumber.equals(fileAccountNumber)
					&& (LocalDate.parse(transactionLine[5], DATE_TIME_FORMATTER_FROM_FILE).isAfter(startDate)
							&& LocalDate.parse(transactionLine[5], DATE_TIME_FORMATTER_FROM_FILE).isBefore(endDate))) {
				transactions.add(new Transaction(Integer.parseInt(transactionLine[0]), transactionLine[1],
						TransactionType.valueOf(transactionLine[2]), transactionLine[3], transactionLine[4],
						LocalDate.parse(transactionLine[5], DATE_TIME_FORMATTER_FROM_FILE)));
			}
		}
		transactionScanner.close();
		return transactions;
	}

	@Override
	public void writeTransaction(String accountNumber, TransactionType type, String amountToWithdraw, String balance,
			LocalDate now) throws IOException {
		int nextTransactionID = this.getNextTransactionID();
		Transaction transaction = new Transaction(nextTransactionID, accountNumber, type, amountToWithdraw, balance,
				now);
		PrintWriter writer = new PrintWriter(new FileOutputStream(new File(DATABASE_NAME), true));
		writer.println(transaction);
		writer.close();

	}

	@Override
	public int getNextTransactionID() throws IOException {
		BufferedReader reader;
		int lines = 0;
		reader = new BufferedReader(new FileReader(DATABASE_NAME));
		while (reader.readLine() != null)
			lines++;
		reader.close();
		lines += 1;
		return lines;
	}

}
