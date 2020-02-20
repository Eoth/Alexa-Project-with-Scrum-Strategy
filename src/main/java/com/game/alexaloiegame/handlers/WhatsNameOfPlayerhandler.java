package com.game.alexaloiegame.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class WhatsNameOfPlayerhandler implements RequestHandler{
	public boolean canHandle(HandlerInput input) {
		// TODO Auto-generated method stub
		return (input.matches(intentName("NameOfPlayerIntent")));
	}

	public Optional<Response> handle(HandlerInput handlerInput) {


		Request request = handlerInput.getRequestEnvelope().getRequest();
		IntentRequest intentRequest = (IntentRequest) request;
		Intent intent = intentRequest.getIntent();
		Map<String, Slot> slots = intent.getSlots();

		// Get the number of player slot from user input.
		Slot playersSlot = slots.get("Name");

		String speechText;
		if (playersSlot != null
				&& playersSlot.getResolutions() != null)
		{

			String nameOfOPlayer = playersSlot.getValue();
			handlerInput.getAttributesManager().setSessionAttributes(Collections.singletonMap("NAME", nameOfOPlayer));

			URL url;
			try {
				url = new URL("http://35.205.140.234:8080/player/register?name="+nameOfOPlayer+"&position=0");
				HttpURLConnection con;
				con = (HttpURLConnection) url.openConnection();

				con.setRequestMethod("POST");
				con.connect();
				int responseCode = con.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_CREATED) {
					speechText = String.format("OK ! Parfait. %s a été enrégistré", nameOfOPlayer);
					con.disconnect();
					url = new URL("http://35.205.140.234:8080/game_party/add?party_name=partie&player_name="+nameOfOPlayer);
					con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					con.connect();
					responseCode = con.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_ACCEPTED)
					{
						speechText = speechText + "Et à été ajouter la partie";
						con.disconnect();
					}

					//GetAndPost.POSTRequest(response.toString());
				} else {
					speechText = String.format("Redites votre nom ! %s n'a pas pu être enrégistré.", nameOfOPlayer);
				}
			} catch (IOException e) {
				speechText = String.format("Redites votre nom ! %s n'a pas pu être enrégistré.", nameOfOPlayer);
			}

		} else {
			// Render an error since user input is out of list of color defined in interaction model.
			speechText = "Oula, saperlipopette ! Je n'ai pas pu enrégistré le joueur. dites joueur ! ";
		}


		return handlerInput.getResponseBuilder()
				.withSimpleCard("Nom de joueurs Session", speechText)
				.withSpeech(speechText)
				.withReprompt(" Dites joueur")
				.withShouldEndSession(false)
				.build();
	}



}