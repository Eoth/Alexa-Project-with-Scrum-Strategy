package com.game.alexaloiegame.Classes;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class Game {
	
	private int nbPlayers;
	
	public Game(int nbPlayers) {
		this.nbPlayers = nbPlayers;
	}

	public void start()  {
		/* Start the game. */
		
		List<Player> players = new ArrayList<>();
		for(int i=1 ; i<=this.nbPlayers ; i++)
			players.add(new Player("Joueur " + i));
		Board board = new Board(50);
		for(int i=5 ; i<=35 ; i+=10)
			board.getCases().set(i, new ForwardCase(i, 4));
		for(int i=7 ; i<=47 ; i+=10)
			board.getCases().set(i, new GoBackCase(i, 4));
		board.getCases().set(48, new ResetCase(48));
		boolean finished = false; // A player reaches the finish ?
		int turn = 1;
		
		while(!finished) {
			out.println("Turn " + turn + " :");
			for (Player p : players) {
				int posPlayer = p.getPosition();
				int maxCases = board.getMaxCases();
				int result1 = (int) ((6 * Math.random()) + 1); // Throw first 6-face dice
				int result2 = (int) ((6 * Math.random()) + 1); // Throw second 6-face dice
				if((posPlayer + result1 + result2) > maxCases) // If player overflows the finish ?
					p.setPosition(2 * maxCases - posPlayer - result1 - result2);
				else
					p.move(result1 + result2);
				Case currentCase = board.getCases().get(p.getPosition());
				out.print("Lancer des deux dés : " + (result1 + result2) + " : ");
				currentCase.effect(p); // activate the effect of this case
				out.println(p);
				if(p.getPosition() == maxCases) { // current player reacher the finish ?
					finished = true;
					break; // exit the "for" structure
				}
				
				
			}
			out.println("\n\n");
			turn++;
			
		}	

	}

}
