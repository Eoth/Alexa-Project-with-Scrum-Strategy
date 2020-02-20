package com.game.alexaloiegame.Classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import static java.lang.System.*;

public class Game {
	
	private int nbPlayers;
	
	public Game(int nbPlayers) {
		this.nbPlayers = nbPlayers;
	}

	public void start()  {
		/* Start the game. */
		String readLine = null;
		URL url = null;
		try {
			url = new URL("http://35.205.140.234:8080/game_party/all");
			HttpURLConnection con = null;
			con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("GET");
			con.connect();
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuffer response = new StringBuffer();
				while ((readLine = in.readLine()) != null) {
					readLine = readLine.replace("[", "").replace("]", "");
					response.append(readLine);
				}
				in.close();
				// print result
				out.println("JSON String Result " + response.toString());
				if ((!response.toString().equals(""))){
					JSONObject json = new JSONObject(response.toString());
					out.println(" Bienvenue  ! Une partie est en cours ! Voulez vous continuez ?");}
				else out.println("bd renvoie null");
				//GetAndPost.POSTRequest(response.toString());
			} else {
				out.println(" Bienvenue  ! Un problème est survenu lors de vérification d'une partie en cours?");
			}
		} catch (IOException e) {
			out.println(" Bienvenue  ! Un problème est survenu lors de vérification d'une partie en cours?");
		}
		
		List<Player> players = new ArrayList<Player>();
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
