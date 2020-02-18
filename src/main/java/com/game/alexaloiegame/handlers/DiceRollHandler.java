package com.game.alexaloiegame.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

public class DiceRollHandler implements RequestHandler{

    public boolean canHandle(HandlerInput input) {
		// TODO Auto-generated method stub
		return (input.matches(intentName("ResultOFDiceIntent")));
	}
	
    public Optional<Response> handle(HandlerInput handlerInput) {
    	
    	int result1 = (int) ((6 * Math.random()) + 1); // Throw first 6-face dice
		int result2 = (int) ((6 * Math.random()) + 1); // Throw second 6-face dice
		int result = result1 + result2 ;
		
        String speechText = String.format(" votre dé retourne %d pour %d et %d", result,result1,result2);
		
        return handlerInput.getResponseBuilder()
                  .withSimpleCard("lance dé", speechText)
                  .withSpeech(speechText)
                  .withReprompt(" dites dé ou lancer dé s")
                  .withShouldEndSession(false)
                  .build();
	}
		

	
}