package com.game.alexaloiegame.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.requestType;

public class LaunchRequestHandler implements RequestHandler {

    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(LaunchRequest.class));
    }

    public Optional<Response> handle(HandlerInput input) {
        StringBuilder speechText = new StringBuilder(" Bienvenue  dans le jeue de l'Oie !"
                + " Dites on est prêt pour commencer! ");
        String readLine;
        URL url;
        try {
            url = new URL("http://35.205.140.234:8080/game_party/all");
            HttpURLConnection con;
            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.connect();
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response = new StringBuilder();
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();
                con.disconnect();

                if ((!response.toString().equals("[]"))) {
                    speechText = new StringBuilder(" Bienvenue  dans le jeue de l'Oie   ! Une partie est en cours ! Avec les joueurs, ");
                    JSONArray arrayParties = new JSONArray(response.toString());
                    for (int i=0 ; i<arrayParties.length() ; i++) {

                        JSONObject jsonParty = arrayParties.getJSONObject(i);
                        JSONArray arrayPlayers =   new JSONArray(jsonParty.get("players").toString());
                        for (int j=0 ; j<arrayPlayers.length() ; j++) {

                            JSONObject jsonPlayer = arrayPlayers.getJSONObject(j);
                            String stringPlayer =  jsonPlayer.getString("name");
                            int intPlayer =  jsonPlayer.getInt("position");
                            speechText.append(stringPlayer).append(" , position "+intPlayer+" ! " );
                        }
                    }
                    speechText.append(" Voulez vous continuez ? " + "Si oui dites  dites nom du joueur suivi de lance dé." + "Si non dites nouvelle partie");
                }
                //GetAndPost.POSTRequest(response.toString());
            } else
                speechText = new StringBuilder(" Bienvenue  ! Un problème est survenu lors de vérification d'une partie en cours?");
        } catch (IOException e) {
            speechText = new StringBuilder(" Bienvenue  ! Un problème est survenu lors de vérification d'une partie en cours?");
        }
        return input.getResponseBuilder()
                .withSpeech(speechText.toString())
                .withSimpleCard("begin interaction", speechText.toString())
                .withReprompt(speechText.toString())
                .build();
    }

}
