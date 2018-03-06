
public class Employee {

	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String department;
	
	public Employee(String firstName, String lastName, String phoneNumber, String department) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.department = department;
	}
	
	public String toString() {
		return lastName + ", " + firstName + " " + phoneNumber + " " + department;
	}
	
}
