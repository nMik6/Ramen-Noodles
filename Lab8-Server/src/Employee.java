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

}
