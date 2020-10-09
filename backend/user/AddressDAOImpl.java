package user;

/**
 * Address dao implementation.
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
import java.util.Scanner;

public class AddressDAOImpl implements AddressDAO {

	/*
	 * The database to connect to.
	 */
	private static final String DATABASE_NAME = "addresses.txt";

	@Override
	public Address getAddress(String driversLicense) throws FileNotFoundException {
		File addressFile = new File(DATABASE_NAME);
		Scanner addressFileScanner = new Scanner(addressFile);
		Address address = null;
		while (addressFileScanner.hasNextLine()) {
			String[] addressLine = addressFileScanner.nextLine().split(",");
			String fileDriversLicense = addressLine[0];
			if (driversLicense.equals(fileDriversLicense)) {
				address = new Address(addressLine[0], addressLine[1], addressLine[2], addressLine[3],
						State.valueOfName(addressLine[4]), addressLine[5]);
			}
		}

		if (address == null) {
			throw new IllegalArgumentException("Address for " + driversLicense + " does not exist.");
		}
		return address;
	}

	@Override
	public boolean addAddress(String driversLicense, String streetOne, String streetTwo, String city, State state,
			String zip) throws FileNotFoundException, IllegalStateException {
		File addressFile = new File(DATABASE_NAME);
		Scanner addressFileScanner = new Scanner(addressFile);
		Address address = new Address(driversLicense, streetOne, streetTwo, city, state, zip);
		while (addressFileScanner.hasNextLine()) {
			String[] addressLine = addressFileScanner.nextLine().split(",");
			String fileDriversLicense = addressLine[0];
			if (driversLicense.equals(fileDriversLicense)) {
				throw new IllegalStateException("Address for " + driversLicense + " already exists.");
			}
		}
		addressFileScanner.close();
		PrintWriter writer = new PrintWriter(new FileOutputStream(new File("addresses.txt"), true));
		writer.println(address);
		writer.close();
		return true;
	}

	@Override
	public boolean removeAddress(String driversLicense) throws IOException {
		File addressFile = new File(DATABASE_NAME);
		File tempFile = new File("temp.txt");
		BufferedReader reader = new BufferedReader(new FileReader(addressFile));
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
		addressFile.delete();
		return tempFile.renameTo(addressFile);
	}

}
