public class Employee implements Comparable<Object>{
	
	private String firstName;
	private String lastName;
	private String department;
	private String phoneNumber;
	private String title;
	private boolean isFemale;
	
	public Employee(String firstName, String lastName, String department, String phoneNum, String title, boolean isFemale) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
		this.phoneNumber = phoneNum;
		this.title = title;
		this.isFemale = isFemale;
	}
	
	@Override
	public String toString() {
		return title + " " + firstName + " " + lastName + " (" + (isFemale?"f":"m") + ") \tin " + department + " \thas phone number of " + phoneNumber;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Employee) {
			Employee other = (Employee) o;
			return lastName.compareTo(other.lastName);
		}
		return 0;
	}
	
	/**
	 * Converts a string into a boolean value representing a gender
	 * @param str (valid forms: "true","false","female","male","f","m")
	 * @return true when female, false when male
	 * @throws IllegalArgumentException when str is invalid (valid forms: "true","false","female","male","f","m")
	 */
	public static boolean toIsFemale(String str) {
		if( str.equalsIgnoreCase("true") || str.equalsIgnoreCase("female") || str.equalsIgnoreCase("f") ) {
			return true;
		}else if( str.equalsIgnoreCase("false") || str.equalsIgnoreCase("male") || str.equalsIgnoreCase("m") ) {
			return false;
		}else{
			throw new IllegalArgumentException();
		}
	}

}
