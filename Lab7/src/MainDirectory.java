import java.util.List;

public class MainDirectory implements Directory {

	private String dir;
	
	public MainDirectory() {
		dir = "";
	}
	
	@Override
	public void add(String emps) {
		// TODO Auto-generated method stub
		if(dir.equals(""))dir = emps;
		else {
			
		}
	}

	@Override
	public void clear() {
		dir = "";
		
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
		System.out.println(dir);
		
	}
	
}
