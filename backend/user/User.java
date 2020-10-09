package user;

/**
 * Representation of an User.
 * 
 * @author CPS 240 Homework 3 Potential Solution
 *
 */
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class User {

	/*
	 * Used to parse the current date of the transaction into a LocalDate object.
	 */
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	/*
	 * Used to parse the date read from the database.
	 */
	private static final DateTimeFormatter DATE_TIME_FORMATTER_FROM_FILE = DateTimeFormatter.ofPattern("yyyy-MM-dd",
			Locale.ENGLISH);

	private String driversLicense;
	private String firstName;
	private String middleName;
	private String lastName;
	private LocalDate dateOfBirth;
	private Address address;

	/**
	 * Constructs a representation of a user with an address.
	 * 
	 * @param driversLicense: The drivers license.
	 * @param firstName:      The first name.
	 * @param middleName:     the middle name.
	 * @param lastName:       The last name.
	 * @param dateOfBirth:    The date of birth.
	 * @param address:        The address.
	 */
	public User(String driversLicense, String firstName, String middleName, String lastName, String dateOfBirth,
			Address address) {
		this.driversLicense = driversLicense;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.dateOfBirth = LocalDate.parse(dateOfBirth, DATE_TIME_FORMATTER_FROM_FILE);
		this.address = address;
	}

	/**
	 * Constructs a representation of a user.
	 * 
	 * @param driversLicense: The drivers license.
	 * @param firstName:      The first name.
	 * @param middleName:     the middle name.
	 * @param lastName:       The last name.
	 * @param dateOfBirth:    The date of birth.
	 */
	public User(String driversLicense, String firstName, String middleName, String lastName, String dateOfBirth) {
		this.driversLicense = driversLicense;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.dateOfBirth = LocalDate.parse(dateOfBirth, DATE_TIME_FORMATTER);
	}

	@Override
	public String toString() {
		return String.format("%s,%s,%s,%s,%s", driversLicense, firstName, middleName, lastName, dateOfBirth);
	}

	public String getDriversLicense() {
		return driversLicense;
	}

	public void setDriversLicense(String driversLicense) {
		this.driversLicense = driversLicense;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getAgeInYears() {
		return Period.between(dateOfBirth, LocalDate.now()).getYears();
	}

}
