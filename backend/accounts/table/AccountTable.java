package accounts.table;

/**
 * A mapper class that maps a user to each of their accounts.
 * 
 * @author: CPS 240 Homework 3 Potential Solution
 */
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class AccountTable implements Serializable {

	private String driversLicense;
	private String studentCheckingAccountNumber;
	private String personalCheckingAccountNumber;
	private String businessCheckingAccountNumber;
	private String studentSavingsAccountNumber;
	private String personalSavingsAccountNumber;
	private String businessSavingsAccountNumber;

	/**
	 * Constructor to set up a default AccountTable
	 * 
	 * @param driversLicense: The user's drivers license.
	 */
	public AccountTable(String driversLicense) {
		this.driversLicense = driversLicense;
	}

	/**
	 * Gets a list of account numbers for a user.
	 * 
	 * @return A list of the user's account numbers.
	 */
	public List<String> getAccountNumbers() {
		return Arrays.asList(studentCheckingAccountNumber, personalCheckingAccountNumber, businessCheckingAccountNumber,
				studentSavingsAccountNumber, personalSavingsAccountNumber, businessSavingsAccountNumber);
	}

	public String getDriversLicense() {
		return driversLicense;
	}

	public void setDriversLicense(String driversLicense) {
		this.driversLicense = driversLicense;
	}

	public String getStudentCheckingAccountNumber() {
		return studentCheckingAccountNumber;
	}

	public void setStudentCheckingAccountNumber(String studentCheckingAccountNumber) {
		this.studentCheckingAccountNumber = studentCheckingAccountNumber;
	}

	public String getPersonalCheckingAccountNumber() {
		return personalCheckingAccountNumber;
	}

	public void setPersonalCheckingAccountNumber(String personalCheckingAccountNumber) {
		this.personalCheckingAccountNumber = personalCheckingAccountNumber;
	}

	public String getBusinessCheckingAccountNumber() {
		return businessCheckingAccountNumber;
	}

	public void setBusinessCheckingAccountNumber(String businessCheckingAccountNumber) {
		this.businessCheckingAccountNumber = businessCheckingAccountNumber;
	}

	public String getStudentSavingsAccountNumber() {
		return studentSavingsAccountNumber;
	}

	public void setStudentSavingsAccountNumber(String studentSavingsAccountNumber) {
		this.studentSavingsAccountNumber = studentSavingsAccountNumber;
	}

	public String getPersonalSavingsAccountNumber() {
		return personalSavingsAccountNumber;
	}

	public void setPersonalSavingsAccountNumber(String personalSavingsAccountNumber) {
		this.personalSavingsAccountNumber = personalSavingsAccountNumber;
	}

	public String getBusinessSavingsAccountNumber() {
		return businessSavingsAccountNumber;
	}

	public void setBusinessSavingsAccountNumber(String businessSavingsAccountNumber) {
		this.businessSavingsAccountNumber = businessSavingsAccountNumber;
	}
}
