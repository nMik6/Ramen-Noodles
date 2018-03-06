import java.util.ArrayList;
import java.util.List;

public class Directory implements DirectoryType {
	
	private List<Employee> list = new ArrayList<Employee>();

	public void add(List<Employee> group) {
		list.addAll(group);
	}
	
	public void print() {
		
	}
	
	public void clear() {
		
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub
		
	}
	
}
