import java.util.List;

public class DirectoryProxy implements Directory {

	private MainDirectory main;
	
	public DirectoryProxy(MainDirectory main) {
		this.main = main;
	}
	
	@Override
	public void add(String emps) {
		main.add(emps);
	}

	@Override
	public void clear() {
		main.clear();
	}

	@Override
	public void print() {
		main.print();
	}
	
}
