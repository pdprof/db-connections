package pdprof.db.connections;

public class Employee {
	private int id;
	private String name;
	
	public Employee(String id, String name) {
		this.id = Integer.parseInt(id);
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
