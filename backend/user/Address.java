package user;

/**
 * Representation of an Address.
 * 
 * @author CPS 240 Homework 3 Potential Solution
 *
 */
public class Address {

	private String driversLicense;
	private String streetOne;
	private String streetTwo;
	private String city;
	private State state;
	private String zip;

	/**
	 * Constructs an address representation.
	 * 
	 * @param driversLicense: The user's drivers license.
	 * @param streetOne:      The street line one.
	 * @param streetTwo:      The street line two.
	 * @param city:           The city.
	 * @param state:          The state.
	 * @param zip:            The zip.
	 */
	public Address(String driversLicense, String streetOne, String streetTwo, String city, State state, String zip) {
		this.driversLicense = driversLicense;
		this.streetOne = streetOne;
		this.streetTwo = streetTwo;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	@Override
	public String toString() {
		return String.format("%s,%s,%s,%s,%s,%s", driversLicense, streetOne, streetTwo, city, state.toString(), zip);
	}

	public String getDriversLicense() {
		return driversLicense;
	}

	public void setDriversLicense(String driversLicense) {
		this.driversLicense = driversLicense;
	}

	public String getStreetOne() {
		return streetOne;
	}

	public void setStreetOne(String streetOne) {
		this.streetOne = streetOne;
	}

	public String getStreetTwo() {
		return streetTwo;
	}

	public void setStreetTwo(String streetTwo) {
		this.streetTwo = streetTwo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}
