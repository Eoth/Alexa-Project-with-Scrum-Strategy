package com.game.alexaloiegame.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.game.alexaloiegame.Classes.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class DiceRollHandler implements RequestHandler{

    public boolean canHandle(HandlerInput input) {
		/* TODO Auto-generated method stub */
		return (input.matches(intentName("ResultOFDiceIntent")));
	}
	
    public Optional<Response> handle(HandlerInput handlerInput) {
    	
    	/* Creation of the board game. */
    	Board board = new Board(50);
		for(int j=5 ; j<=35 ; j+=10)
			board.getCases().set(j, new ForwardCase(j, 4));
		for(int j=7 ; j<=47 ; j+=10)
			board.getCases().set(j, new GoBackCase(j, 4));
		board.getCases().set(48, new ResetCase(48));
		
		/* The sentence which Alexa will say.*/
		String speechText="";
		
    	/* Ask the name of player who is moved by dice roll. */
    	Request request = handlerInput.getRequestEnvelope().getRequest();
    	IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();

        // Get the number of player slot from user input.
        Slot playersSlot = slots.get("Dice_name");

        if(playersSlot != null) {
        	String playerName = playersSlot.getValue();
        	
        	try {
				URL url = new URL("http://35.205.140.234:8080/player/all");
				HttpURLConnection con;
				con = (HttpURLConnection) url.openConnection();

				con.setRequestMethod("GET");
				con.connect();
				int responseCode = con.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					StringBuilder response = new StringBuilder();
					String readLine;
					while ((readLine = in.readLine()) != null) {
						response.append(readLine);
					}
					in.close();
					con.disconnect();
					JSONArray arrayPlayers = new JSONArray(response.toString());
					for (int i=0 ; i<arrayPlayers.length() ; i++) {
						
						JSONObject jsonPlayer = arrayPlayers.getJSONObject(i);
						String stringPlayer =  jsonPlayer.getString("name");
						if (playerName.equals(stringPlayer)) {
							Player p = new Player(playerName); // Current Player
							p.setPosition(jsonPlayer.getInt("position"));
							int posPlayer = p.getPosition();
							int maxCases = board.getMaxCases();
							int result1 = (int) ((6 * Math.random()) + 1); // Throw first 6-face dice
							int result2 = (int) ((6 * Math.random()) + 1); // Throw second 6-face dice
							if((posPlayer + result1 + result2) > maxCases) // If player overflows the finish ?
								p.setPosition(2 * maxCases - posPlayer - result1 - result2);
							else
								p.move(result1 + result2);
							Case currentCase = board.getCases().get(p.getPosition());
							speechText += "Lancer des deux dés ! " + (result1 + result2) + " ! ";
							currentCase.effect(p); // activate the effect of this case
							if (currentCase instanceof ForwardCase) {
								ForwardCase fc = (ForwardCase) currentCase;
								int numberOfCases = fc.getNumberOfCases();
								speechText += " Vous avancez de " + numberOfCases + " cases !";
							}
							if (currentCase instanceof GoBackCase) {
								GoBackCase gc = (GoBackCase) currentCase;
								int numberOfCases = gc.getNumberOfCases();
								speechText += " Vous reculez de " + numberOfCases + " cases !";
							}
							if (currentCase instanceof ResetCase) {
								speechText +=  " Vous retournez à la case départ ! CHEEEEEH !";
	
							}
							speechText += playerName +" ! placez votre pion à "+p.getPosition();


							con.disconnect();
							url = new URL("http://35.205.140.234:8080/player/update?name="+playerName+"&position="+p.getPosition());
							con = (HttpURLConnection) url.openConnection();
							con.setRequestMethod("PUT");
							con.connect();
							responseCode = con.getResponseCode();
							if (responseCode == HttpURLConnection.HTTP_OK)
							{
								speechText +=  "! ok !";
								con.disconnect();
							}
							
							break;
						}
					}

					//GetAndPost.POSTRequest(response.toString());
				}
			} catch (IOException ignored) {
			}
        	
        	
        }else speechText = " le nom du joueur qui à lancé n'a pas été recupérer";



        return handlerInput.getResponseBuilder()
                  .withSimpleCard("lance dé", speechText)
                  .withSpeech(speechText)
                  .withReprompt(" Dites votre nom et dite lancer dé")
                  .withShouldEndSession(false)
                  .build();
	}
		

	
}