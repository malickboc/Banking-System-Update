package user;

/**
 * User dao implementation.
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class UserDAOImpl implements UserDAO {

	/*
	 * The database to connect to.
	 */
	private static final String DATABASE_NAME = "users.txt";

	/*
	 * Used to parse the current date of the transaction into a LocalDate object.
	 */
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	/*
	 * Used to parse the date read from the database.
	 */
	private static final DateTimeFormatter DATE_TIME_FORMATTER_FROM_FILE = DateTimeFormatter.ofPattern("yyyy-MM-dd",
			Locale.ENGLISH);

	private AddressDAO addressDAO;

	/**
	 * Constructs the user dao using an existing address dao connection.
	 * 
	 * @param addressDAO: Provides access to the user's address.
	 */
	public UserDAOImpl(AddressDAO addressDAO) {
		this.addressDAO = addressDAO;
	}

	@Override
	public User getUser(String driversLicense) throws FileNotFoundException, IllegalArgumentException {
		File userFile = new File(DATABASE_NAME);
		Scanner userFileScanner = new Scanner(userFile);
		User user = null;
		while (userFileScanner.hasNextLine()) {
			String[] userLine = userFileScanner.nextLine().split(",");
			String fileDriversLicense = userLine[0];
			if (driversLicense.equals(fileDriversLicense)) {
				user = new User(userLine[0], userLine[1], userLine[2], userLine[3], userLine[4],
						addressDAO.getAddress(userLine[0]));
			}
		}
		if (user == null) {
			throw new IllegalArgumentException("User " + driversLicense + " does not exist.");
		}
		return user;
	}

	@Override
	public void addUser(String driversLicense, String firstName, String middleName, String lastName, String dateOfBirth,
			String streetOne, String streetTwo, String city, String state, String zip)
			throws FileNotFoundException, IllegalStateException {
		File userFile = new File(DATABASE_NAME);
		Scanner userFileScanner = new Scanner(userFile);
		User user = new User(driversLicense, firstName, middleName, lastName, dateOfBirth);
		while (userFileScanner.hasNextLine()) {
			String[] userLine = userFileScanner.nextLine().split(",");
			String fileDriversLicense = userLine[0];
			if (driversLicense.equals(fileDriversLicense)) {
				userFileScanner.close();
				throw new IllegalStateException("User " + driversLicense + " already exists.");
			}
		}
		userFileScanner.close();
		this.addressDAO.addAddress(driversLicense, streetOne, streetTwo, city, State.valueOfAbbreviation(state), zip);
		PrintWriter writer = new PrintWriter(new FileOutputStream(new File("users.txt"), true));
		writer.println(user);
		writer.close();
	}

	@Override
	public boolean removeUser(String driversLicense) throws IOException {
		File usersFile = new File(DATABASE_NAME);
		File tempFile = new File("temp.txt");
		BufferedReader reader = new BufferedReader(new FileReader(usersFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		String currentLine;
		while ((currentLine = reader.readLine()) != null) {
			String fileDriversLicense = currentLine.split(",")[0];
			if (fileDriversLicense.equals(driversLicense))
				continue;
			writer.write(currentLine + System.getProperty("line.separator"));
		}
		writer.close();
		reader.close();
		usersFile.delete();
		return tempFile.renameTo(usersFile);
	}

}
