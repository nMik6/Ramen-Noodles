import java.util.List;

public class DirectoryProxy implements Directory {

	private MainDirectory main;
	
	@Override
	public void add(String emps) {
		// TODO Auto-generated method stub
		main.add(emps);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		main.clear();
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
		main.print();
	}
	
}
