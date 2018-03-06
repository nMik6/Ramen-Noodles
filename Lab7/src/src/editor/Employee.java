package src.editor;

public class Employee {
	
	private String firstName;
	private String lastName;
	private String department;
	private String phoneNumber;
	
	public Employee(String firstName, String lastName, String department, String phoneNumber) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
		this.phoneNumber = phoneNumber;
	}
	
	public String toString() {
		return firstName + ", " + lastName + " " + phoneNumber + " " + department;
	}
}
