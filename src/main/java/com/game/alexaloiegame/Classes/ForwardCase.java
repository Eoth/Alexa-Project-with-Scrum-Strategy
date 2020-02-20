package com.game.alexaloiegame.Classes;

public class ForwardCase extends Case {
	
	private int numberOfCases;

	public ForwardCase(int id , int numberOfCases) {
		super(id);
		this.numberOfCases = numberOfCases;
	}
	
	

	public int getNumberOfCases() {
		return numberOfCases;
	}



	public void setNumberOfCases(int numberOfCases) {
		this.numberOfCases = numberOfCases;
	}



	@Override
	public void effect(Player p) {
		/* If the player stops on this case , the player will advance of "numberOfCases". */
		System.out.print("Avancer de " + numberOfCases + " cases ; ");
		p.move(numberOfCases);		
	}


}
