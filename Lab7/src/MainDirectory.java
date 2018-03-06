import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Collection;

import java.util.ArrayList;

public class MainDirectory implements Directory {


private List<Employee> employees = new ArrayList<Employee>();
	@Override
	public void add(String str) {
		// TODO Auto-generated method stub
		// Steve smith 184484848484 ENG
		//Splits based on spaces.
		String[] employeeData = str.split(" ");
		String first = "";
		String last = "";
		String phoneNumber = "";
		String department = "";
		
		//Parses data based on index of array. Since this is guaranteed by proxy, this should be okay.
		for (int i = 0; i < employeeData.length; ++i)
		{
			if (i % 4 == 0 || i == 0)
			{
				first = employeeData[i];
			}
			else if (i % 4 == 1 || i == 1) 
			{
				last = employeeData[i];
				
			}
			else if (i % 4 == 2 || i == 2)
			{
				phoneNumber = employeeData[i];
			}
			
			else if (i % 4 == 3 || i ==3)
			{
				//All other fields will be full by now, can create the object and add it to the list.
				department = employeeData[i];
				employees.add(new Employee(first, last, phoneNumber, department));
			}
		}
		
		Gson g = new Gson();
		
		employees = (g.fromJson(str,  new TypeToken<Collection<Employee>>() {}.getType()));
		
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		//Clear string
		employees = new ArrayList<Employee>();
		
		
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
		for (Employee e : employees) {
			System.out.println(e.toString());
		}
		
		
		
	}
	
}
