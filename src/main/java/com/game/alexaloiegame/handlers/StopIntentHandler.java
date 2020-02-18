package com.game.alexaloiegame.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

public class StopIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.StopIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Vous quitter le skill de Tibé, Shuang, Anas & olivier, au revoir !";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Leave game", speechText)
                .build();
    }
}
