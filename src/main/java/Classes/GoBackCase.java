package Classes;

public class GoBackCase extends Case {
	
	private int numberOfCases;

	public GoBackCase(int id , int numberOfCases) {
		super(id);
		this.numberOfCases = numberOfCases;
	}

	@Override
	public void effect(Player p) {
		/* If the player stops on this case , the player will go back of "numberOfCases". */
		System.out.print("Reculer de " + numberOfCases + " cases ; ");
		p.move(-numberOfCases);		
	}

}
