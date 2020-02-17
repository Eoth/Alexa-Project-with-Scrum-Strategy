package Classes;

import java.util.ArrayList;
import java.util.List;

public class Board {
	/* Represents the board of the game. The board has got a list of cases 
	 * which represents the cases of the board.
	 * */
	
	private List<Case> cases;
	private int maxCases;
	
	
	
	public List<Case> getCases() {
		return cases;
	}

	public void setCases(List<Case> cases) {
		this.cases = cases;
	}
	
	public int getMaxCases() {
		return maxCases;
	}

	public void setMaxCases(int maxCases) {
		this.maxCases = maxCases;
	}

	public Board(int maxCases) {
		int i;
		this.cases = new ArrayList<Case>();
		this.maxCases = maxCases;
		for (i=0 ; i < maxCases+1 ; i++) { // we create maxCases cases + a start case
			cases.add(new Case(i));
		}
		
	}
	
}
