package com.game.alexaloiegame.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.requestType;
import static java.lang.System.out;

public class LaunchRequestHandler implements RequestHandler {

    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(LaunchRequest.class));
    }

    public Optional<Response> handle(HandlerInput input) {
        String speechText = " Bienvenue  dans le jeue de l'Oie !"
                          + " Dites on est prêt pour commencer! ";
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
                if ((!response.toString().equals(""))) {
                    speechText = " Bienvenue  dans le jeue de l'Oie   ! Une partie est en cours ! Voulez vous continuez ? "
                                 +"Si oui dites  reprendre."
                                 +"Si non dites nouvelle partie";
                }
                //GetAndPost.POSTRequest(response.toString());
            } else {
                speechText = " Bienvenue  ! Un problème est survenu lors de vérification d'une partie en cours?";
            }
        } catch (IOException e) {
            speechText = " Bienvenue  ! Un problème est survenu lors de vérification d'une partie en cours?";
        }
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("begin interaction", speechText)
                .withReprompt(speechText)
                .build();
    }

}
