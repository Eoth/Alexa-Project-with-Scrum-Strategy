package Classes;

public class Case {
	protected int id; // number of the case
	
	public Case(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void effect(Player p) {
		// A normal case has no effects.		
	}

}
