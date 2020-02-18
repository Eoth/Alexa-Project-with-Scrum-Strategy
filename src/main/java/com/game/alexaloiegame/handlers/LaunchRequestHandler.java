package com.game.alexaloiegame.handlers;

import static com.amazon.ask.request.Predicates.requestType;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;

public class LaunchRequestHandler implements RequestHandler {

    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(LaunchRequest.class));
    }

    public Optional<Response> handle(HandlerInput input) {
    	
        String speechText = " Bienvenue  ! vous venez de lancer le jeu de l'oie !"
        		            + " Dites on est prêt ! ";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("begin interaction", speechText)
                .withReprompt(speechText)
                .build();
    }

}
