package com.game.alexaloiegame.handlers;
/*
 * 
 */
	

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;

public class WhatsNumberOfPlayershandler implements RequestHandler{

    public boolean canHandle(HandlerInput input) {
		// TODO Auto-generated method stub
		return (input.matches(intentName("NumberOfplayersIntent")));
	}
	
    public Optional<Response> handle(HandlerInput handlerInput) {
		
//    	Intent intent = intentRequest.getIntent();
//	    Slot nb_playersSlot = intent.getSlots().get(PLAYERS_SLOT);
//	    String nb_players = nb_playersSlot
//                .getResolutions()
//                .getResolutionsPerAuthority().get(0).getValues()
//                .get(0).getValue().getId();
    	Request request = handlerInput.getRequestEnvelope().getRequest();
    	IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();

        // Get the number of player slot from user input.
        Slot playersSlot = slots.get("Nb_players");
	    
		String speechText;
		if (playersSlot != null
	            && playersSlot.getResolutions() != null
	            //&& playersSlot.getResolutions().toString().contains("ER_SUCCESS_MATCH")
	            ) 
		{
			
			String numberOfOPlayer = playersSlot.getValue();
			handlerInput.getAttributesManager().setSessionAttributes(Collections.singletonMap("NB_PLAYERS", numberOfOPlayer));
		    speechText = String.format("OK ! Parfait. Vous êtes %s joueurs. Que le meilleur gagne "
                 + "Maintenant, demander lancer de dé ?", numberOfOPlayer);
				 //" Vous êtes "+ nb_players +" joueurs, Que le meilleur gagne !";
		 
        } else {
            // Render an error since user input is out of list of color defined in interaction model.
            speechText = "Oula, saperlipopette ! Je n'ai pas réussi à enrégister le nombre de joueurs. dites recommencer ! ";
        }
           
		
        return handlerInput.getResponseBuilder()
                  .withSimpleCard("Nombre de joueurs Session", speechText)
                  .withSpeech(speechText)
                  .withReprompt(" Dites recommencer")
                  .withShouldEndSession(false)
                  .build();
	}
		

	
}