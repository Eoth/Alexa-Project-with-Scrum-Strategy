package com.game.alexaloiegame.Classes;

public class Player {
	
	private String name;
	private int position; // current position of the player
	
	
	public Player(String name) {
		this.name = name;
		this.position = 0; // all players start at the start case
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "Player [name = " + name + ", position = " + position + "]";
	}
	
	public void move(int numberOfCases) {
		/* Moves the player of number of cases specified. If this number is negative ,
		 * the player will go back. */
		position = position + numberOfCases;
		
		
	}
	
	
	
	
	
	

	
}
