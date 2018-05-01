import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Collection;

import java.util.ArrayList;

public class MainDirectory implements Directory {
	
	private ArrayList<Employee> employees = new ArrayList<Employee>();

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
		
		System.out.println(str);
		
		//Parses data based on index of array. Since this is guaranteed by proxy, this should be okay.
		
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
